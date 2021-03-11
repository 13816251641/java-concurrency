package com.wangwenjun.concurrency.chapter14.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Package: com.wangwenjun.concurrency.chapter14
 * @ClassName: UseCyclicBarrier
 * @Author: lujieni
 * @Description: 使用UseCyclicBarrier
 * @Date: 2021-03-09 11:03
 * @Version: 1.0
 *
 * 在CyclicBarrier类的内部有一个计数器，每个线程在到达屏障点的时候都会调用await方法
 * 将自己阻塞,此时计数器会减1，当计数器减为0的时候所有因调用await方法而被阻塞的线程将被唤醒。
 *
 * 被直接打断的线程抛InterruptedException;
 * 其余的线程抛因为打翻栅栏操作而被唤醒则抛出异常:BrokenBarrierException
 * 整盘游戏结束
 * cyclicBarrier.await()
 *
 */
public class UseCyclicBarrier {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println(Thread.currentThread().getName()+":"+"召唤神龙成功!");
        });

        Thread [] threads = new Thread[7];

        for(int i = 0;i < 7;i++){
            final int temp = i;
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集" + temp + "个龙珠");
                try {
                    if (temp == 1) {
                        Thread.sleep(500);
                    }
                    cyclicBarrier.await();//阻塞 计数器-1
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName());
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":执行结束");
            }, String.valueOf(i));
            threads[i].start();
        }

        threads[5].interrupt();
    }


}