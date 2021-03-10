package com.wangwenjun.concurrency.chapter6;

/**
    t.interrupt()只是给线程对象t设置中断标志;
    t.isInterrupted()只是检测线程对象t中断标志;
    Thread.interrupted()检测当前线程中断标志并清除中断状态；
    还有重要的一点就是Thread.interrupted()作用于当前线程,
    t.interrupt()和t.isInterrupted()作用于t

    即使线程正在睡眠状态也可以被中断这一点很重要
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
            Thread t = new Thread(){
                @Override
                public void run() {
                    while(true){
                        try {
                            /*
                               即使正在睡眠状态也可以被中断
                             */
                            Thread.sleep(15_000);
                        } catch (InterruptedException e) {
                            System.out.println("收到打断信号.");
                            e.printStackTrace();
                            break;//跳出while循环
                        }
                    }
                    System.out.println("while循环结束,线程结束");
                }
            };
            t.start();
            Thread.sleep(1000);
            System.out.println(t.isInterrupted());//检测t线程状态 false
            t.interrupt();//中断t线程
    }
}
