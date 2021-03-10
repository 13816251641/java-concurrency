package com.wangwenjun.concurrency.chapter7;

/**
 * 证明synchronized static的锁是class,即SynchronizedStatic.class
 */
public class SynchronizedStaticTest {

    public static void main(String[] args) {
        /*
            new的是Thread的子类
         */
        new Thread("T1"){
            @Override
            public void run() {
                SynchronizedStatic.m1();
            }
        }.start();

        new Thread("T2"){
            @Override
            public void run() {
                SynchronizedStatic.m2();
            }
        }.start();

        new Thread("T3"){
            @Override
            public void run() {
                SynchronizedStatic.m3();
            }
        }.start();



    }


}
