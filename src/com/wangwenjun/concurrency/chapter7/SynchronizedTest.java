package com.wangwenjun.concurrency.chapter7;

import java.util.concurrent.TimeUnit;

/*
   利用synchronized关键字控制并发,使代码内并发变为单发
 */
public class SynchronizedTest {

    private static final Object LOCK = new Object();//存入方法区

    public static void main(String[] args) {
        Runnable runnable = ()->{
          synchronized (LOCK){
              try {
                  TimeUnit.MINUTES.sleep(3);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        };
        Thread t1 = new Thread(runnable,"t1");
        Thread t2 = new Thread(runnable,"t2");
        Thread t3 = new Thread(runnable,"t3");
        t1.start();
        t2.start();
        t3.start();

    }


}
