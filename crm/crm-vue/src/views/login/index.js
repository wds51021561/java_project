
import {getCaptche,doLogin,getAdminInfo} from "@/api/login" //登录

import  auth from "@/utils/auth";//引入保存token


let login={
    created(){
        this.getCaptcha();
        this.doLogin();

    },
    data(){
      return{
          base64Str:'',
          loginForm:{
              uuid:'',
              username:'',
              password:'',
              captcha:''
          }
      }
    },
    methods:{

        //发送uuid
        async getCaptcha(){
            //获取uuid
            this.loginForm.uuid=this.guid();
            //获得到验证码 并赋值到img
           this.base64Str=await getCaptche(this.loginForm.uuid);
            console.log(this.base64Str)
        },
        //登录
        async doLogin(){
            //登录 拿到token
            let token=await  doLogin(this.loginForm);
            console.log(token)
            //保存token
            auth.setToken(token);
            //获得权限
            this.getAdminInfo();


        },

        //登录获得用户权限
        async getAdminInfo(){
            //获得所有权限
            let response=await getAdminInfo();
            console.log(response)
            //存入到cookie  有bug：
            // auth.setUserInfo(response.admin);
            // auth.setmenuList(response.hjmenuList);
            // auth.setbenPerms(response.hjbtnPerm)
            //存入本地  不使用 cookie
            localStorage.setItem("userInfo",JSON.stringify(response.admin));//存储用户
            localStorage.setItem("menuList",JSON.stringify(response.hjmenuList));//存储菜单
            localStorage.setItem("btnPerm",JSON.stringify(response.hjbtnPerm));//存储按钮

            console.log("admin=="+response.admin);
            console.log("admin-stringify=="+JSON.stringify(response.admin));
            console.log("hjmenuList=="+response.hjmenuList);
            console.log("hjmenuList-stringify=="+JSON.stringify(response.hjmenuList));
            console.log("hjbtnPerm=="+response.hjbtnPerm);
            console.log("hjbtnPerm-stringify=="+JSON.stringify(response.hjbtnPerm));
            //登录成功跳转页面
            this.$router.push("/index")
        },

        //获得uuid
        guid(){
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0,
                    v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

    }

}
export default login