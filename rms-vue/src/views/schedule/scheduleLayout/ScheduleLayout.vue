<template>
  <el-container style="background-color: rgba(243,243,244,0.69);">
    <el-aside style="width: fit-content">
      <schedule-aside></schedule-aside>
    </el-aside>
    <el-main style="padding: 10px">
      <router-view v-if="isRouterAlive"></router-view>
    </el-main>
  </el-container>
</template>

<script>
  export default {
  name: "scheduleLayout"
  }
</script>

<script setup>
  // import ScheduleAside from './ScheduleAside.vue'
  import ScheduleAside from './SecondAside.vue'
  import {nextTick, provide, ref} from "vue";

  // 刷新工具类-用于刷新页面
  const  isRouterAlive = ref(true);
  const reload = ()=>{
    isRouterAlive.value = false;
    nextTick(()=>{
      isRouterAlive.value = true;
    });
  };
  provide("reload",reload);

</script>

<style scoped>

</style>