<template>
  <el-card shadow="never" style="height: 80px;">
    <div style="display: inline-block">
      <el-radio-group v-model="radio" size="large">
        <el-radio-button label="项目视图" value="project" />
        <el-radio-button label="系统视图" value="system" />
      </el-radio-group>
    </div>
    <div style="display: inline-block; margin-left: 670px">
      <el-button type="danger" onclick="gantt.exportToPDF();" :icon="Download">导出PDF</el-button>
      <el-button type="success" onclick="gantt.exportToExcel()" :icon="Download">导出Excel</el-button>
      <el-button type="primary" @click="exportImg" :icon="Download">导出图片</el-button>
    </div>

  </el-card>
  <el-scrollbar height="630px">
    <Gantt :tasks = "ganttTasks" style="height: 100px;" id="gantt" ref="ganttRef"></Gantt>
  </el-scrollbar>
</template>

<script setup>
import {getCurrentInstance, onMounted, reactive, ref, watch} from "vue"
import Gantt from "./components/ProjectGantt.vue"
import {Download} from "@element-plus/icons-vue";
import html2canvas from "html2canvas";

let radio = ref("project")
const ganttTasks = reactive({
  data: []
})

watch(radio, ()=>{
  if(radio.value == "project"){
    getProjectGanttTasks()
  } else {
    getGanttTasks()
  }
})

/**
 * @description 甘特图转canvas
 */
function exportImg(){
  html2canvas(document.querySelector("#gantt")).then(function (canvas) {
    downloadPng(canvas);
  });
};
/**
 * @description 下载canvas
 */
function downloadPng(el){
  // 创建一个新的a元素，设置其href为canvas的toDataURL方法，并添加download属性
  var link = document.createElement("a");
  link.href = el.toDataURL("image/png");
  link.download = `picture.png`;
  // 触发a元素的click事件以开始下载
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const {proxy} = getCurrentInstance()
function getGanttTasks(){
  ganttTasks.data=[]
  gantt.clearAll()
  proxy.$http({
    url: "/algorithm/display/system",
    method: "get",
  }).then((res)=>{
    let systemGanttTasks = res.data.data
    for(let i = 0; i < systemGanttTasks.length; i++){
      ganttTasks.data.push({
        id: systemGanttTasks[i].systemId,
        text: "总时间",
        developerName: systemGanttTasks[i].developerName,
        name: systemGanttTasks[i].systemName,
        open: true
      })
      let tasks = systemGanttTasks[i].ganttTasks
      for(let j = 0; j < systemGanttTasks[i].ganttTasks.length; j++){
        ganttTasks.data.push({

          id: systemGanttTasks[i].ganttTasks[j].id,
          // id: systemGanttTasks[i].ganttTasks[j].systemId,
          text: systemGanttTasks[i].ganttTasks[j].systemName,
          developerId: systemGanttTasks[i].ganttTasks[j].developerId,
          developerName: systemGanttTasks[i].ganttTasks[j].developerName,
          name: systemGanttTasks[i].ganttTasks[j].developerName,
          start_date: systemGanttTasks[i].ganttTasks[j].startDate,
          end_date: systemGanttTasks[i].ganttTasks[j].endDate,
          workload: systemGanttTasks[i].ganttTasks[j].workload,
          saturation: systemGanttTasks[i].ganttTasks[j].saturation,
          parent: systemGanttTasks[i].systemId,
        })
      }
    }
    gantt.parse(ganttTasks)
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

function getProjectGanttTasks(){
  ganttTasks.data=[]
  gantt.clearAll()
  proxy.$http({
    url: "/algorithm/display/project",
    method: "get",
  }).then((res)=>{
    let projectGanttTasks = res.data.data
    for(let i = 0; i < projectGanttTasks.length; i++){
      ganttTasks.data.push({
        id: projectGanttTasks[i].projectId,
        text: "总时间",
        name: projectGanttTasks[i].projectName,
        open: true
      })
      for(let j = 0; j < projectGanttTasks[i].ganttTasks.length; j++){
        ganttTasks.data.push({
          id: projectGanttTasks[i].ganttTasks[j].id,
          // id: systemGanttTasks[i].ganttTasks[j].systemId,
          text: projectGanttTasks[i].ganttTasks[j].systemName,
          developerNames: projectGanttTasks[i].ganttTasks[j].developerNames,
          name: projectGanttTasks[i].ganttTasks[j].systemName,
          start_date: projectGanttTasks[i].ganttTasks[j].startDate,
          end_date: projectGanttTasks[i].ganttTasks[j].endDate,
          workload: projectGanttTasks[i].ganttTasks[j].workload,
          saturation: projectGanttTasks[i].ganttTasks[j].saturation,
          parent: projectGanttTasks[i].projectId,
        })
      }
    }
    gantt.parse(ganttTasks)
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}

onMounted(() => {
  getProjectGanttTasks();
})
</script>

<script>
export default {
  name: "ScheduleProjectView"
}
</script>

<style lang="scss">

:deep(.gantt_task_line) {
  background-color: #fff;
  border-color: rgb(220 223 230 / 100%);
  border-radius: 4px;

  .gantt_task_content {
    z-index: 1;
    overflow: initial;
    color: #000;
  }

  .gantt_task_progress_wrapper {
    z-index: 2;
    border-radius: 4px;
  }
}

:deep(.gantt_task_progress) {
  background-color: transparent;
}

:deep(.real-task) {
  z-index: 3;
  //background: url("../../../../../assets/icons/diagonal-line.svg") repeat;
  border: 1px solid rgb(220 223 230 / 100%);
  border-radius: 4px;
  opacity: 0.5;
}

:deep(.gantt_marker) {
  z-index: 99;
}

.gantt_tooltip{
  top: 50px!important;
  left: 1500px!important;
  font-size: 16px!important;
  padding: 15px!important;
  border-radius: 5px!important;
  box-shadow:3px 3px 0 #6e6e6e;
}
</style>