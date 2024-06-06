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

  const handleSelect = (index)=>{
    if(index==="1"){
      router.push({path:"/developerManage"})
    }else if(index==="2"){
      router.push({path:"/positionManage"})
    }else if(index==="3"){
      router.push("/groupManage")
    }else if(index==="4"){
      router.push("/skillManage")
    }else if(index==="5"){
      router.push("/resourceMonitoring")
    }else if(index==="6"){
      router.push("/performanceAnalysis")
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
      <el-menu @select="handleSelect" style="background-color: #f3f3f4;height: 694px" default-active="1" text-color="#606266" :collapse="menuInfo.isFold">
        <el-tooltip content="开发人员管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="1" style="font-size: small">
              <el-icon><User/></el-icon>
            <span>开发人员管理</span>
  <!--          <template #title>开发人员管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="岗位管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="2" style="font-size: small">
            <el-icon><PieChart/></el-icon>
            <span>岗位管理</span>
            <!--          <template #title>岗位管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="组织架构管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="3" style="font-size: small">
            <el-icon><Notebook/></el-icon>
            <span>组织架构管理</span>
  <!--          <template #title>组织架构管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="技能库管理" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="4" style="font-size: small">
            <el-icon><MessageBox/></el-icon>
            <span>技能库管理</span>
  <!--          <template #title>技能库管理</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="资源利用监控" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="5" style="font-size: small">
            <el-icon><ZoomIn/></el-icon>
            <span>资源利用监控</span>
  <!--          <template #title>资源利用监控</template>-->
          </el-menu-item>
        </el-tooltip>
        <el-tooltip content="效能分析" placement="right" :disabled="!menuInfo.isFold">
          <el-menu-item index="6" style="font-size: small">
            <el-icon><DataAnalysis/></el-icon>
            <span>效能分析</span>
  <!--          <template #title>效能分析</template>-->
          </el-menu-item>
        </el-tooltip>
      </el-menu>
    </el-col>
  </el-row>
</template>

<style scoped>

</style>