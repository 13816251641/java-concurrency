package com.wangwenjun.concurrency.chapter6;


/**
 * 优雅关闭线程,通过设置开关数据
 * 这里start不需要加volatile
 */
public class ThreadCloseGraceful {

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.shutdown();//外部类可以调用内部类私有的方法
    }

    /**
     * 静态内部类
     */
     static class Worker extends Thread{
        private boolean start = true;
        @Override
        public void run() {
            while (start){
                System.out.println("lalalala");
            }
        }
        private void shutdown(){
            start = false;
        }
    }
}


