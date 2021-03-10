package com.wangwenjun.concurrency.chapter7;

/**
    模拟银行叫号版本3 使用Runnable接口,测试
    SynchronizedRunnable类
 */
public class BankVersion3 {

    public static void main(String[] args) {
        SynchronizedRunnable ticketWindow = new SynchronizedRunnable();
        Thread t1 = new Thread(ticketWindow,"1号窗口");
        Thread t2 = new Thread(ticketWindow,"2号窗口");
        Thread t3 = new Thread(ticketWindow,"3号窗口");
        t1.start();
        t2.start();
        t3.start();


    }


}
