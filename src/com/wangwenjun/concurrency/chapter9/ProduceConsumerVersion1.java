package com.wangwenjun.concurrency.chapter9;

/**
 * 生产者消费者模式1
 * 2个线程没有互相通知
 */
public class ProduceConsumerVersion1 {

    private int i = 0;

    private final Object LOCK = new Object();

    private void produce() {
        synchronized (LOCK) {
            System.out.println("P->" + (++i));
        }
    }

    private void consume() {
        synchronized (LOCK) {
            System.out.println("C->" + (i));
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion1 produceConsumerVersion1 = new ProduceConsumerVersion1();
        new Thread(() -> {
            while (true) {
                produceConsumerVersion1.produce();
            }
        }, "P").start();

        new Thread(() -> {
            while (true) {
                produceConsumerVersion1.consume();
            }
        }, "C").start();

    }


}
