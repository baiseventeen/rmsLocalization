<template>
  <!-- 收放导航栏 -->
  <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
    <el-radio-button :value="false">展开</el-radio-button>
    <el-radio-button :value="true">收起</el-radio-button>
  </el-radio-group> -->

  <!-- menu中去除一些属性 -->
  <!-- default-active="2"
   :collapse="isCollapse"-->
   <!-- active-text-color="#fff" -->    
  <el-menu
      class="el-menu-vertical-demo"
      @select="handleSelect"
      :collapse="isCollapse"
      
      background-color="#50b7c1"
      default-active="1"
      text-color="#fff"
      active-text-color="#006699"
      style="min-height: 400vh"
  >
    <el-menu-item index="1" class="font-setting">
      <el-icon><icon-menu /></el-icon>
      <template #title>项目管理</template>
    </el-menu-item>
    <el-menu-item index="2" class="font-setting">
      <el-icon><document /></el-icon>
      <template #title>系统管理</template>
    </el-menu-item>
    <el-menu-item index="3" class="font-setting">
      <el-icon><location /></el-icon>
      <template #title>生成排期计划</template>
    </el-menu-item>
    <el-menu-item index="4" class="font-setting">
      <el-icon><location /></el-icon>
      <template #title>项目排期视图</template>
    </el-menu-item>
    <el-menu-item index="5" class="font-setting">
      <el-icon><location /></el-icon>
      <template #title>人力资源排期视图</template>
    </el-menu-item>
    <el-menu-item index="6" class="font-setting">
      <el-icon><location /></el-icon>
      <template #title>实时开发进度</template>
    </el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup>
  import {getCurrentInstance, ref} from 'vue'
  import {useRouter} from "vue-router"
  import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
  } from '@element-plus/icons-vue'

  const isCollapse = ref(false)
  const {proxy} = getCurrentInstance()
  const router = useRouter()

  function handleSelect(index){
    if(index == 1){
      router.push({
        path:'/projectManage'
      })
    }
    if(index == 2){
      router.push({
        path:'/projectSystemManage'
      })
    }
    if(index == 3){
      router.push({
        path:'/scheduleGenerate'
      })
    }
    if(index == 4){
      router.push({
        path:'/scheduleProjectView'
      })
    }
    if(index == 5){
      router.push({
        path:'/scheduleDeveloperView'
      })
    }
    if(index == 6){
      router.push({
        path:'/actualProgress'
      })
    }
  }
</script>

<style scoped>
  /*.el-menu-vertical-demo:not(.el-menu--collapse) {*/
  /*  width: 200px;*/
  /*  min-height: 400vh;*/
  /*}*/
  .font-setting {
    font-size: 14px;
    font-weight: 500;
  }
</style>


<script lang="ts">
  export default {
    name: "scheduleAside"
  }
</script>

