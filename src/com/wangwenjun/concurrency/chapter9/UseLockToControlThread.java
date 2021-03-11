package com.wangwenjun.concurrency.chapter9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package: com.wangwenjun.concurrency.chapter9
 * @ClassName: UseLockToControlThread
 * @Author: lujieni
 * @Description: 使用lock控制线程的执行顺序
 * @Date: 2021-03-03 15:27
 * @Version: 1.0
 *
 * conditionA.await(); 要先获得锁
 */
public class UseLockToControlThread {

    public static void main(String[] args) {
        Handler handler = new Handler();
        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodA();
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodA();
            }
        }).start();



        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodB();
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodB();
            }
        }).start();


        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodC();
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<150;i++){
                handler.methodC();
            }
        }).start();
    }


}

class Handler{
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private int num = 1;

    public void methodA(){
        lock.lock();
        try{
            while(num != 1){
                conditionA.await();//释放锁
            }
            System.out.println("methodA:"+num);
            num++;
            conditionB.signal();//并不会释放锁,要配合unlock一起使用
        }catch (Exception e){

        }finally {
           lock.unlock();
        }
    }

    public void methodB(){
        lock.lock();
        try{
            while(num != 2){
                conditionB.await();
            }
            System.out.println("methodB:"+num);
            num++;
            conditionC.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }

    public void methodC(){
        lock.lock();
        try{
            while(num != 3){
                conditionC.await();
            }
            System.out.println("methodC:"+num);
            num = 1;
            conditionA.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }


}


