<template>
  <el-card shadow="never" style="height: 130px;">
    <el-form :inline="true" :model="queryInfo">
      <el-form-item label="项目名称" class="inputSearch">
        <el-input v-model="queryInfo.name" placeholder="请输入名称" clearable style="width: 180px;"/>
      </el-form-item>
      <el-form-item label="项目状态" class="inputSearch">
        <el-select v-model="queryInfo.state" clearable placeholder="请选择状态" style="width: 180px" >
          <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" class="inputSearch">
        <el-select v-model="queryInfo.priorityLevel" clearable placeholder="请选择优先级" style="width: 180px" >
          <el-option v-for="item in priorityLevelOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="项目负责人" class="inputSearch" >
        <el-input v-model="queryInfo.responsiblePersonName" placeholder="请输入姓名" style="width: 180px"/>
      </el-form-item>
      <el-form-item label="项目截止时间" class="inputSearch">
        <el-date-picker v-model="queryInfo.startLastEndTime" type="datetime" placeholder="开始时间" style="width: 180px"/>
        <span style="margin-left: 5px;margin-right: 5px;">-</span>
        <el-date-picker v-model="queryInfo.endLastEndTime" type="datetime" placeholder="结束时间" style="width: 180px"/>
      </el-form-item>
      <el-form-item style="margin-left: 460px">
        <el-button :icon="Search" type="primary" @click="handleSearch" style="width: 100px">搜索</el-button>
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
      <el-table-column prop="name" label="项目名称"/>
      <el-table-column prop="info" label="项目信息" />
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
      <el-table-column prop="priorityLevelName" label="优先级" />
      <el-table-column prop="responsiblePersonName" label="项目负责人" />
      <el-table-column prop="lastEndTime" label="截止时间" sortable/>
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

  <el-dialog v-model="dialogConfig.visible" :title="dialogConfig.title" width="500" @close="clearNewProject">
    <el-form :model="newProject" :disabled="dialogConfig.disable">
      <el-form-item label="项目名称">
        <el-input v-model="newProject.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="项目信息">
        <el-input v-model="newProject.info" placeholder="请输入信息"></el-input>
      </el-form-item>
      <el-form-item label="项目负责人">
        <el-select v-model="newProject.responsiblePersonId" filterable remote remote-show-suffix
          placeholder="请选择负责人"
          :remote-method="remoteMethod"
          style="width: 240px"
        >
          <el-option
              v-for="item in developerOptionsRemoteMethod.listData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="技术负责人">
        <el-select v-model="newProject.skillResponsiblePersonId" filterable remote remote-show-suffix
            placeholder="请选择负责人"
            :remote-method="remoteMethod"
            style="width: 240px"
        >
          <el-option
              v-for="item in developerOptionsRemoteMethod.listData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品负责人">
        <el-select v-model="newProject.productResponsiblePersonId" filterable remote remote-show-suffix
            placeholder="请选择负责人"
            :remote-method="remoteMethod"
            style="width: 240px"
        >
          <el-option
              v-for="item in developerOptionsRemoteMethod.listData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="项目预期成本">
        <el-input-number v-model="newProject.scheduleCost" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="项目预期工期">
        <el-input-number v-model="newProject.scheduleWorkday" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="项目预期工作量">
        <el-input-number v-model="newProject.scheduleWorkload" :precision="6" :step="0.1" :max="1000000000" style="width: 200px;" />
      </el-form-item>
      <el-form-item label="优先级">
        <el-select v-model="newProject.priorityLevel" clearable placeholder="请选择优先级" style="width: 180px" >
          <el-option v-for="item in priorityLevelOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker v-model="newProject.lastEndTime" type="datetime" placeholder="截止时间" style="width: 180px"/>
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

