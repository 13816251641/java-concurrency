package com.wangwenjun.concurrency.chapter9;


import java.util.stream.Stream;

/**
 * 多消费者多生产者模式的代码,比原本改进如下1点
 * 1.notify->notifyAll
 *
 */
public class ProduceConsumerVersion3 {
    private int i=0;
    private final Object LOCK = new Object();
    private boolean isProduced =false;

    private void produce(){
        synchronized (LOCK){
            if (isProduced){
                try {
                    LOCK.wait();//wait立即释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                i++;
                System.out.println("P->"+i);
                //TODO  notifyAll消费者 不立即释放锁
                LOCK.notifyAll();
                isProduced = true;
            }

        }
    }

    private void consume(){
        synchronized (LOCK){
            if (!isProduced){
                try {
                    LOCK.wait();//wait立即释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("C->"+i);
                LOCK.notifyAll();
                isProduced = false;
            }

        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion3 produceConsumerVersion3 = new ProduceConsumerVersion3();
        Stream.of("P1","P2").forEach(n->
                new Thread(() -> {
                    while (true) {
                        produceConsumerVersion3.produce();
                    }
                }).start());

        Stream.of("C1","C2").forEach(n->
                new Thread(() -> {
                    while (true) {
                        produceConsumerVersion3.consume();
                    }
                }).start());
    }
}
