package com.wangwenjun.concurrency.chapter3;

/**
 * main线程结束,别的线程也不会结束
 */
public class CreateThread1 {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for(int i=0;i<Integer.MAX_VALUE;i++){
                System.out.println(i);
            }
        });
        t.start();
        System.out.println("main thread over");
    }
}
