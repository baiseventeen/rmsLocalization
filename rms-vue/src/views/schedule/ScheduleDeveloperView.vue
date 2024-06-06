<template>
  <el-card shadow="never" style="height: 80px;">
    <el-form :inline="true">
      <el-form-item>
       <el-text size="large">人力资源排期视图</el-text>
      </el-form-item>
      <el-form-item style="margin-left: 700px">
        <el-button type="danger" onclick="gantt.exportToPDF();" :icon="Download">导出PDF</el-button>
        <el-button type="success" onclick="gantt.exportToExcel()" :icon="Download">导出Excel</el-button>
        <el-button type="primary" @click="exportImg" :icon="Download">导出图片</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <el-scrollbar height="630px">
    <Gantt :tasks = "ganttTasks" style="height: 100px;" id="gantt" ref="ganttRef"></Gantt>
  </el-scrollbar>
</template>

<script setup>
import {getCurrentInstance, onBeforeUnmount, onMounted, onUnmounted, reactive} from "vue"
import Gantt from "./components/DeveloperGantt.vue"
import {Download} from "@element-plus/icons-vue";
import html2canvas from "html2canvas";
import  "http://export.dhtmlx.com/gantt/api.js"

const ganttTasks = reactive({
  data: []
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
  gantt.clearAll()
  proxy.$http({
    url: "/algorithm/display/developer",
    method: "get",
  }).then((res)=>{
    let developerGanttTasks = res.data.data
    for(let i = 0; i < developerGanttTasks.length; i++){
      ganttTasks.data.push({
        id: developerGanttTasks[i].developerId,
        text: "总时间",
        developerName: developerGanttTasks[i].developerName,
        name: developerGanttTasks[i].developerName,
        open: true,
        workload: '无',
        saturation: "无"
      })
      for(let j = 0; j < developerGanttTasks[i].ganttTasks.length; j++){
        ganttTasks.data.push({

          id: developerGanttTasks[i].ganttTasks[j].id,
          // id: developerGanttTasks[i].ganttTasks[j].systemId,
          text: developerGanttTasks[i].ganttTasks[j].text,
          developerId: developerGanttTasks[i].ganttTasks[j].developerId,
          developerName: developerGanttTasks[i].ganttTasks[j].developerName,
          name: developerGanttTasks[i].ganttTasks[j].text,
          start_date: developerGanttTasks[i].ganttTasks[j].startDate,
          end_date: developerGanttTasks[i].ganttTasks[j].endDate,
          workload: developerGanttTasks[i].ganttTasks[j].workload,
          saturation: developerGanttTasks[i].ganttTasks[j].saturation,
          parent: developerGanttTasks[i].developerId,
          progress: 0.4
        })
      }
    }
    gantt.parse(ganttTasks)
  }).catch((ex)=>{
    proxy.$message.error(ex.message)
  })
}


onMounted(() => {
  getGanttTasks();
})

</script>

<script>
export default {
  name: "ScheduleDeveloperView"
}
</script>

<style lang="scss">
/* task */

  .gantt_task_line {
    //background-color: white;
    //border-color: rgb(220 223 230 / 100%);
    border-radius: 10px;
  }

 .gantt_task_content {
    color: white;
   font-size: 14px;
  }

  .gantt_task_line .gantt_task_progress {
  background-color: #68d390;
  }

  .gantt_tooltip{
  top: 50px!important;
  left: 1200px!important;
  //max-width: 10%;
  font-size: 16px!important;
  padding: 15px!important;
  border-radius: 5px!important;
  box-shadow:3px 3px 0 #6e6e6e;
  }
</style>