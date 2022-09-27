package com.javasm.config;

import com.javasm.common.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 一.问题：Springboot 拦截器 拦截swagger请求解决办法：
// 解决： 1.在添加拦截器的位置 添加放行路径，这里不能只放行 swagger的html资源  ，还需要放行其他和swagger相关的资源。
//          例子：见项目中swagger



@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
        registration.addPathPatterns("/**"); //所有路径都被拦截
        registration.excludePathPatterns( //添加不拦截路径
                "/common/getCaptcha/*", //获取验证码路径
                "/common/doLogin", //登录放行
                "/**/*.html", //html静态资源
                "/**/*.js", //js静态资源
                "/**/*.css" //css静态资源
        ).excludePathPatterns(  // 放行swagger相关的
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/**",
                "/webjars/**"
        ).excludePathPatterns(
                "/storage/**",
                "/dic/**"
        );
    }
}
