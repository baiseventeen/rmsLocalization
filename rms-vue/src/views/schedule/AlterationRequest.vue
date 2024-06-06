<template>
  <el-card style="margin: 100px auto; width: 500px; border-radius: 10px; padding: 20px">
    <el-form :model="requestInfo" label-width="auto" style="max-width: 500px">
      <div style="margin-bottom: 15px; font-size: 20px">提交变更请求</div>
      <el-form-item label="请求类型">
        <el-radio-group v-model="requestInfo.type">
          <el-radio value="1" name="type">
            退出计划
          </el-radio>
          <el-radio value="2" name="type">
            加入计划
          </el-radio>
          <el-radio value="3" name="type">
            修改系统信息
          </el-radio>
          <el-radio value="4" name="type">
            重新排期
          </el-radio>
          <el-radio value="5" name="type">
            岗位等级变更
          </el-radio>
          <el-radio value="6" name="type">
            岗位种类变更
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="变更理由">
        <el-input v-model="requestInfo.content" type="textarea" :autosize="{ minRows: 5, maxRows: 5 }" style="width: 360px"/>
      </el-form-item>
      <el-form-item style="margin-left: 290px">
        <el-button @click="clean">清空</el-button>
        <el-button type="primary" @click="handleSubmit">确认</el-button>
      </el-form-item>
    </el-form>
  </el-card>

</template>

<script setup>
import {reactive, getCurrentInstance} from "vue";
const {proxy} = getCurrentInstance()

let requestInfo = reactive({
  type: null,
  content: "",
  requestDeveloperId: window.sessionStorage.getItem("developerId"),
  level: window.sessionStorage.getItem("role") == "ROLE_USER" ? 1 : 2
})

function clean(){
  Object.assign(requestInfo, {
    type: null,
    content: "",
    requestDeveloperId: window.sessionStorage.getItem("developerId"),
    level: window.sessionStorage.getItem("role") == "ROLE_USER" ? 1 : 2
  })
}

function handleSubmit(){
  proxy.$http({
    url: "/algorithm/alteration/add",
    method: "post",
    data: requestInfo
  }).then((res)=>{
    proxy.$message.success("提交成功")
    clean()
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
    clean()
  })
}
</script>

<script>
export default {
  name: "AlterationRequest"
}
</script>

<style scoped>

</style>