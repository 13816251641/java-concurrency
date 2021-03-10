package com.wangwenjun.concurrency.chapter6;

/**
 * 优雅关闭线程2
 * 使用打断的api,在执行线程中进行状态查询
 */
public class ThreadCloseGraceful2 {
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        Worker worker2 = new Worker();
        worker2.start();

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.interrupt();//打断worker线程,这里只是设置线程的中断状态
        worker2.interrupt();//打断worker2线程,这里只是设置线程的中断状态
    }

     static class Worker extends Thread{
        @Override
        public void run() {
            while (true) {
                System.out.println("hello");
                /*
                  哪个线程调用这段代码就测试这个线程是否已被中断,并清除线程的中断状态
                  interrupted是Thread的静态方法,Worker继承了Thread当然能直接调用。
                  这里加了Thread是要强调这是静态的方法 */

                  if(Thread.interrupted())
                     break;



                /* 这样也可以达到效果
                if(super.isInterrupted())
                    break;
                 */
            }
            System.out.println("线程中断");
        }
  }
}
