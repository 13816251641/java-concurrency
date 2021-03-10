package com.wangwenjun.concurrency.chapter14;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Package: com.wangwenjun.concurrency.chapter14
 * @ClassName: UseReentrantWriteLock
 * @Author: lujieni
 * @Description: 1
 * @Date: 2021-03-09 14:30
 * @Version: 1.0
 *
 * 读-读 可以共存
 * 读-写 不能共存
 * 写-写 不能共存
 *
 * 一个线程要想同时持有写锁和读锁，必须先获取写锁再获取读锁；写锁可以“降级”为读锁；读锁不能“升级”为写锁。
 *
 *
 */
public class UseReentrantWriteLock {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for(int i = 1;i <= 5;i++){
            final int temp = i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }


        for(int i = 1;i <= 5;i++){
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }




    }
}

class MyCache{

    private volatile Map<String,Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * @Description: 写入缓存 写锁,独占锁
     * @param
     * @return:
     * @Author: lujieni
     * @Date: 2021/3/9
     */
    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"写入"+key);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"写入OK");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"读取"+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取OK,value:"+o);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }

    }




}