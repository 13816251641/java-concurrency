package com.wangwenjun.concurrency.chapter10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * 谁获得的锁,谁去释放
 */
public class BooleanLock implements Lock{


    //The initValue is true indicated the lock have be get
    //The initValue is false indicated the lock is free(other thread can get this.)
    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock(){
        this.initValue=false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue){ //不能把while->if
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.initValue=true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if(mills <0)
            lock();
        else{
            long hasRemaining = mills;
            long endTime = System.currentTimeMillis()+mills;//最晚结束时间
            while (initValue){ //unlock才置为false
                if(hasRemaining <= 0){
                    blockedThreadCollection.remove(Thread.currentThread());
                    throw new TimeOutException("Time out");
                }
                blockedThreadCollection.add(Thread.currentThread());
                this.wait(mills);//自己觉醒(别人要释放锁)&别人notify
                hasRemaining = endTime - System.currentTimeMillis();
            }
            blockedThreadCollection.remove(Thread.currentThread());
            this.initValue=true;
            this.currentThread = Thread.currentThread();
        }
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == currentThread){
            this.initValue=false;
            Optional.of(Thread.currentThread().getName()+" release the lock monitor.")
                    .ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
