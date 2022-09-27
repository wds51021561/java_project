package com.javasm.storage.myEnum;

import lombok.Data;

/**
 * 作者:yy
 * 日期:2022/7/2 21:32
 * 描述:
 */
public enum StorageEnum {
    //出入库
    WAIT_IN_STORAGE(1),
    IN_STORAGE(2),
    EXIT_IN_STORAGE(3),
    WAIT_OUT_STORAGE(4),
    OUT_STORAGE(5),
    EXIT_OUT_STORAGE(6),
    INPUT(7),


    STORAGE_FORM_TYPE(1),
    STORAGE_STATES(2),
    ORDER_TYPE(3),
    PAY_TYPE(4),
    STORAGE_TYPE(5)
    ;
    StorageEnum(Integer value){
        this.value = value;
    }
    Integer value;
    public Integer get(){
        return this.value;
    }
}
