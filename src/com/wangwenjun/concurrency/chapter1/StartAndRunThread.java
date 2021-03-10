package com.wangwenjun.concurrency.chapter1;

/**
    main方法是非守护线程
    当java虚拟机启动时，通常有一个非守护进程线程（通常调用某些指定类的名为main的方法）,
    我们可以使用jconsole工具进行查看
 */
public class StartAndRunThread {

    public static void main(String[] args) {
        new Thread("read-thread"){
            @Override
            public void run() {
                readFromDataBase();
            }
        }.start();
        writeDataToFile();
    }


    private static void readFromDataBase(){
        // read data from database and handle it
        try {
            System.out.println("Begin read data from db.");
            Thread.sleep(1000 * 20L );
            System.out.println("Read data done and start handle from it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The data handle finish and successfully");
    }

    private static void writeDataToFile(){
        try {
            System.out.println("Begin write data to file.");
            Thread.sleep(2000 * 10L);
            System.out.println("Write data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The data handle finish and successfully");
    }





}
