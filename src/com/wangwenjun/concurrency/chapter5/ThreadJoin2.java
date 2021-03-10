package com.wangwenjun.concurrency.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

public class ThreadJoin2 {

    public static void main(String[] args) throws  InterruptedException {

        Thread t1 = new Thread(()->{
            IntStream.range(1,1000).forEach(i->System.out.println(Thread.currentThread().getName()+"->"+i));
        });

        Thread t2 = new Thread(()->{
            IntStream.range(1,1000).forEach(i->System.out.println(Thread.currentThread().getName()+"->"+i));
        });

        t1.start();//t1&t2是交替运行的
        t2.start();
        t1.join();//main线程等待t1线程结束才运行下面代码
        t2.join();//同上
        Optional.of("All of tasks finish done").ifPresent(System.out::println);
    }

}
