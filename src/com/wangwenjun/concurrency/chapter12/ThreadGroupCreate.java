package com.wangwenjun.concurrency.chapter12;


public class ThreadGroupCreate {

    public static void main(String[] args) throws Exception {

        ThreadGroup tg1 = new ThreadGroup("TG1");//将main线程的threadGroup当做父threadGroup
        Thread t1 = new Thread(tg1,"t1"){
            @Override
            public void run() {
                System.out.println(getThreadGroup().getName());//此线程的threadGroup为TG1
                System.out.println(getThreadGroup().getParent().getName());//父threadGroup为main
                System.out.println(getThreadGroup().getParent().activeCount());//3
            }
        };
        t1.start();

        Thread.sleep(1000);

        ThreadGroup tg2 = new ThreadGroup(tg1,"TG2");
        System.out.println(tg2.getName());//TG2
        System.out.println(tg2.getParent());//java.lang.ThreadGroup[name=TG1,maxpri=10]



    }

}
