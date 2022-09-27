package com.javasm.storage.utils.transfer.base;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseTransfer {

    @SneakyThrows
    public static <T,VO> VO toVO (T t, Class<VO> voClass){
        VO vo = voClass.newInstance();
        BeanUtils.copyProperties(t,vo);
        return vo;

    }

    public static <T,VO> VO toVO (T t, Class<VO> voClass, TransferAble<T,VO> transfer){
        VO vo = toVO(t, voClass);
        transfer.transfer(t,vo);
        return vo;
    }

    public static <T,VO> List<VO> toListVO(List<T> list,Class<VO> voClass){
        List<VO> list1 = new ArrayList<>();
        list.forEach(t->list1.add(toVO(t,voClass)));
        return list1;
    }

    public static <T,VO> List<VO> toListVO(List<T> list,Class<VO> voClass,TransferAble<T,VO> transfer){
        List<VO> list1 = new ArrayList<>();
        list.forEach(t->list1.add(toVO(t,voClass,transfer)));
        return list1;
    }



}
