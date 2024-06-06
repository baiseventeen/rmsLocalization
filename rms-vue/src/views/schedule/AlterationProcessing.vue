<template>


  <el-card style="margin-top: 5px;height: 625px;" shadow="never">
    <div style="margin-bottom: 10px">
      <el-button type="success" @click="doRefresh" :icon="Refresh">刷新</el-button>
    </div>

    <div>
      <el-table :data="tableData.investmentData" style="width: 100%">
        <el-table-column prop="requestDeveloperName" label="请求人"></el-table-column>
        <el-table-column prop="requestDate" label="请求时间"></el-table-column>
        <el-table-column prop="processDeveloperName" label="PM处理人" v-if="role == 'ROLE_ADMIN'"></el-table-column>
        <el-table-column prop="requestDate" label="PM处理时间" v-if="role == 'ROLE_ADMIN'"></el-table-column>
        <el-table-column label="类型">
          <template #default="scope">
            <el-tag disable-transitions v-if="scope.row.type == 1 || scope.row.type == 2">
              {{ scope.row.typeName }}
            </el-tag>
            <el-tag disable-transitions v-if="scope.row.type == 3 || scope.row.type == 4" type="success">
              {{ scope.row.typeName }}
            </el-tag>
            <el-tag disable-transitions v-if="scope.row.type == 5 || scope.row.type == 6" type="warning">
              {{ scope.row.typeName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="请求内容"></el-table-column>
        <el-table-column  label="操作">
          <template #default="scope">
            <el-button type="success" @click="approve(scope.row)">批准</el-button>
            <el-popconfirm title="确定拒绝吗？" @confirm="reject(scope.row)">
              <template #reference>
                <el-button type="danger">拒绝</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="block" style="margin-top: 10px">
      <el-pagination
          small
          style="font-size: 16px;margin-top: 10px;margin-left: 450px"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNow"
          :page-size="queryInfo.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="tableData.total">
      </el-pagination>
    </div>
  </el-card>

</template>

<script setup lang="ts">
import {ref, reactive, getCurrentInstance, inject, onMounted, watch} from 'vue';
import {Search,Refresh} from "@element-plus/icons-vue";
//用于刷新数据
const doRefresh = inject("reload")
const {proxy} = getCurrentInstance()
let role = window.sessionStorage.getItem('role')
let newInvestment = reactive({
  id:null,
  level:null,
  processDate:null,
  processDeveloperId:null,
  processDeveloperName:null,
  reprocessDate:null,
  reprocessDeveloperId:null,
  reprocessDeveloperName:null,
  requestDate:null,
  requestDeveloperId:null,
  requestDeveloperName:null,
  state:null,
})

let queryInfo = reactive({
  pageNow: 1,
  pageSize:5,
})

let tableData = reactive({
  investmentData: [],
  total: 0
})

function cleanNewInvestment() {
  Object.assign(newInvestment, {
    id:null,
    level:null,
    processDate:null,
    processDeveloperId:null,
    processDeveloperName:null,
    reprocessDate:null,
    reprocessDeveloperId:null,
    reprocessDeveloperName:null,
    requestDate:null,
    requestDeveloperId:null,
    requestDeveloperName:null,
    state:null,
    type:null,
    content:null,
  })
}
let typeOptions = [
  {
    label: "退出计划",
    value: "1"
  },
  {
    label: "加入计划",
    value: "2"
  },{
    label: "修改系统信息",
    value: "3"
  },{
    label: "重新排期",
    value: "4"
  },{
    label: "岗位等级变更",
    value: "5"
  },{
    label: "岗位种类变更",
    value: "6"
  }
]

function handleSearch(){
  let role = window.sessionStorage.getItem("role")
  if(role == "ROLE_ADMIN"){
    proxy.$http({
      url: "/algorithm/alteration/admin",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      tableData.investmentData = res.data.data.rowData
      for(let i = 0; i < tableData.investmentData.length; i++){
        for(let j = 0; j < typeOptions.length; j++){
          if(tableData.investmentData[i].type == typeOptions[j].value){

            tableData.investmentData[i].typeName = typeOptions[j].label
            break
          }
        }
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
  if(role == "ROLE_PROJECT_MANAGER"){
    proxy.$http({
      url: "/algorithm/alteration/manager",
      method: "post",
      data: queryInfo
    }).then((res)=>{
      console.log(res.data)
      tableData.investmentData = res.data.data.rowData
      console.log(tableData.investmentData.type)
      for(let i = 0; i < tableData.investmentData.length; i++){
        for(let j = 0; j < typeOptions.length; j++){
          if(tableData.investmentData[i].type == typeOptions[j].value){

            tableData.investmentData[i].typeName = typeOptions[j].label
            break
          }
        }
      }
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
}

function approve(row){
  cleanNewInvestment()
  Object.assign(newInvestment, row)
  if(role == "ROLE_PROJECT_MANAGER"){
    newInvestment.level = 2
    newInvestment.processDeveloperId = window.sessionStorage.getItem("developerId")
  }
  if(role == "ROLE_ADMIN"){
    newInvestment.level = 2
    newInvestment.reprocessDeveloperId = window.sessionStorage.getItem("developerId")
    newInvestment.state = 1
  }
  proxy.$http({
    url: "/algorithm/alteration/update",
    method: "post",
    data: newInvestment
  }).then((res)=>{
    doRefresh();
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
    doRefresh();
  })
}

function reject(row){
  cleanNewInvestment()
  Object.assign(newInvestment, row)
  newInvestment.state = 2
  proxy.$http({
    url: "/algorithm/alteration/update",
    method: "post",
    data: newInvestment
  }).then((res)=>{
    doRefresh();
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
    doRefresh();
  })
}

const handleCurrentChange =(index)=>{
  queryInfo.pageNow=index;
  handleSearch();
}

onMounted(() => {
  handleSearch();
})

</script>

<script lang="ts">
export default {
  name: "AlterationProcessing"
}
</script>

<style scoped>

</style>