package com.javasm.common.interceptor;

import com.javasm.common.exception.NoLoginException;
import com.javasm.common.http.EnumStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception  {
        String method = request.getMethod();
        System.out.println("method=="+method);
        String requestURI = request.getRequestURI();
        System.out.println("requestURI==="+requestURI);
//        if ("/common/doLogin".equals(requestURI)){
//            String authorization = request.getHeader("Authorization").split(" ")[1];
//            if (null==authorization || "null".equals(authorization)){
//                throw new NoLoginException(EnumStatus.NO_TOKEN);
//            }
//            System.out.println("Authorization=="+authorization);
//        }

        return true;
    }
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception  {
////        String method = request.getMethod();
////        System.out.println("method=="+method);
////        String requestURI = request.getRequestURI();
////        System.out.println("requestURI==="+requestURI);
////        String authorization = request.getHeader("Authorization").split(" ")[1];
////        if (null==authorization || "null".equals(authorization)){
////            throw new NoLoginException(EnumStatus.NO_TOKEN);
////        }
////        System.out.println("Authorization=="+authorization);
//
//        return true;
//    }
}