<script lang="ts" setup>
  import { ref, reactive, getCurrentInstance, onMounted, inject } from 'vue';
  import {useRouter} from 'vue-router'
  import {Search, Refresh, Edit, ZoomIn} from "@element-plus/icons-vue";
  import {ElNotification} from 'element-plus'

  //用于刷新数据
  const doRefresh = inject("reload")
  const router = useRouter()
  const {proxy} = getCurrentInstance()
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
  const priorityLevelOptions = [
    {
      value: 1,
      label: '紧急'
    },
    {
      value: 2,
      label: '高'
    },
    {
      value: 3,
      label: '中'
    },
    {
      value: 4,
      label: '低'
    },
  ]
  let tableData = reactive({
    total: 0,
    projectData: []
  })
  let queryInfo = reactive({
    pageSize:10,
    pageNow:1,
    name: null,
    version:null,
    responsiblePersonName:null,
    skillResponsiblePersonId:null,
    productResponsiblePersonId:null,
    state:null,
    priorityLevel:null,
    createPersonId:null,
    startLastEndTime:null,
    endLastEndTime:null
  })
  let dialogConfig = reactive({
    visible: false,
    title:'',
    disable: false,
    type:''
  })
  let newProject = reactive({
    id:null,
    name:null,
    version:null,
    info:null,
    responsiblePersonId:null,
    skillResponsiblePersonId:null,
    productResponsiblePersonId:null,
    scheduleTotalStartTime:null,
    scheduleTotalEndTime:null,
    productScheduleStartTime:null,
    productScheduleEndTime:null,
    designScheduleStartTime:null,
    designScheduleEndTime:null,
    developScheduleStartTime:null,
    developScheduleEndTime:null,
    testScheduleStartTime:null,
    testScheduleEndTime:null,
    onlineScheduleEndTime:null,
    lastEndTime:null,
    state:null,
    scheduleWorkday:null,
    actualWorkday:null,
    scheduleWorkload:null,
    actualWorkload:null,
    scheduleCost:null,
    actualCost:null,
    investedWorkday:null,
    investedWorkload:null,
    investedCost:null,
    priorityLevel:null,
    createTime:null,
    createPersonId:null,
  })
  let developerOptions = reactive([]) // 弃用
  let developerOptionsRemoteMethod = reactive({
    listData: []
  })


  // 初始化所有开发人员
  // 方法弃用
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
        developerOptions.push({
          label:developers[i].name,
          value:developers[i].id
        })
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function showState(data){
    if(data == 1) return '未开始'
    if(data == 2) return '进行中'
    if(data == 3) return '已完成'
    if(data == 4) return '已暂停'
  }
  function reverseState(data){
    if(data == '未开始') return 1
    if(data == '进行中') return 2
    if(data == '已完成') return 3
    if(data == '已暂停') return 4
  }
  function showPriorityLevel(data){
    if(data == 1) return '紧急'
    if(data == 2) return '高'
    if(data == 3) return '中'
    if(data == 4) return '低'
  }
  function reversePriorityLevel(data) {
    if(data == '紧急') return 1
    if(data == '高') return 2
    if(data == '中') return 3
    if(data == '低') return 4
  }
  function showTable(myData){
    for(let i = 0; i < myData.length; i++){
      myData[i].priorityLevelName = showPriorityLevel(myData[i].priorityLevel)
      myData[i].stateName = showState(myData[i].state)
    }
  }
  function reverseRow(row) {
    row.priorityLevel = reversePriorityLevel(row.priorityLevel)
    row.state = reverseState(row.state)
  }

  function showDialog(type) {
    if(type === 'add'){
      dialogConfig.type = 'add'
      dialogConfig.title = '新增项目'
      dialogConfig.visible = true
      dialogConfig.disable = false
    }
    if(type === 'edit'){
      dialogConfig.type = 'edit'
      dialogConfig.title = '编辑项目'
      dialogConfig.visible = true
      dialogConfig.disable = false
    }
    if(type === 'detail'){
      dialogConfig.type = 'detail'
      dialogConfig.title = '项目详情'
      dialogConfig.visible = true
      dialogConfig.disable = true
    }
  }
  function clearNewProject(){
    Object.assign(newProject, {
      id:null,
      name:null,
      version:null,
      info:null,
      responsiblePersonId:null,
      skillResponsiblePersonId:null,
      productResponsiblePersonId:null,
      scheduleTotalStartTime:null,
      scheduleTotalEndTime:null,
      productScheduleStartTime:null,
      productScheduleEndTime:null,
      designScheduleStartTime:null,
      designScheduleEndTime:null,
      developScheduleStartTime:null,
      developScheduleEndTime:null,
      testScheduleStartTime:null,
      testScheduleEndTime:null,
      onlineScheduleEndTime:null,
      lastEndTime:null,
      state:null,
      scheduleWorkday:null,
      actualWorkday:null,
      scheduleWorkload:null,
      actualWorkload:null,
      scheduleCost:null,
      actualCost:null,
      investedWorkday:null,
      investedWorkload:null,
      investedCost:null,
      priorityLevel:null,
      createTime:null,
      createPersonId:null,

      stateName:null,
      priorityLevelName:null
    })
  }

  function handleSearch(){
    proxy.$http({
      url: "/schedule/project/getProjectListBySearch",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.total=res.data.data.totalRows
      let myData = res.data.data.rowData
      showTable(myData)
      tableData.projectData = myData
      // console.log(res)
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function handleSubmit(){
    proxy.$http({
      url: "/schedule/project",
      method: "post",
      data: newProject
    }).then((res)=>{
      if(res.data.code===200){
        ElNotification({
          title: dialogConfig.type==="add"?'添加成功':'修改成功',
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
  function handleShowDetail(row){
    Object.assign(newProject, row)
    showDialog('detail')
  }
  function handleEdit(row){
    Object.assign(newProject, row)
    showDialog('edit')
  }
  function handleDelete(row){
    proxy.$http({
      url: "/schedule/project/"+row.id,
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

  function handleShowHistory(row){
    router.push({
      path:'/projectHistory',
      query:{
        projectId:row.id
      }
    })
  }

  const loading = ref(false)
  function remoteMethod(query){
    developerOptionsRemoteMethod.listData = []
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
            developerOptionsRemoteMethod.listData.push({
              label: developerList[i].name,
              value: developerList[i].id
            })
          }
        }).catch((ex)=>{
          proxy.$message.error(ex.message)
        })
      }, 200)
    } else {
      developerOptionsRemoteMethod.listData = []
    }
  }

  onMounted(() => {
    handleSearch()
    // console.log("挂载完毕")
  })
  
</script>

<style scoped>
  .inputSearch {
    width: max-content;
    margin-right: 20px;
  }
</style>

<script lang="ts">
export default {
  name:'ProjectManage'
}
</script>