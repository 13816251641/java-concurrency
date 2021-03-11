package com.wangwenjun.concurrency.chapter14.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Package: com.wangwenjun.concurrency.chapter14.threadpool
 * @ClassName: UseThreadPool
 * @Author: lujieni
 * @Description: 1
 * @Date: 2021-03-11 14:26
 * @Version: 1.0
 */
public class UseThreadPool {

    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newSingleThreadExecutor();//单个线程
        //ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个固定的线程池的大小
        ExecutorService executorService = Executors.newCachedThreadPool();//可伸缩的,遇强则强

        for(int i = 0;i < 100;i++){
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+" ok");
            });
        }
        executorService.shutdown();
    }


}