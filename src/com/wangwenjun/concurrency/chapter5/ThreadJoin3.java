package com.wangwenjun.concurrency.chapter5;

/**
    一个主task,多个从task,要使用join,主task才会等待所有从task结束而结束
 */
public class ThreadJoin3 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new CaptureRunnable("M1",10000));
        Thread t2 = new Thread(new CaptureRunnable("M2",30000));
        Thread t3 = new Thread(new CaptureRunnable("M3",15000));

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("All are successful");
    }
}

class CaptureRunnable implements Runnable{

    private String machineName;

    private long spendTime;

    public CaptureRunnable(String machineName,long spendTime){
        this.machineName=machineName;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        //do the real capture data
        try {
            Thread.sleep(spendTime);
            System.out.println(machineName+" completed data capture and successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
