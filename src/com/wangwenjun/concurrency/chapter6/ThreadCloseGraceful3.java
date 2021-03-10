package com.wangwenjun.concurrency.chapter6;

/**
   原本想在自己的代码里捕获InterruptedException异常,结果发现
   这样做是不可以的,因为run方法里没有Thread.sleep
 */
public class ThreadCloseGraceful3 {

    public static void main(String[] args) throws InterruptedException {
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        while(true){

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("被打断了");//捕获不到
                    }
                }
            };
            t.start();
            Thread.sleep(500);
            System.out.println(t.isInterrupted());//检测t线程状态 false
            t.interrupt();//中断t线程 只是设置线程的中断状态
            System.out.println(t.isInterrupted());//检测t线程状态 true

    }
}
