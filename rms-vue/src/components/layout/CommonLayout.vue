<script setup>

import CommonHeader from "./CommonHeader.vue";
</script>

<template>
  <div class="wrapper">
    <el-container style="height: max-content">
      <el-header height="200" style="padding: 0;">
        <common-header></common-header>
      </el-header>
      <el-container style="z-index: 1;">
        <el-container>
          <el-main style="padding: 0 0 0 0;">
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  top:0;
  left: 0;
}
</style>