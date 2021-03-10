package com.wangwenjun.concurrency.chapter5;

import java.util.stream.IntStream;

public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()-> { //[1,1000)
            IntStream.range(1,1000).forEach(i->System.out.println(Thread.currentThread().getName()+"->"+i));
        });
        t1.start();
        t1.join();//current thread wait for t1 to die

        IntStream.range(1,1000).forEach(i->System.out.println(Thread.currentThread().getName()+"->"+i));

    }

}
