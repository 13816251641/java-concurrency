package com.wangwenjun.concurrency.chapter14;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.wangwenjun.concurrency.chapter14
 * @ClassName: UseSemaphore
 * @Author: lujieni
 * @Description: 使用Semaphore
 * @Date: 2021-03-09 13:51
 * @Version: 1.0
 *
 * semaphore.acquire() 获得允许,假如已经满了就等待
 * semaphore.release() 释放,会将当前的信号量释放+1,然后唤醒等待的线程
 */
public class UseSemaphore {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);

        Thread thread = null;

        for (int i = 1;i <= 7;i++){
            if(i==4){
                thread = new Thread(()->{
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"抢到车位");
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(Thread.currentThread().getName()+"离开车位");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        /*
                            There is no requirement that a thread that releases a permit must
                            have acquired that permit by calling {@link #acquire}
                         */
                        semaphore.release();//释放锁
                    }
                },String.valueOf(i));
                thread.start();
            }else{
                new Thread(()->{
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"抢到车位");
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(Thread.currentThread().getName()+"离开车位");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();//释放锁
                    }
                },String.valueOf(i)).start();
            }
        }

        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();

    }



}