<script setup>
  import SecondAside from "./SecondAside.vue";
  import {nextTick, provide, ref} from "vue";

  const  isRouterAlive = ref(true);
  const reload = ()=>{
    isRouterAlive.value = false;
    nextTick(()=>{
      isRouterAlive.value = true;
    });
  };
  provide("reload",reload);

</script>

<template>
  <el-container style="background-color: rgba(243,243,244,0.69);">
    <el-aside style="width: fit-content">
      <second-aside></second-aside>
    </el-aside>
    <el-main style="padding: 10px">
      <router-view v-if="isRouterAlive"></router-view>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>