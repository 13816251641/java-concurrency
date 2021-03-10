package com.wangwenjun.concurrency.chapter9;


import java.util.stream.Stream;

/**
 * 生产者消费者模式2 单生产单消费
 * wait()会立刻释放synchronized（obj）中的obj锁，以便其他线程可以得到锁
 * 但是notify()不会立刻释放sycronized（obj）中的obj锁,必须要等notify()
 * 所在线程执行完synchronized（obj）块中的所有代码才会释放这把锁.
 */
public class ProduceConsumerVersion2 {
    private int i = 0;
    private final Object LOCK = new Object();
    private boolean isProduced = false;

    private void produce(){
        synchronized (LOCK){
            if(isProduced){
                try {
                    LOCK.wait();//wait立即释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                i++;
                System.out.println("P->"+i);
                //TODO notify其他线程,但不立即释放锁
                LOCK.notify();//notify不会立即释放锁
                isProduced = true;
            }
        }
    }

    private void consume(){
        synchronized (LOCK){
           if(isProduced){
               System.out.println("C->"+i);
               LOCK.notify();//notify不会立即释放锁
               isProduced = false;
           }
           else{
               try {
                   LOCK.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion2 produceConsumerVersion2 = new ProduceConsumerVersion2();

        Stream.of("a").forEach(e->{
            new Thread(() -> {
                while (true) {
                    produceConsumerVersion2.produce();
                }
            }).start();
        });

        Stream.of("1").forEach(e->{
            new Thread(() -> {
                while (true) {
                    produceConsumerVersion2.consume();
                }
            }).start();
        });


      /*  new Thread(() -> {
            while (true) {
                produceConsumerVersion2.produce();
            }
        }, "P").start();

        new Thread(() -> {
            while (true) {
                produceConsumerVersion2.consume();
            }
        }, "C").start();*/
    }
}
