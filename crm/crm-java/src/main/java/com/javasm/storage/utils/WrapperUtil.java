package com.javasm.storage.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * 作者:yy
 * 日期:2022/7/1 21:49
 * 描述:
 */
public class WrapperUtil {

    public static <T> LambdaQueryWrapper<T> getWrapper(Class<T> clazz){
        return new LambdaQueryWrapper<>();
    }



}
