package com.wangwenjun.concurrency.chapter7;
/*
    叫号器Runnable版本实现Runnable接口,同步代码块的方式
 */
public class TicketWindowRunnable implements Runnable {
    private int index=1;
    private int max=500;
    private Object monitor = new Object();

    @Override
    public void run() {
        while (true){
            synchronized (monitor){
                if (index > max)
                    break;
                try{
                    Thread.sleep(5);//不会释放锁
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"当前的号码是:"+(index++));
            }
        }
    }
}
