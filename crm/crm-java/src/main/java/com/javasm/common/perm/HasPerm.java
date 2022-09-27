package com.javasm.common.perm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解
 *
 * 你是否有增删改查的权限
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})//写在方法上
public @interface HasPerm {

    String perm() default "";
}
