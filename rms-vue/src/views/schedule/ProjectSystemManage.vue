<template>
  <el-card shadow="never" style="height: 75px;">
    <el-form :inline="true" :model="queryInfo">
      <el-form-item label="系统名称" class="inputSearch">
        <el-input v-model="queryInfo.name" placeholder="请输入名称" clearable style="width: 180px;"/>
      </el-form-item>
      <el-form-item label="系统状态" class="inputSearch">
        <el-select v-model="queryInfo.state" clearable placeholder="请选择状态" style="width: 180px" >
          <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="系统负责人" class="inputSearch" >
        <el-input v-model="queryInfo.responsiblePersonName" placeholder="请输入姓名" style="width: 180px"/>
      </el-form-item>
      <el-form-item label="所用技术" class="inputSearch" >
        <el-select v-model="queryInfo.developmentTech" clearable placeholder="请选择技术" style="width: 180px" >
          <el-option v-for="item in developmentTechOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" type="primary" @click="handleSearch">搜索</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <el-card style="margin-top: 5px;height: 625px;" shadow="never">
    <el-button :icon="Refresh" @click="doRefresh" circle></el-button>
    <el-button type="primary" @click="showDialog('add')">新增</el-button>
    <el-button type="success">导入</el-button>
    <el-button type="warning">导出</el-button>

    <el-table :data="tableData.projectData" height="500" style="width: 100%">
      <el-table-column type="index" ></el-table-column>
      <el-table-column prop="name" label="系统名称"/>
      <el-table-column prop="projectName" label="所属项目名称"/>
      <el-table-column label="状态">
        <template #default="scope">
          <el-tag disable-transitions v-if="scope.row.stateName == '未开始'">
            {{ scope.row.stateName }}
          </el-tag>
          <el-tag disable-transitions v-if="scope.row.stateName == '进行中'" type="success">
            {{ scope.row.stateName }}
          </el-tag>
          <el-tag disable-transitions v-if="scope.row.stateName == '已完成'" type="warning">
            {{ scope.row.stateName }}
          </el-tag>
          <el-tag disable-transitions v-if="scope.row.stateName == '已暂停'" type="danger">
            {{ scope.row.stateName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="responsiblePersonName" label="系统负责人" />
      <el-table-column prop="difficultyRate" label="难度系数" sortable/>
      <el-table-column prop="developmentTechName" label="所用技术"/>
      <el-table-column label="操作" align="left" width="330px">
        <template #default="scope">
          <el-button text @click="handleShowDetail(scope.row)" style="margin-right: 0; margin-left: 0;padding-left: 5px; padding-right: 5px">
            <el-icon style="margin-right: 3px"><ZoomIn/></el-icon>
            详情
          </el-button>
          <el-button text type="danger" @click="handleEdit(scope.row)" style="margin-right: 0; margin-left: 0;padding-left: 5px; padding-right: 5px">
            <el-icon style="margin-right: 3px"><Edit/></el-icon>
            编辑
          </el-button>
          <el-button text type="primary" @click="handleDelete(scope.row)" style="margin-right: 0; margin-left: 0;padding-left: 5px; padding-right: 5px">
            <el-icon style="margin-right: 3px"><Delete/></el-icon>
            删除
          </el-button>
          <el-button text type="warning" @click="handleShowHistory(scope.row)" style="margin-right: 0; margin-left: 0;padding-left: 5px; padding-right: 5px">
            <el-icon style="margin-right: 3px"><Grid/></el-icon>
            历史版本
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="dialogConfig.visible" :title="dialogConfig.title" width="500" @close="clearNewProjectSystem">
    <el-form :model="newProjectSystem" :disabled="dialogConfig.disable">
      <el-form-item label="系统名称">
        <el-input v-model="newProjectSystem.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="所属项目">
        <el-select v-model="newProjectSystem.projectId" filterable remote remote-show-suffix
                   placeholder="请选择项目"
                   :remote-method="remoteSelectProject"
                   style="width: 240px"
        >
          <el-option
              v-for="item in projectOptions.listData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系统负责人">
        <el-select v-model="newProjectSystem.responsiblePersonId" filterable remote remote-show-suffix
                   placeholder="请选择负责人"
                   :remote-method="remoteSelectResponsiblePerson"
                   style="width: 240px"
        >
          <el-option
              v-for="item in developerOptions.listData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="需求阶段工作量">
        <el-input-number v-model="newProjectSystem.demandWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="设计阶段工作量">
        <el-input-number v-model="newProjectSystem.designWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="开发阶段工作量">
        <el-input-number v-model="newProjectSystem.developWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="测试预期工期">
        <el-input-number v-model="newProjectSystem.testWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="上线预期工作量">
        <el-input-number v-model="newProjectSystem.onlineWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;" />
      </el-form-item>
      <el-form-item label="难度系数">
        <el-input-number v-model="newProjectSystem.difficultyRate" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;" />
      </el-form-item>
      <el-form-item label="所用开发技术">
        <el-select v-model="newProjectSystem.developmentTech" clearable placeholder="请选择技术" style="width: 180px" >
          <el-option v-for="item in developmentTechOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogConfig.visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>

  //用于刷新数据
  import {getCurrentInstance, reactive, inject, onMounted, ref} from "vue";
  import {Search, Refresh, Edit, ZoomIn} from "@element-plus/icons-vue";
  import {ElNotification} from "element-plus";
  import {useRouter} from 'vue-router'

  const doRefresh = inject("reload")
  const {proxy} = getCurrentInstance()
  let router = useRouter()
  const stateOptions = [
    {
      value: 1,
      label: '未开始'
    },
    {
      value: 2,
      label: '进行中'
    },
    {
      value: 3,
      label: '已完成'
    },
    {
      value: 4,
      label: '已暂停'
    },
  ]
  let tableData = reactive({
    total: 0,
    projectSystemData: []
  })
  let queryInfo = reactive({
    pageSize:15,
    pageNow:1,
    projectId:null,
    name:null,
    state:null,
    responsiblePersonName:null,
    developmentTech:null, // id
  })
  let dialogConfig = reactive({
    visible: false,
    title:'',
    disable: false,
    type:''
  })
  let newProjectSystem = reactive({
    id:null,
    projectId:null,
    systemId:null,
    responsiblePersonId:null,
    totalWorkload:null,
    demandWorkload:null,
    designWorkload:null,
    developWorkload:null,
    testWorkload:null,
    onlineWorkload:null,
    actualWorkday:null,
    investedWorkday:null,
    actualWorkload:null,
    investedWorkload:null,
    state:null,
    version:null,
    name:null,
    difficultyRate:null,
    developmentTech:null,

    stateName:null,
    projectName:null,
    responsiblePersonName:null
  })
  let developerOptions = reactive({
    listData: []
  })
  let projectOptions = reactive({
    listData: []
  })
  let developmentTechOptions = reactive([
    {
      label:"Java",
      value:"0e83088df4b34698b4ebc07dcd8f113d"
    },
    {
      label:"Python",
      value:"1155dfaef66e4207bb7311d5189c21ce",
    },
    {
      label:"PHP",
      value:"24020ba73c60449c9664db8b9f808d61",
    },
    {
      label:"Golang",
      value:"dc2c5a210667461b911f80726f315ec3",
    },
    {
      label:"Rust",
      value:"ebe4d789b32742b28c126c31c88905b2",
    },
    {
      label:"Android",
      value:"f31963eab02e49ae890c1fdad0427713",
    },
    {
      label: "C++",
      value: "eab9e158a98d4a91a255e1f58eae5717"
    }
  ])
  function initdeveloperOptions(){
    proxy.$http({
      url: "/modeling/developerManage/page",
      method: "post",
      data:{
        pageSize:100,
        pageNow:1,
      }
    }).then((res)=>{
      let developers = res.data.data.rowData
      for(let i = 0; i < developers.length; i++){
        developerOptions.listData.push({
          label:developers[i].name,
          value:developers[i].id
        })
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function initdevelopmentTechOptions(){} // TODO
  function showState(data){
    if(data == 1) return '未开始'
    if(data == 2) return '进行中'
    if(data == 3) return '已完成'
    if(data == 4) return '已暂停'
  }
  function showTable(myData){
    for(let i = 0; i < myData.length; i++){
      myData[i].stateName = showState(myData[i].state)
      for(let j = 0; j < developmentTechOptions.length; j++){
        if(myData[i].developmentTech === developmentTechOptions[j].value){
          myData[i].developmentTechName = developmentTechOptions[j].label;
          break;
        }
      }
    }
  }
  function showDialog(type) {
    if(type === 'add'){
      dialogConfig.type = 'add'
      dialogConfig.title = '新增系统'
      dialogConfig.visible = true
      dialogConfig.disable = false
    }
    if(type === 'edit'){
      dialogConfig.type = 'edit'
      dialogConfig.title = '编辑系统'
      dialogConfig.visible = true
      dialogConfig.disable = false
    }
    if(type === 'detail'){
      dialogConfig.type = 'detail'
      dialogConfig.title = '系统详情'
      dialogConfig.visible = true
      dialogConfig.disable = true
    }
  }
  function clearNewProjectSystem(){
    Object.assign(newProjectSystem,{
      id:null,
      projectId:null,
      systemId:null,
      responsiblePersonId:null,
      totalWorkload:null,
      demandWorkload:null,
      designWorkload:null,
      developWorkload:null,
      testWorkload:null,
      onlineWorkload:null,
      actualWorkday:null,
      investedWorkday:null,
      actualWorkload:null,
      investedWorkload:null,
      state:null,
      version:null,
      name:null,
      difficultyRate:null,
      developmentTech:null,
    })
  }

  function handleSubmit(){
    console.log("提交前最后一步", newProjectSystem.projectId)
    proxy.$http({
      url: "/schedule/system",
      method: "post",
      data: newProjectSystem
    }).then((res)=>{
      if(res.data.code===200){
        ElNotification({
          title: '修改成功',
          type: 'success',
        })
      }else{
        proxy.$message.error(res.data.msg)
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
    doRefresh()
  }
  function handleSearch(){
    proxy.$http({
      url: "/schedule/system/getProjectSystemListBySearch",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.total=res.data.data.totalRows
      let myData = res.data.data.rowData
      showTable(myData)
      tableData.projectData = myData
      //TODO从后端加载开发技术列表，并赋值给projectData

      // console.log(res)
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function handleShowDetail(row){
    Object.assign(newProjectSystem, row)
    showDialog('detail')
  }
  function handleEdit(row){
    Object.assign(newProjectSystem, row)
    showDialog('edit')
  }
  function handleDelete(row){
    proxy.$http({
      url: "/schedule/system/"+row.systemId,
      method: "delete",
    }).then((res)=>{
      if(res.data.code===200){
        ElNotification({
          title: '删除成功',
          type: 'success',
        })
      }else{
        proxy.$message.error(res.data.msg)
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
    doRefresh()
  }

  const loading = ref(false)
  function remoteSelectProject(query){
    projectOptions.listData = []
    if (query) {
      console.log(query)
      loading.value = true
      setTimeout(() => {
        loading.value = false
        proxy.$http({
          url: "/schedule/project/getProjectListBySearch",
          method: "post",
          data:{
            pageSize:100,
            pageNow:1,
            name:query
          }
        }).then((res)=>{
          let projectList = res.data.data.rowData
          for(let i = 0; i < projectList.length; i++){
            projectOptions.listData.push({
              label: projectList[i].name,
              value: projectList[i].id
            })
          }
        }).catch((ex)=>{
          proxy.$message.error(ex.message)
        })
      }, 200)
    } else {
      projectOptions.listData = []
    }
  }
  function remoteSelectResponsiblePerson(query){
    developerOptions.listData = []
    if (query) {
      console.log(query)
      loading.value = true
      setTimeout(() => {
        loading.value = false
        proxy.$http({
          url: "/modeling/developerManage/page",
          method: "post",
          data:{
            pageSize:100,
            pageNow:1,
            name:query
          }
        }).then((res)=>{
          let developerList = res.data.data.rowData
          for(let i = 0; i < developerList.length; i++){
            developerOptions.listData.push({
              label: developerList[i].name,
              value: developerList[i].id
            })
          }
        }).catch((ex)=>{
          proxy.$message.error(ex.message)
        })
      }, 200)
    } else {
      developerOptions.listData = []
    }
  }
  function handleShowHistory(row){
    router.push({
      path:'/projectSystemHistory',
      query:{
        projectId: row.projectId,
        systemId: row.systemId
      }
    })
  }

  onMounted(() => {
    handleSearch()
    initdeveloperOptions()
  })
</script>

<script>
export default {
  name: "ProjectSystemManage"
}
</script>

<style scoped>

</style>