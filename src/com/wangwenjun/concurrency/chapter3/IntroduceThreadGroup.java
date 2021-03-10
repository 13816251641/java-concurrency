package com.wangwenjun.concurrency.chapter3;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * 如果构造线程对象时未传入ThreadGroup,Thread会默认获取父线程的ThreadGroup
 * 作为该线程的ThreadGroup,此时子线程和父线程将会在同一个threadGroup中
 */
public class IntroduceThreadGroup {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"myThread");
        t.start();
        System.out.println(t.getThreadGroup());//java.lang.ThreadGroup[name=main,maxpri=10]
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup.getName());//main
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        Arrays.asList(threads).forEach(System.out::println);
        /*
           Thread[main,5,main]
           Thread[Monitor Ctrl-Break,5,main]
           Thread[myThread,5,main]
         */

    }
}
