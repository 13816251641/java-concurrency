package com.wangwenjun.concurrency.chapter4;

import java.util.Optional;

/**
 线程的执行优先级可以设置参数来设置,但不见的优先级高的就一定会先执行
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
           for(int i=0;i<1000;i++){
               Optional.of(Thread.currentThread().getName()+"-index"+i).ifPresent(System.out::println);
           }
        });
        t1.setPriority(Thread.MAX_PRIORITY);

        Thread t2 = new Thread(()->{
            for(int i=0;i<1000;i++){
                Optional.of(Thread.currentThread().getName()+"-index"+i).ifPresent(System.out::println);
            }
        });
        t2.setPriority(Thread.NORM_PRIORITY);

        Thread t3 = new Thread(()->{
            for(int i=0;i<1000;i++){
                Optional.of(Thread.currentThread().getName()+"-index"+i).ifPresent(System.out::println);
            }
        });
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }
}
