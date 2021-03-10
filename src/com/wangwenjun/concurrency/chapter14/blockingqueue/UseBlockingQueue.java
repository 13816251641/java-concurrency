package com.wangwenjun.concurrency.chapter14.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.wangwenjun.concurrency.chapter14.blockingqueue
 * @ClassName: UseBlockingQueue
 * @Author: lujieni
 * @Description: 1
 * @Date: 2021-03-10 16:07
 * @Version: 1.0
 */
public class UseBlockingQueue {

    public static void main(String[] args) throws Exception {
        test04();
    }

    /**
     * @Description:抛出异常
     * @param
     * @return:
     * @Author: lujieni
     * @Date: 2021/3/10
     */
    private static void test01(){
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.add(2));
        System.out.println(blockingQueue.add(3));

        // IllegalStateException: Queue full 抛出异常
        // System.out.println(blockingQueue.add(4));

        System.out.println(blockingQueue.element());//查看队首元素是啥,如果没有会抛出异常

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // java.util.NoSuchElementException 抛出异常
        System.out.println(blockingQueue.remove());

    }

    private static void test02(){
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(2));
        System.out.println(blockingQueue.offer(3));

        // false 不抛出异常
        // System.out.println(blockingQueue.offer(4));

        System.out.println(blockingQueue.peek());//队首元素是啥,不会抛出异常

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//null 不抛出异常
    }

    /**
     * @Description: 等待,一直阻塞
     * @param
     * @return:
     * @Author: lujieni
     * @Date: 2021/3/10
     */
    private static void test03() throws InterruptedException{
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);

        // blockingQueue.put(4);一直阻塞

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());// 没有这个元素,一直阻塞

    }

    /**
     * @Description: 超时退出
     * @param
     * @return:
     * @Author: lujieni
     * @Date: 2021/3/10
     */
    private static void test04() throws InterruptedException{
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(2));
        System.out.println(blockingQueue.offer(3));

        //System.out.println(blockingQueue.offer(4, 2, TimeUnit.SECONDS));//等待2秒后如果还是满的就退出

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));//等待2秒就退出


    }


}