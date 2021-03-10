package com.wangwenjun.concurrency.chapter4;


/**
   当运行的线程都是守护线程时，java虚拟机将退出。
   当用户线程都退出的时候所有守护线程都会强制退出,
   jvm也会退出,这里注意一个都字。

   当你在一个守护线程中产生了其他线程，那么这些新产生
   的线程不用设置Daemon属性,都将是守护线程。用户线程与此相同
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+" running");

                    new Thread(()->{
                        try {
                            Thread.sleep(5000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();//这个线程天生就是守护线程

                    Thread.sleep(100000);
                    System.out.println(Thread.currentThread().getName()+" done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        t.setDaemon(true);//必须在start之前设置
        t.start();

        System.out.println(Thread.currentThread().getName());
        try {
            Thread .sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
