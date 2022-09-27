package com.javasm.common.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 从容器中获取到bean对象
 */
@Component
public class SpringContainerUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext1) throws BeansException {
            applicationContext=applicationContext1;
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
