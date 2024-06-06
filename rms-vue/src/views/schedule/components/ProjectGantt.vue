<template>
  <div style="width: 100%;" ref="ganttRef"></div>
</template>

<script lang="ts" setup>
import { gantt } from 'dhtmlx-gantt'
import { onMounted, ref } from 'vue';

// 挂载ref
const ganttRef = ref()
const props = defineProps<{
  // 任务对象
  tasks?: any
  // 显示列设置
  columns?: Array<any>,
  // 显示单位
  scaleUnit?: {
    type: String,
    default: 'day' // “minute”, “hour”, “day”, “week”, “quarter”, “month”, “year”
  },
  // 时间显示格式
  dateScale?: {
    type: String,
    default: '%Y-%m-%d'
  }
}>()

onMounted(() => {
  // 清空之前的配置
  gantt.clearAll()
  // 默认配置
  gantt.config.xml_date = '%Y-%m-%d'
  gantt.i18n.setLocale('cn') // 设置中文
  gantt.config.readonly = true // 设置为不可编辑
  gantt.config.autosize = true//自适应尺寸
  gantt.config.autofit = true// 表格列宽自适应
  gantt.config.autoscroll = true// 把任务或者连线拖拽到浏览器屏幕外时，自动触发滚动效果
  gantt.config.drag_progress = false //取消任务进度条进度拖动
  //时间栏配置
  gantt.config.scales = [
    { unit: 'month', step: 1, format: '%Y年%m月' },
    { unit: 'day', step: 1, format: '%m/%d' },
  ]
  gantt.config.scale_height = 60
  // 自定义左侧表格列
  const ganttColumns = ref([
    { align: 'left', name: 'name', label: '名称', tree: true },
    { align: 'center', name: 'end_date', label: '计划完成时间' }
  ])
  gantt.config.columns = ganttColumns.value;

  gantt.plugins({tooltip: true});
  gantt.templates.tooltip_text = function (start, end, task) {
    return (
        "<b>标题:</b> " +
        task.text +
        "<br/><span>开始:</span> " +
        gantt.templates.tooltip_date_format(start) +
        "<br/><span>结束:</span> " +
        gantt.templates.tooltip_date_format(end) +
        "<br/><span>进度:</span> " +
        Math.round(task.progress * 100) +
        "%"+
        "<b>标题:</b> " +
        task.text +
        "<br/><span>开始:</span> " +
        gantt.templates.tooltip_date_format(start) +
        "<br/><span>结束:</span> " +
        gantt.templates.tooltip_date_format(end) +
        "<br/><span style='margin-bottom: 0'>进度:</span> " +
        Math.round(task.progress * 100) +
        "%"
    );
  };

  // 初始化甘特图
  gantt.init(ganttRef.value)
  // 渲染数据
  gantt.parse(props.tasks)
})
</script>

<script lang="ts">
export default {
  name: "ProjectGantt"
}
</script>

<style >
@import "/node_modules/dhtmlx-gantt/codebase/dhtmlxgantt.css"
</style>