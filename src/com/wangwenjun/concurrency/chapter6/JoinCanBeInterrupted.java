package com.wangwenjun.concurrency.chapter6;

/**
 * @Auther ljn
 * @Date 2020/1/17
 * join方法可以被打断
 */
public class JoinCanBeInterrupted {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            while (true){

            }
        });
        t.start();

        Thread mainThread = Thread.currentThread();

        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainThread.interrupt();//打断main线程的执行 只是设置线程的中断状态
            }
        };
        t1.start();

        try {
            t.join();//main线程需要等到t线程结束才继续执行
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被打断了");
            e.printStackTrace();
        }
        System.out.println("执行到我了");
    }
}
