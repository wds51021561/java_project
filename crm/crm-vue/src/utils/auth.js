
//权限
import  cookitUtil from "@/utils/cookie"
//token
let tokenkey="adminToken";
//用户信息
let adminKey="adminInfo";
//菜单信息
let menuList="routerList";
//按钮
let benPerms="benPerms";


let auth={
    //存入cookie
    setToken(token){
        cookitUtil.setCookie(tokenkey,token)
    },
    //获取token
    getToken(token){
        return cookitUtil.getCookie(tokenkey)
    },
    //删除token
    removeToken(token){
        cookitUtil.deleteCookie(tokenkey)
    },
    //保存用户信息
    setUserInfo(admin){
        cookitUtil.setCookie(adminKey,JSON.stringify(admin))
    },
    //获得用户信息
    getUserInfo(){
        return cookitUtil.getCookie(adminKey)
    },
    //删除用户信息
    removeUserInfo(){
        cookitUtil.deleteCookie(adminKey)
    },
    //保存菜单信息
    setmenuList(routerList){
        cookitUtil.setCookie(menuList,JSON.stringify(routerList))
    },
    //获取菜单信息
    getmenuList(){
        return cookitUtil.getCookie(menuList)
        console.log(cookitUtil.getCookie(menuList))
    },
    //删除菜单信息
    removemenuList(){
        cookitUtil.deleteCookie(menuList)
    },
    //保存按钮信息
    setbenPerms(btnPerm){
        cookitUtil.setCookie(benPerms,JSON.stringify(btnPerm))
    },
    //获取按钮信息
    getbenPerms(){
        return cookitUtil.getCookie(benPerms)
    },
    //删除按钮信息
    removebenPerms(){
        cookitUtil.deleteCookie(benPerms)
    },

    //清除所有cookie
    clearAllCookie(){
        this.removebenPerms();
        this.removemenuList();
        this.removeUserInfo();
        this.removeToken();
    }

}

export default  auth;
