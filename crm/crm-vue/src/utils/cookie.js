//引入cookie
import cookie from 'vue-cookies'



let cookieUtil={

    //创建cookie
    setCookie(key,value){
        cookie.set(key,value);
    },
    //获取cookie
    getCookie(key){
        return cookie.get(key)
    },
    //删除cookie
    deleteCookie(key){
         cookie.remove(key);
    }

}

export default cookieUtil