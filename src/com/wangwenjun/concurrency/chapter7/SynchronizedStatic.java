package com.wangwenjun.concurrency.chapter7;

/**
 *  synchronized static的锁是class,对应的测试类是SynchronizedStaticTest
 */
public class SynchronizedStatic {

   /* static {
        synchronized (SynchronizedStatic.class){
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    public synchronized static void m1(){
        System.out.println("m1 "+Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m2(){
        System.out.println("m2 "+Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m3(){
        System.out.println("m3 "+Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
