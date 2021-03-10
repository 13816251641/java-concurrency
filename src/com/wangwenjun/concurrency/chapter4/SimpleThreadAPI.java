package com.wangwenjun.concurrency.chapter4;

import java.util.Optional;

public class SimpleThreadAPI {

    public static void main(String[] args) {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"myThread");
        t.start();
        Optional.of(t.getName()).ifPresent(System.out::println);//myThread
        Optional.of(t.getId()).ifPresent(System.out::println);//11
        Optional.of(t.getPriority()).ifPresent(System.out::println);//5

    }

}
