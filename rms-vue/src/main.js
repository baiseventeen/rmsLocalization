// const serverIp = "172.2.0.176"
const serverIp = "127.0.0.1"
import {createApp} from 'vue'
import { ElMessage } from 'element-plus'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import zhCn from 'element-plus/es/locale/lang/zh-cn';

import App from './App.vue'
const app =createApp(App)

import router from './router/index.js'

import axios from 'axios'
axios.defaults.baseURL = `http://${serverIp}:7134`

axios.interceptors.request.use((config)=>{
    const token = window.sessionStorage.getItem("token");
    if(token){
        config.headers.Authorization = token;
        config.withCredentials = true;
    }
    return config;
})

axios.interceptors.response.use(
    (response)=>{
        return response;
    },
    (error) =>{
        if(error.response.data.code===401){
            ElMessage.warning("您未登录")
            router.push({path:"/login"})
        }
        return Promise.reject(error)
    }
)

app.config.globalProperties.$http = axios

for(const [key, component] of Object.entries(ElementPlusIconsVue)){
    app.component(key, component)
}

app.use(ElementPlus, { locale: zhCn })
app.use(router)
app.mount('#app')
