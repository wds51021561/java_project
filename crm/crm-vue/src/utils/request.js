/*
* 封装axios
* */
import axios from "axios";

//引入elementUI中的Notification
import {Notification} from 'element-ui'

//引入token
import  auth from "@/utils/auth";//引入token

//创建axios
/*
* instance对象 就是我们之前在项目中使用 axios
*
* */
const instance = axios.create({
    baseURL: 'http://localhost:8088/',
    timeout: 3000,
});


// Add a request interceptor
instance.interceptors.request.use(function (config) {
    //发送token到后台
    config.headers.Authorization="Bearer "+auth.getToken();

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

//请求后
instance.interceptors.response.use(function (response) {
    //判断状态吗
    let {status, message, data} = response.data;
    if (status == 20000) {
        return data;
    } else {
        Notification.error(message)
        return Promise.reject(false);
    }
}, function (error) {

    return Promise.reject(error);
});

export default instance;

