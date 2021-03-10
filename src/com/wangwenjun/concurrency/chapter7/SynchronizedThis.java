package com.wangwenjun.concurrency.chapter7;

/**
    m1和m2方法的锁都是this,即ThisLock对象,
    对于同一个线程而言是可重入的,但对于不
    同的线程而言,只能当m1执行完毕后m2方法
    才能执行
 */
public class SynchronizedThis {
    public static void main(String[] args) {
        ThisLock thisLock = new ThisLock();
        new Thread(()->{thisLock.m1();},"t1").start();
        new Thread(()->{thisLock.m2();},"t2").start();

    }


}

class ThisLock{
    public synchronized void m1(){
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10_000);//不释放锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void m2(){
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



