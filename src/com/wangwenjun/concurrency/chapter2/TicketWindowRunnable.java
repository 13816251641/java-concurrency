package com.wangwenjun.concurrency.chapter2;

import java.util.concurrent.TimeUnit;

/**
    叫号器Runnable版本
    实现Runnable接口
 */
public class TicketWindowRunnable implements Runnable {

    private final int MAX = 50;

    private int index = 1;

    @Override
    public void run() {
        while (index <= MAX){
            try{
                TimeUnit.SECONDS.sleep(1); //即使不加延迟也会出现线程安全问题
                System.out.println(Thread.currentThread().getName()+"当前的号码是:"+(index++));
            }catch (Exception e){

            }
        }
    }

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
