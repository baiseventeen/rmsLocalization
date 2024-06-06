<template>


  <el-card style="margin-top: 5px;height: 625px;" shadow="never">
    <el-button :icon="Refresh" @click="doRefresh" circle type="success" style="margin-right: 10px; margin-bottom: 10px"></el-button>
    <el-select v-model="developerId" filterable remote remote-show-suffix
               placeholder="请选择负责人"
               :remote-method="remoteMethod"
               style="width: 240px;margin-bottom: 10px"
    >
      <el-option
          v-for="item in developerOptionsRemoteMethod.listData"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
    <el-button type="warning" :icon="Message" style="margin-left: 660px; margin-bottom: 10px" @click="sendEMail" :disabled="sendEmailDisabled">发送邮件通知</el-button>
    <el-table :data="tableData.investmentData" height="500" style="width: 100%">
      <el-table-column type="index" ></el-table-column>
      <el-table-column prop="date" label="投入日期" sortable/>
      <el-table-column prop="developerName" label="开发人员" />
      <el-table-column prop="systemName" label="系统名称" />
      <el-table-column prop="workload" label="投入工作量" />
      <el-table-column prop="workHour" label="投入小时数" sortable/>
      <el-table-column label="操作" align="left" width="330px">
        <template #default="scope">
          <el-button @click="handleShowDetail(scope.row)" type="primary">
            <el-icon style="margin-right: 3px"><ZoomIn/></el-icon>
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

</template>

<script setup lang="ts">
import {ref, reactive, getCurrentInstance, inject, onMounted, watch} from 'vue';
import {Refresh, ZoomIn, Message} from "@element-plus/icons-vue";

//用于刷新数据
const doRefresh = inject("reload")
const {proxy} = getCurrentInstance()
const loading = ref(false)
let developerId = ref("")
// 用于承载开发人员的远程方法
let developerOptionsRemoteMethod = reactive({
  listData: []
})
let sendEmailDisabled = ref(false)

let tableData = reactive({
  investmentData: []
})

function handleSearch(){
  proxy.$http({
    url: "/algorithm/progress",
    method: "post",
    data: {
      developerId: developerId.value
    }
  }).then((res)=>{
    tableData.investmentData = res.data.data
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

function remoteMethod(query){
  developerOptionsRemoteMethod.listData = []
  if (query) {
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

function sendEMail(){
  sendEmailDisabled.value = true
  proxy.$http({
    url: "/algorithm/progress/sendEMail",
    method: "get",
  }).then((res)=>{
    proxy.$message.success("发送成功")
    sendEmailDisabled.value = false
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

onMounted(() => {
  handleSearch();
})

watch(developerId, ()=>{
  console.log(developerId.value)
  handleSearch()
})
</script>

<script lang="ts">
export default {
  name: "ActualProgress"
}
</script>

<style scoped>

</style>