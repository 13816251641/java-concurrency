package com.wangwenjun.concurrency.chapter3;


public class 栈溢出和解决方法 {

    private static int counter=1;

    public static void main(String[] args) {
       Thread t1 = new Thread(null,new Runnable() {
           @Override
           public void run() {
               try {
                   add(1); //递归导致栈溢出
               } catch (Error e) {
                   System.out.println(counter);
               }
           }
           private void add(int i){
               counter++;
               add(i+1);
           }
       },"myThread",1 << 24);
       //增大每个线程的虚拟机栈的大小,但会导致同一时刻jvm能new的线程数变小
       t1.start();
    }

}
