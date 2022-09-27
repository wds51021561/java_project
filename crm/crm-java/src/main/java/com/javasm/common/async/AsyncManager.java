package com.javasm.common.async;

import com.javasm.common.util.SpringContainerUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AsyncManager {

    //获取async静态方法
    private static ThreadPoolTaskExecutor threadPoolTaskExecutor;
    //当前对象 单例模式
    private static AsyncManager asyncManager;



    private AsyncManager(){
        //获取bean
        threadPoolTaskExecutor = SpringContainerUtils.getBean(ThreadPoolTaskExecutor.class);
        System.out.println(threadPoolTaskExecutor.getCorePoolSize());//获取核心线程池

    }

    //单例模式
    public static AsyncManager getInstance(){
        if (asyncManager==null){
            asyncManager=new AsyncManager();
        }
        return asyncManager;
    }

    //调用
    public  void executor(Runnable runnable){
        threadPoolTaskExecutor.execute(runnable);
    }


    //停止线程运行
    public void shutDown() {
        threadPoolTaskExecutor.shutdown();
    }

}
