package com.wangwenjun.concurrency.chapter1;

/**
 * @Auther ljn
 * @Date 2020/1/13
 * 即使main线程执行完毕,别的线程也不会被终止
 */
public class Hello {

    public static void main(String[] args)  {
        new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello");
        }).start();
        System.out.println("main线程结束");
    }
}
