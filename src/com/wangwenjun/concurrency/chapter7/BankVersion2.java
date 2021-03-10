package com.wangwenjun.concurrency.chapter7;

/*
    模拟银行叫号版本2 使用Runnable接口
    这样写线程共用一个实例,TicketWindowRunnable
 */
public class BankVersion2 {

    public static void main(String[] args) {
        TicketWindowRunnable ticketWindow = new TicketWindowRunnable();
        Thread t1 = new Thread(ticketWindow,"1号窗口");
        Thread t2 = new Thread(ticketWindow,"2号窗口");
        Thread t3 = new Thread(ticketWindow,"3号窗口");
        t1.start();
        t2.start();
        t3.start();


    }


}
