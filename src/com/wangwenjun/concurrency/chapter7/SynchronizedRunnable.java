package com.wangwenjun.concurrency.chapter7;

/**
   同步方法,锁是当前对象的实例
   与BankVersion3组成cp
 */
public class SynchronizedRunnable implements Runnable {
    private int index = 1;
    private final static int MAX = 500;

    /**
       同步方法锁是当前对象的实例,可重入
     */
    @Override
    public synchronized void run() {
        while (true) {
            if (index > MAX)
                break;
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "当前的号码是:" + (index++));
        }
    }
}
