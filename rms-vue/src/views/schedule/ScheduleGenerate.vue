<template>
  <el-card shadow="never" >
    <el-row>
      <el-col :span="12"><div style="font-size: larger; margin-bottom: 20px; margin-left: 20px">
        请选择项目及系统
      </div>
        <div style="margin-bottom: 20px;; margin-left: 20px">
          <el-button type="primary" @click="setCheckedProjectKeys">全选</el-button>
          <el-button type="primary" @click="resetCheckedProject">清空</el-button>
        </div>
        <div>
          <el-scrollbar height="350px" style="max-width: 508px; ">
            <el-tree
                ref="treeProjectRef"
                style="max-width: 500px; "
                :data="projectData"
                show-checkbox
                node-key="id"
                highlight-current
                :props="defaultProps"
                @node-click="handleProjectNodeClick"
            />
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="12"><div style="font-size: larger; margin-bottom: 20px; margin-left: 20px">
          请选择开发人员
        </div>
          <div style="margin-bottom: 20px;; margin-left: 20px">
            <el-button type="primary" @click="setCheckedDeveloperKeys">全选</el-button>
            <el-button type="primary" @click="resetCheckedDeveloper">清空</el-button>
          </div>
          <div>
            <el-scrollbar height="350px" style="max-width: 508px; ">
              <el-tree
                  ref="treeDeveloperRef"
                  style="max-width: 500px; "
                  :data="developerData"
                  show-checkbox
                  node-key="id"
                  highlight-current
                  :props="defaultProps"
                  @node-click="handleDeveloperNodeClick"
              />
            </el-scrollbar>
          </div>
      </el-col>
    </el-row>
    <el-divider content-position="left">
      <span style="font-size: larger;">设置参数</span>
    </el-divider>
    <el-row style="height: 120px;">
      <el-form :inline="true" style="margin-top: 40px">
        <el-form-item label="每日最大工作饱和度">
          <el-input-number v-model="scheduleParams.maxDailyWorkSaturation" :precision="2" :step="0.1" :max="1.5" :min="1" style="width: 200px;"/>
        </el-form-item>
        <el-form-item label="平均最大工作饱和度">
          <el-input-number v-model="scheduleParams.maxAverageWorkSaturation" :precision="2" :step="0.1" :max="1.5" :min="1" style="width: 200px;"/>
        </el-form-item>
        <br>
        <el-form-item label="计划开始时间" class="inputSearch">
          <el-date-picker v-model="scheduleParams.startTime" type="date" placeholder="开始时间" style="width: 180px"/>
        </el-form-item>
          <el-form-item>
          <el-button :icon="Cpu" type="primary" @click="openFullScreen">生成计划</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </el-card>

  <el-dialog v-model="projectVisible" title="项目详情" width="500" @close="clearNewProject">
    <el-form :model="newProject" disabled>
      <el-form-item label="项目名称">
        <el-input v-model="newProject.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="项目信息">
        <el-input v-model="newProject.info" placeholder="请输入信息"></el-input>
      </el-form-item>
      <el-form-item label="项目负责人">
        <el-input v-model="newProject.responsiblePersonId" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="技术负责人">
        <el-input v-model="newProject.skillResponsiblePersonId" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="产品负责人">
        <el-input v-model="newProject.productResponsiblePersonId" placeholder="请输入名称"></el-input>
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
        <el-input v-model="newProject.priorityLevel" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker v-model="newProject.lastEndTime" type="datetime" placeholder="截止时间" style="width: 180px"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="projectVisible = false">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="projectSystemVisible" title="详情" width="500" @close="clearNewProjectSystem()">
    <el-form :model="newProjectSystem" disabled>
      <el-form-item label="系统名称">
        <el-input v-model="newProjectSystem.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="所属项目">
        <el-input v-model="newProjectSystem.projectName"></el-input>
      </el-form-item>
      <el-form-item label="系统负责人">
        <el-input v-model="newProjectSystem.responsiblePersonId"></el-input>
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
        <el-input v-model="newProjectSystem.developmentTech"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="projectSystemVisible = false">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="developerVisible" style="width: fit-content" title="开发人员" @close="clearNewDeveloper">
    <el-form label-position="left" label-width="auto" disabled>
      <el-form-item label="姓名">
        <el-input v-model="newDeveloper.name" placeholder="请输入姓名" clearable/>
      </el-form-item>
      <el-form-item label="生日">
        <el-date-picker v-model="newDeveloper.birthday" clearable/>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="newDeveloper.phone" clearable />
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker v-model="newDeveloper.onboardingTime" clearable/>
      </el-form-item>
      <el-form-item label="毕业院校">
        <el-input v-model="newDeveloper.college" clearable/>
      </el-form-item>
      <el-form-item label="学历">
        <el-input v-model="newDeveloper.educationBackground" clearable/>
      </el-form-item>
      <el-form-item label="岗位(TODO)">
        <el-select disabled placeholder="">
        </el-select>
      </el-form-item>
      <el-form-item label="月薪">
        <el-input v-model="newDeveloper.salary" clearable/>
      </el-form-item>
      <el-form-item label="所属小组(TODO)">
        <el-select disabled placeholder="">
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="developerVisible = false">确定</el-button>
      </div>
    </template>

  </el-dialog>

