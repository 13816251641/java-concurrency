package com.wangwenjun.concurrency.chapter14.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Package: com.wangwenjun.concurrency.chapter14.countdownlatch
 * @ClassName: UseCountDownLatch2
 * @Author: lujieni
 * @Description: 2
 * @Date: 2021-03-09 15:15
 * @Version: 1.0
 */
public class UseCountDownLatch2 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            CountRunnable runnable = new CountRunnable(cdl);
            pool.execute(runnable);
        }
        pool.shutdown();
    }


}

class CountRunnable implements Runnable {
    private CountDownLatch countDownLatch;
    public CountRunnable(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try {
            synchronized (countDownLatch) {
                /*** 每次减少一个容量*/
                countDownLatch.countDown();
                System.out.println("thread counts = " + (countDownLatch.getCount()));//countDownLatch.getCount()线程不安全,所以加同步
            }
            countDownLatch.await();//等待计数器数字为0才执行
            System.out.println("concurrency counts = " + (100 - countDownLatch.getCount()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}