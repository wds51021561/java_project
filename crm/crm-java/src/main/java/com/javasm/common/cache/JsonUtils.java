package com.javasm.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

import java.util.List;

/**'
 * Json 转换类
 */
public class JsonUtils {

    static ObjectMapper objectMapper=new ObjectMapper();

    /**
     * 对象转字符串
     */
    public static String obj2Str(Object obj) {

        try {
            String s = objectMapper.writeValueAsString(obj);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";

    }


    /**
     * 字符串转对象
     */
    public static <T> T str2Obj(String str, Class<T> clazz) {

        try {
            T t = objectMapper.readValue(str, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换集合
     */

    public static <T> List<T> str2List(String str, Class<T> clazz){
        CollectionLikeType collectionLikeType = objectMapper.getTypeFactory().constructCollectionLikeType(List.class, clazz);
        try {
            List<T> list = objectMapper.readValue(str, collectionLikeType);
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }
}
