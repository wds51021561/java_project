package com.javasm.storage.utils;

import com.javasm.storage.entity.Dic;
import com.javasm.storage.vo.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:yy
 * 日期:2022/6/30 16:44
 * 描述:
 */
public class BaseUtil {
    /**
     * 将Dic对象转换位options对象
     *
     * @param dic
     * @return
     */
    public static Options dicToOptions(Dic dic) {
        Options options = new Options();
        options.setValue(dic.getStatusId());
        options.setLabel(dic.getStatusName());
        return options;
    }

    /**
     * 批量转换dic对象
     *
     * @param list
     * @return
     */
    public static List<Options> dicToOptions(List<Dic> list) {
        List<Options> list1 = new ArrayList<>();
        list.forEach(dic -> {
            list1.add(dicToOptions(dic));
        });
        return list1;
    }

    public static Boolean isStorageStates(Integer i) {
        return i < 6;

    }

}
