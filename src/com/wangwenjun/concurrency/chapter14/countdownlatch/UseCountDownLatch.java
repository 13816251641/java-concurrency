package com.wangwenjun.concurrency.chapter14.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.wangwenjun.concurrency.chapter14
 * @ClassName: UseCountDownLatch
 * @Author: lujieni
 * @Description: 使用CountDownLatch
 * @Date: 2021-03-08 14:30
 * @Version: 1.0
 *
 * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行
 *
 * 1.countDownLatch.countDown() 数量-1
 * 2.countDownLatch.await() 等待计数器归零,然后再向下执行,会阻塞
 * 3.完全可以在一个线程内countDown两次
 * 4.CountDownLatch中委托给了内部类syn,而这个内部类实现了AQS,所以本身就是安全的,在多线程下不需要再加锁或者是synchronizwd
 *
 * countDownLatch.countDown() 2个api
 * countDownLatch.await()
 */
public class UseCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(11);

        for(int i = 1;i <= 5;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();//阻塞直到计数器为0才执行
        System.out.println("end");

    }
}