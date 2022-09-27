package com.javasm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan(basePackages = "com.javasm.mapper")
@MapperScan(basePackages = "com.javasm.storage.mapper")
@EnableTransactionManagement//事务
//@EnableScheduling//开启任务调度
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启aop
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class);
    }

    /**
     * 请求去调用服务端的数据
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 密码加密
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
