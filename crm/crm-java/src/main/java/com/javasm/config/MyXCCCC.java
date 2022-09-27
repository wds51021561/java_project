package com.javasm.config;

import com.javasm.common.async.AsyncFactory;
import com.javasm.common.async.AsyncManager;
import org.springframework.stereotype.Component;

@Component
public class MyXCCCC {

/*
    *//**
     * fixedRate :表示定时任务执行时间的延时时间   第二次需要等第一次执行完后的5秒
     * fixedDelay： 表示第一次开始执行时间 到第二次开始执行时间的间隔 不管第一吃是否执行完
     *//*
    @Scheduled(cron = "0/5 * * * * *",fixedRate = 5000,fixedDelay = 5000)
    public  void doSomeThing(){
        System.out.println("任务调度开始开始");
    }


    *//**
     * 多线程任务调度
     *//*
    @Scheduled(cron = "0/6 * * * * *")
    public  void doSomeThing2(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/7 * * * * *")
    public  void doSomeThing3(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 定时给邮箱发送 邮箱任务
     */
    //@Scheduled(cron = "0 * * * * *")
    public void sendEmail(){
        //发送邮件
        AsyncManager.getInstance().executor(AsyncFactory.sendEmail("146865@qq.com","定时任务aaaa"));
    }





}
