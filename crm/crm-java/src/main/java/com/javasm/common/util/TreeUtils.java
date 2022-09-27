package com.javasm.common.util;


import com.javasm.common.reflect.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用属性结构工具类
 */
public class TreeUtils {


    /**
     * 通过父亲id=0 从一级开始往下排的方法
     */


    public static <T> List<T> bulidTree(List<T> list) {
        //通过反射拿到一级分类
        List<T> root = list.stream().filter(item -> ReflectionUtils.getFieldValue(item, "parentId").toString().equals("0")).collect(Collectors.toList());
        list.removeAll(root);
        root.forEach(item -> {
            getChildren(item, list);
        });

        return root;

    }

    /**
     * 递归 找孩子
     */
    public static <T> void getChildren(T t, List<T> list) {
        if (hasChildren(t, list)) {
//            List<T> collect = list.stream().filter(item -> (Long) ReflectionUtils.getFieldValue(item, "parentId") == (Long) ReflectionUtils.getFieldValue(t, "id")).collect(Collectors.toList());
            List<T> collect = list.stream()
                    .filter(item ->

                            ReflectionUtils.getFieldValue(item, "parentId").toString()
                                    .equals(
                                            ReflectionUtils.getFieldValue(t, "id").toString()
                                    )
                    )
                    .collect(Collectors.toList());
            ReflectionUtils.setFieldValue(t, "children", collect);
            list.removeAll(collect);
            //遍历二级
            collect.forEach(item1 -> getChildren(item1, list));


        }


    }

    /**
     * 判断有没有孩子
     */
    public static <T> boolean hasChildren(T t, List<T> list) {
        //使用anyMath 来判断
        return list.stream().anyMatch(item -> {
            String a = ReflectionUtils.getFieldValue(item, "parentId").toString();
            String b = ReflectionUtils.getFieldValue(t, "id").toString();
            return a.equals(b);

        });

    }


}
