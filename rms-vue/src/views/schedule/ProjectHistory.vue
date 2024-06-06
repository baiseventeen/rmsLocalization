<template>
  <div>
    <el-card style="margin-top: 5px;height: 625px;" shadow="never">
      <el-button :icon="Back" @click="doBack" circle></el-button>
      <el-button type="success">导入</el-button>
      <el-button type="warning">导出</el-button>
      <el-table :data="tableData.projectHistoryData" height="500" style="width: 100%">
        <el-table-column type="index" ></el-table-column>
        <el-table-column prop="version" label="版本"/>
        <el-table-column prop="name" label="项目名称"/>
        <el-table-column prop="info" label="项目信息" />
        <el-table-column prop="priorityLevel" label="优先级" />
        <el-table-column prop="responsiblePersonId" label="项目负责人" />
        <el-table-column prop="lastEndTime" label="截止时间" sortable/>
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
  import {Back, Refresh, ZoomIn} from "@element-plus/icons-vue";
  import {useRoute, useRouter} from 'vue-router'

  let route = useRoute()
  let router = useRouter()
  let tableData = reactive({
    total: 0,
    projectHistoryData: []
  })
  let queryInfo = reactive({
    projectId: null,
    version: ''
  })

  function doBack(){
    router.push({
      path:'/projectManage'
    })
  }
  function initQuery(){
    queryInfo.projectId = route.query.projectId
  }
  function handleSearch(){
    proxy.$http({
      url: "/schedule/project/getProjectHistoryListByProjectId",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.total=res.data.data.totalRows
      let myData = res.data.data.rowData
      // showTable(myData)
      tableData.projectHistoryData = myData
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  function handleShowDetail(){
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