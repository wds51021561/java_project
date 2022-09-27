import instance from "@/utils/request";

export function getCaptche(uuid) {

    //向后台发送uuid
    return instance.get(`common/getCaptcha/${uuid}`)

}

/**
 * 登录
 */
export function doLogin(entity) {
    return instance.post(`common/doLogin`,entity)
}


/**
 * 获得登录是获得用户权限
 */
export function getAdminInfo() {
    return instance.get("common/getAdminInfo")
}