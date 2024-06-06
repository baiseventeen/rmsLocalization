<script setup>
import {getCurrentInstance, onMounted, reactive} from "vue"
import {User,Lock} from "@element-plus/icons-vue"
import router from "../../router/index.js";

  const {proxy} = getCurrentInstance()

  const loginInfo = reactive({
    account: null,
    password: null
  })

  const login= ()=>{

    if(loginInfo.account!==null&&loginInfo.account!==""
    &&loginInfo.password!==null&&loginInfo.password!==""){
      proxy.$http({
        url: "/entry/login",
        method: "post",
        data: loginInfo
      }).then((res)=>{
        console.log(res)
        //TODO: 响应处理
        window.sessionStorage.setItem('token',res.data.data.token);
        window.sessionStorage.setItem("user_name",loginInfo.account);
        window.sessionStorage.setItem("role",res.data.data.role);
        window.sessionStorage.setItem("developerId",res.data.data.developerId);
        window.sessionStorage.setItem("developerName",res.data.data.developerName);
        router.push({path:"/modeling"})

      }).catch((ex)=>{
        //TODO: 错误处理
      })
    }
  }

  onMounted(()=>{
    document.addEventListener("keydown",(evt)=>{
      if(evt.key==="Enter"){
        login()
      }
    })

  })

</script>


<template>
  <div class="wrapper">
    <div id="building"
         style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">

      <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>登 录</b></div>
      <el-form>
        <el-form-item prop="username" >
          <el-input style="margin: 10px 0" size="large" :prefix-icon="User" v-model="loginInfo.account"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input style="margin: 10px 0" size="large" :prefix-icon="Lock" show-password
                    v-model="loginInfo.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" autocomplete="off" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>


<style scoped>
.wrapper {
  /*渐变的背景色*/
  /*height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EF8);
  overflow: hidden;*/
  /*背景图*/
  background: url("../../assets/back.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  top:0;
  left: 0;
}

#building {
  /*设置透明度，0为完全透明，1为不透明*/
  opacity: 0.85;
}
</style>