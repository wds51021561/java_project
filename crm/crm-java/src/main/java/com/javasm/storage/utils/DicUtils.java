package com.javasm.storage.utils;

import com.javasm.storage.entity.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者:yy
 * 日期:2022/7/2 17:06
 * 描述:
 */
@Component
public class DicUtils {

    private static List<Dic> dicList;
    @Autowired
    public void setDicList(List<Dic> dicList){
        DicUtils.dicList = dicList;
    }

    /**
     * 获取字典
     * @param typeId 字典类型id
     * @return
     */
    public static Map<Integer,String> getDicList(Integer typeId){
        Map<Integer,String> map = new HashMap<>();
        dicList.forEach(dic -> {
            if (dic.getTypeId().equals(typeId)) {
                map.put(dic.getStatusId(),dic.getStatusName());
            }
        });

        return map;
    }

    public static String queryDic(Integer typeId,Integer id){
        Map<Integer, String> dicList1 = getDicList(typeId);
        return dicList1.get(id);
    }

    /**
     * 仓库类型转义
     * @param id
     * @return
     */
    public static String getStorageType(Integer id){
        Map<Integer, String> dicMap = getDicList(5);
        return dicMap.get(id);
    }




}
