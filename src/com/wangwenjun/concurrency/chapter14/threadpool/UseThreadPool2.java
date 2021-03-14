package com.wangwenjun.concurrency.chapter14.threadpool;

import java.util.concurrent.*;

/**
 * @Package: com.wangwenjun.concurrency.chapter14.threadpool
 * @ClassName: UseThreadPool
 * @Author: lujieni
 * @Description: 1
 * @Date: 2021-03-11 14:26
 * @Version: 1.0
 *
 * 4种拒绝策略
 * new ThreadPoolExecutor.AbortPolicy() //银行满了,还有人进来,不处理这个人,直接抛出异常
 * new ThreadPoolExecutor.CallerRunsPolicy() //哪来的去哪里
 * new ThreadPoolExecutor.DiscardPolicy() //队列满了,丢掉任务,不会抛出异常
 * new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了尝试去和最早的竞争但不会抛出异常
 *
 * 超过 corePoolSize + LinkedBlockingQueue 之和才会增加核心线程数
 * 这里当为6个任务及以上才行
 */
public class UseThreadPool2 {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        try{
            for(int i = 1;i <= 6;i++){
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" ok");
                });
            }
        }catch (Exception e){

        }finally {
            threadPoolExecutor.shutdown();

        }
    }


}