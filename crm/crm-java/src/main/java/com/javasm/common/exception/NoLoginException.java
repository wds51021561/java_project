package com.javasm.common.exception;


import com.javasm.common.http.EnumStatus;

/**
 * 处理未登录异常
 */
public class NoLoginException extends RuntimeException{

    private EnumStatus enumStatus;

    public NoLoginException(EnumStatus enumStatus) {
        this.enumStatus = enumStatus;
    }

    public EnumStatus getEnumStatus() {
        return enumStatus;
    }

    public void setEnumStatus(EnumStatus enumStatus) {
        this.enumStatus = enumStatus;
    }
}