</template>

<script lang="ts" setup>
import {getCurrentInstance, onMounted, reactive, ref} from 'vue'
import { ElTree } from 'element-plus'
import { Cpu } from "@element-plus/icons-vue";
import { ElLoading } from 'element-plus'

interface Tree {
  id: number
  label: string
  children?: Tree[]
}
const defaultProps = {
  children: 'children',
  label: 'label',
}
const {proxy} = getCurrentInstance()

let projectData: Tree[] = reactive([])
let developerData: Tree[] = reactive([])

const treeProjectRef = ref<InstanceType<typeof ElTree>>()
const treeDeveloperRef = ref<InstanceType<typeof ElTree>>()

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
let newDeveloper = reactive({
  id: null,
  name: null,
  birthday: null,
  phone: null,
  onboardingTime: null,
  college: null,
  educationBackground: null,
  position: null,
  salary: null,
  groupId: null
})
let scheduleParams = reactive({
  maxDailyWorkSaturation: 1.5,
  maxAverageWorkSaturation:1.2,
  systemIdList:[],
  developerIdList:[],
  startTime:null
})

let projectSystemVisible = ref(false)
let projectVisible = ref(false)
let developerVisible = ref(false)

function clearNewDeveloper() {
  Object.assign(newDeveloper, {
    id: null,
    name: null,
    birthday: null,
    phone: null,
    onboardingTime: null,
    college: null,
    educationBackground: null,
    position: null,
    salary: null,
    groupId: null
  })
}
function clearNewProjectSystem(){
  Object.assign(newProjectSystem, {
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
}
function clearNewProject() {
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

// 算法加载展示
const openFullScreen = () => {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  scheduleGenerate()
  loading.close()
}
function scheduleGenerate() {
  Object.assign(scheduleParams, {
    maxDailyWorkSaturation: 1.5,
    maxAverageWorkSaturation:1.2,
    systemIdList:[],
    developerIdList:[]
  })
  getCheckedProjectKeys()
  getCheckedDeveloperKeys()
  proxy.$http({
    url: "/algorithm/algorithm/scheduleGenerate",
    method: "post",
    data: scheduleParams
  }).then((res)=>{
    console.log(res.data)
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

function handleProjectNodeClick(nodeObject, node, treeNode, event) {
  if(nodeObject.id.split("-", 1)[0] === 'SYS'){
    proxy.$http({
      url: "/schedule/system/"+nodeObject.id,
      method: "get",
    }).then((res)=>{
      Object.assign(newProjectSystem, res.data.data)
      projectSystemVisible.value = true
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  } else{
    proxy.$http({
      url: "/schedule/project/"+nodeObject.id,
      method: "get",
    }).then((res)=>{
      Object.assign(newProject, res.data.data)
      projectVisible.value = true
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
}

function handleDeveloperNodeClick(nodeObject, node, treeNode, event){
  proxy.$http({
    url: "/modeling/developerManage/page",
    method: "post",
    data: {
      pageSize:1,
      pageNow:1,
      id:nodeObject.id
    }
  }).then((res)=>{
    Object.assign(newDeveloper, res.data.data.rowData[0])
    console.log(res.data.data.rowData)
    console.log(newDeveloper)
    developerVisible.value = true
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

function initProjectData(){
  proxy.$http({
    url: "/schedule/project/getProjectListBySearch",
    method: "post",
    data:{
      pageSize:100,
      pageNow:1
    }
  }).then((res)=>{
    let myData = res.data.data.rowData.map(item=>({id:item.id, label:item.name}))
    Object.assign(projectData,myData)
    initProjectSystemDataInProjectData()
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}
// initProjectData方法内部使用的方法
function initProjectSystemDataInProjectData(){
  for(let i = 0; i < projectData.length; i++){
    proxy.$http({
      url: "/schedule/system/getProjectSystemListBySearch",
      method: "post",
      data: {
        pageSize:100,
        pageNow:1,
        projectId:projectData[i].id
      }
    }).then((res)=>{
      let myData = res.data.data.rowData.map(item=>({id:item.systemId, label:item.name}))
      projectData[i].children = myData
      console.log(projectData)
    }).catch((ex)=>{
      proxy.$message.error(ex.message)
    })
  }
}

function initDeveloperData(){
  proxy.$http({
    url: "/modeling/developerManage/page",
    method: "post",
    data:{
      pageSize:100,
      pageNow:1,
    }
  }).then((res)=>{
    let myData = res.data.data.rowData.map(item=>({id:item.id, label:item.name}))
    Object.assign(developerData,myData)
    // initProjectSystemDataInProjectData()
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

//获取选中的系统节点的id
const getCheckedProjectKeys = () => {
  let projectAndSystemIdList = treeProjectRef.value!.getCheckedKeys(false)
  for(let i = 0; i < projectAndSystemIdList.length; i++){
    if(projectAndSystemIdList[i].split("-", 1)[0] === 'SYS'){
      scheduleParams.systemIdList.push(projectAndSystemIdList[i])
    }
  }
  console.log(scheduleParams.systemIdList)
}
//获取选中的开发人员节点的id
const getCheckedDeveloperKeys = () => {
  scheduleParams.developerIdList = treeDeveloperRef.value!.getCheckedKeys(false)
  console.log(scheduleParams.developerIdList)
}
const resetCheckedProject = () => {
  treeProjectRef.value!.setCheckedKeys([], false)
}
const resetCheckedDeveloper = () => {
  treeDeveloperRef.value!.setCheckedKeys([], false)
}
const setCheckedProjectKeys = () => {
  let allProjectId = []
  for(let i = 0; i < projectData.length; i++){
    allProjectId.push(projectData[i].id)
  }
  treeProjectRef.value!.setCheckedKeys(allProjectId, false)
}
const setCheckedDeveloperKeys = () => {
  let allDeveloperId = []
  for(let i = 0; i < developerData.length; i++){
    allDeveloperId.push(developerData[i].id)
  }
  treeDeveloperRef.value!.setCheckedKeys(allDeveloperId, false)
}
function getCurrentProjectNode(){
  console.log(treeProjectRef.value!.getCurrentNode())
}
function getCurrentDeveloperNode(){
  console.log(treeDeveloperRef.value!.getCurrentNode())
}

onMounted(()=>{
  initProjectData()
  initDeveloperData()
  // initProjectSystemDataInProjectData()
})
</script>

<script lang="ts">
export default {
  name: "ScheduleGenerate"
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>