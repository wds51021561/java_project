package com.javasm.common.perm;


import com.javasm.common.exception.PermException;
import com.javasm.common.http.EnumStatus;
import com.javasm.common.util.TokenService;
import com.javasm.domin.entity.Menu;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * 用来处理按钮权限的aop切面
 */
@Aspect
@Component
public class MyAspect {


    @Autowired
    private TokenService tokenService;


    @Before("pointCut()")
    public void checkPerm(JoinPoint joinPoint) throws IOException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //拿到方法
        Method method = signature.getMethod();
        //拿到方法上权限的自定义注解
        HasPerm annotation = method.getAnnotation(HasPerm.class);
        //判断是否为空
        if (annotation != null) {
            //判断是否有权限
            List<Menu> perms = tokenService.getLoginAdmin().getPerms();
            //拿到权限自定义注解的值
            String perm = annotation.perm();
            //是否有一个数值
            boolean b = perms.stream().anyMatch(menu -> perm.equals(menu.getPermission()));
            if (!b) {
                //如果没有 则抛出 无权限的异常
                throw new PermException(EnumStatus.NO_PERM);
            }


        }


    }

    @Pointcut("@annotation(com.javasm.common.perm.HasPerm)")
    public void pointCut() {

    }
}
