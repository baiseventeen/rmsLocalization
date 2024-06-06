<template>
  <div>
    <el-card style="margin-top: 5px;height: 625px;" shadow="never">
      <el-button :icon="Back" @click="doBack" circle></el-button>
      <el-button type="success">导入</el-button>
      <el-button type="warning">导出</el-button>
      <el-table :data="tableData.projectSystemHistoryData" height="500" style="width: 100%">
        <el-table-column type="index" ></el-table-column>
        <el-table-column prop="version" label="版本"/>
        <el-table-column prop="name" label="系统名称"/>
        <el-table-column prop="responsiblePersonName" label="系统负责人" />
        <el-table-column prop="difficultyRate" label="难度系数" />
        <el-table-column prop="developmentTechName" label="所用技术"/>
        <el-table-column label="操作" align="left" width="330px">
          <template #default="scope">
            <el-button @click="handleShowDetail(scope.row)" type="primary">
              <el-icon style="margin-right: 3px"><ZoomIn/></el-icon>
              详情
            </el-button>
            <el-button type="primary">
              <el-icon style="margin-right: 3px"><Refresh/></el-icon>
              恢复
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
  import {ref, reactive, onMounted, getCurrentInstance} from 'vue'
  const {proxy} = getCurrentInstance()
  import {Back, ZoomIn} from "@element-plus/icons-vue";
  import {useRoute, useRouter} from 'vue-router'

  let route = useRoute()
  let router = useRouter()
  let tableData = reactive({
    total: 0,
    projectSystemHistoryData: []
  })
  let queryInfo = reactive({
    projectId: null,
    systemId: null,
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
  let developerOptions = reactive([
    {
      label: "就花反般当从",
      value: "18eda11f053f401b87f032320068d61f"
    }
  ])

  function doBack(){
    router.push({
      path:'/projectSystemManage'
    })
  }
  function initQuery(){
    queryInfo.projectId = route.query.projectId
    queryInfo.systemId = route.query.systemId
  }
  function handleSearch(){
    proxy.$http({
      url: "/schedule/system/getProjectSystemHistroyListBySearch",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.total=res.data.data.totalRows
      let myData = res.data.data.rowData
      for(let i = 0; i < myData.length; i++){
        for(let j = 0; j < developmentTechOptions.length; j++){
          if(myData[i].developmentTech === developmentTechOptions[j].value){
            myData[i].developmentTechName = developmentTechOptions[j].label;
            break;
          }
        }
        for(let j = 0; j < developerOptions.length; j++){
          if(myData[i].responsiblePersonId === developerOptions[j].value){
            myData[i].responsiblePersonName = developerOptions[j].label;
            break;
          }
        }
      }
      tableData.projectSystemHistoryData = myData
      console.log(tableData.projectSystemHistoryData)
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function handleShowDetail(row){
    proxy.$message.info('TODO')
  }
  onMounted(()=>{
    initQuery()
    handleSearch()
  })
</script>

<style scoped>

</style>

<script lang="ts">
  export default {
    name:'ProjectHistory'
  }
</script>