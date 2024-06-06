<script setup>
import {
  User,
  ZoomIn,
  DataAnalysis,
  PieChart,
  MessageBox,
  Notebook,
  ArrowLeftBold,
  ArrowRightBold,
} from "@element-plus/icons-vue"
import {ref, reactive, getCurrentInstance} from "vue"
import router from "../../../router/index.js";
  let show1 = window.sessionStorage.getItem('role') == 'ROLE_ADMIN' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';
  let show2 = window.sessionStorage.getItem('role') == 'ROLE_ADMIN' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';
  let show3 = window.sessionStorage.getItem('role') == 'ROLE_ADMIN' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';
  let show4 = true;
  let show5 = true;
  let show6 = true;
  let show7 = window.sessionStorage.getItem('role') == 'ROLE_USER' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';
  let show8 = window.sessionStorage.getItem('role') == 'ROLE_ADMIN' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';
  let show9 = window.sessionStorage.getItem('role') == 'ROLE_USER' || window.sessionStorage.getItem('role') == 'ROLE_PROJECT_MANAGER';

const menuInfo = reactive({
    isFold: false,
    text: "折叠功能菜单"
  })

  const foldAndUnfold = ()=>{
    menuInfo.isFold=!menuInfo.isFold;
    if(menuInfo.text==="折叠功能菜单"){
      menuInfo.text="";
    }else {
      menuInfo.text="折叠功能菜单";
    }
  }

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
        path:'/investment'
      })
    }
    if(index == 7){
      router.push({
        path:'/alterationRequest'
      })
    }
    if(index == 8){
      router.push({
        path:'/alterationProcessing'
      })
    }
    if(index == 9){
      router.push({
        path:'/alterationManage'
      })
    }
  }

</script>

<template>
  <el-row>
    <el-col>
      <el-tooltip content="展开功能菜单" placement="right" :disabled="!menuInfo.isFold">
        <el-button :icon="menuInfo.isFold?ArrowRightBold:ArrowLeftBold" @click="foldAndUnfold" size="small"
                   :style="[{backgroundColor: '#f3f3f4'},
                   {borderBottom: 'none'},
                   {borderRadius: 0},
                   {width:menuInfo.isFold?'64px':'150px'},]">
          {{menuInfo.text}}
        </el-button>
      </el-tooltip>
      <el-menu @select="handleSelect" style="background-color: #f3f3f4;min-height: 694vh" default-active="1" text-color="#606266" :collapse="menuInfo.isFold">
        <el-tooltip content="项目管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="1" style="font-size: small" v-if="show1">
              <el-icon><User/></el-icon>
            <span>项目管理</span>
  <!--          <template #title>开发人员管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="系统管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="2" style="font-size: small" v-if="show2">
            <el-icon><PieChart/></el-icon>
            <span>系统管理</span>
            <!--          <template #title>岗位管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="生成排期计划" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="3" style="font-size: small" v-if="show3">
            <el-icon><Notebook/></el-icon>
            <span>生成排期计划</span>
  <!--          <template #title>组织架构管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="项目排期视图" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="4" style="font-size: small"  v-if="show4">
            <el-icon><MessageBox/></el-icon>
            <span>项目排期视图</span>
  <!--          <template #title>技能库管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="资源排期视图" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="5" style="font-size: small"  v-if="show5">
            <el-icon><ZoomIn/></el-icon>
            <span>资源排期视图</span>
  <!--          <template #title>资源利用监控</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="实时开发进度" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="6" style="font-size: small"  v-if="show6">
            <el-icon><DataAnalysis/></el-icon>
            <span>每日投入信息</span>
  <!--          <template #title>效能分析</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-sub-menu index="#" >
          <template #title>
            <el-icon><location /></el-icon>
            <span style="font-size: small">变更管理</span>
          </template>
          <el-menu-item index="7" style="font-size: small; background-color:#eaeaea;" v-if="show7">
            <el-icon><ZoomIn/></el-icon>
            <span>变更请求</span>
          </el-menu-item>
          <el-menu-item index="8" style="font-size: small; background-color:#eaeaea;" v-if="show8">
            <el-icon><DataAnalysis/></el-icon>
            <span>处理请求</span>
          </el-menu-item>
          <el-menu-item index="9" style="font-size: small; background-color:#eaeaea;" v-if="show9">
            <el-icon><location/></el-icon>
            <span>请求管理</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-col>
  </el-row>
</template>

<style scoped>

</style>