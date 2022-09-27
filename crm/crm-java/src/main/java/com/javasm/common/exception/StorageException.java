package com.javasm.common.exception;

import com.javasm.common.http.EnumStatus;

/**
 * 作者:yy
 * 日期:2022/7/2 20:07
 * 描述:
 */
public class StorageException extends RuntimeException{

    private EnumStatus enumStatus;

    public StorageException(EnumStatus enumStatus) {
        this.enumStatus = enumStatus;
    }

    public EnumStatus getEnumStatus() {
        return enumStatus;
    }

    public void StorageException(EnumStatus enumStatus) {
        this.enumStatus = enumStatus;
    }
}
