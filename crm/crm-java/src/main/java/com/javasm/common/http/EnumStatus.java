package com.javasm.common.http;


public enum EnumStatus {

    OK(20000, "操作成功"),
    ERROR(40000, "小奥做失败"),
    NO_LOGIN(33333, "未登录"),
    ACCOUNT_ERROR(22222, "用户名不正确"),
    CODE_ERROR(22223, "验证码不正确"),
    PASSWOED_ERROR(22224, "密码错误"),
    NO_ACTIVE(22225, "用户未激活"),
    FORM_VALICATOR_ERROR(33233, "表单校验失败"),
    IMG_NO_UPLOAD(2223344, "上传的不是一个图片"),
    IMG_NO(22331, "上传格式不正确"),
    IMG_NO_MAX(22332, "上传文件太大"),
    NO_PERM(666666, "无权限"),
    NO_TOKEN(777777, "无token"),
    AUDITED(555555,"此记录已审核"),
    ERROR_FORM_CREATED(12345,"此订单的入库单已创建"),
    ERROR_GOOD_ID(123145,"商品与串口不一致或商品已被使用"),
    ERROR_GOOD_STORAGE(50000, "商品没有全部盘点完成"),
    ;

    private int status;

    private String message;

    EnumStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
