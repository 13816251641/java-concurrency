package com.wangwenjun.concurrency.chapter8;

/**
 * 创建一个死锁场景,2把锁,2个不同的线程互相竞争
 */
public class DeadLock {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();


    private  void execute1(){
       synchronized (lock1){
           try {
               Thread.sleep(2_000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           execute2();
       }
    }

    private  void execute2(){
        synchronized (lock2){
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            execute1();
        }
    }


    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
         new Thread(()->{
             deadLock.execute1();
         }).start();
        new Thread(()->{
            deadLock.execute2();
        }).start();


    }



}
