<script setup>
  import router from "../../router/index.js";
  import {getCurrentInstance, onMounted, ref} from "vue";
  import {ArrowDown} from "@element-plus/icons-vue";
  const {proxy} = getCurrentInstance()

  const logo = new URL('../../assets/logo.png',import.meta.url).href
  let isactive1 = ref(true)
  let isactive2 = ref(false)
  let isactive3 = ref(false)

  const handleSelect = (index)=>{
    if(index==="1"){
      isactive1.value = true
      isactive2.value = false
      isactive3.value = false
      // proxy.$message.info("okok")
      router.push({path: "/modeling"})
    }else if(index==="2"){
      isactive1.value = false
      isactive2.value = true
      isactive3.value = false
      router.push({
        path:"/schedule"
      })
    }else if(index==="3"){
      isactive1.value = false
      isactive2.value = false
      isactive3.value = true
      proxy.$message.info(index + "clicked")
    }

  }

  const handleCommand = (command)=>{
    if(command==="logout"){
      window.sessionStorage.clear()
      router.push({path: "/login"})
    }
  }

  let account = null

  onMounted(()=>{
    account = window.sessionStorage.getItem("user_name")
  })
</script>

<template>
  <el-row :class="{active1 : isactive1, active2 : isactive2, active3 : isactive3 }">
    <div style="width: 10px"></div>
    <el-col :offset="0" :span="1" style="height: 50px;" :class="{active1 : isactive1, active2 : isactive2, active3 : isactive3 }">
      <el-image :src=logo style="height: 45px;position: absolute;top: 5%;border-radius: 50%"></el-image>
    </el-col>
    <el-col :span="1">
<!--      background: linear-gradient(109.23deg, #2d61fc 4.94%, #9362ed 49.73%, #f06195 101.9%, #fca1ad 141.28%);-->
        <el-text
            style="background: linear-gradient(109.23deg, #2d61fc 4.94%, #9362ed 49.73%, #f06195 101.9%, #fca1ad 141.28%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 25px;
            position: absolute;
            top: 18%">RMS</el-text>
    </el-col>
    <div style="width: 20px"/>
    <el-col :span="15" > 
      <el-menu style="height: 50px;border-bottom: none;"
               mode="horizontal"
               text-color="#fff"
               default-active="1"
               @select="handleSelect"
               :class="{active1 : isactive1, active2 : isactive2, active3 : isactive3 }">
        <el-menu-item index="1">产能建模</el-menu-item>
        <el-menu-item index="2">排期决策</el-menu-item>
        <el-menu-item index="3">能力评价</el-menu-item>
      </el-menu>
    </el-col>
    <div style="width: 30px"/>
    <el-col :span="2" :offset="4">
      <el-dropdown @command="handleCommand" style="position: absolute;top: 35%">
        <span style="color: azure">
            {{ account }}
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <!--          <el-dropdown-item>我的账号</el-dropdown-item>-->
            <!--          <el-dropdown-item>修改密码</el-dropdown-item>-->
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
  </el-row>

</template>

<style scoped>
  .active1{
    background-color: #526476
  }
  .active2{
    /*background-color: #50b7c1*/
    background-color: #526476
  }
  .active3{
    background-color: #526476
  }
</style>