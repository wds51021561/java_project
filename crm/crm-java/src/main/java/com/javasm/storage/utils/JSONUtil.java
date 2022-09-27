package com.javasm.storage.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 作者:yy
 * 日期:2022/6/30 16:34
 * 描述:
 */
public class JSONUtil {
   private static ObjectMapper objectMapper = new ObjectMapper();

   public static <T> T toObject(String json,Class<T> clazz){
       T t = null;
       try {
           t = objectMapper.readValue(json, clazz);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }
       return t;
   }

   public static String toString(Object obj){
       String s = null;
       try {
           s = objectMapper.writeValueAsString(obj);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }
       return s;
   }

}
