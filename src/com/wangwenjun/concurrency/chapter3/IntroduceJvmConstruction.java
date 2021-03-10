package com.wangwenjun.concurrency.chapter3;

/**
 * java.lang.StackOverflowError展现
 * 成员变量如果不是静态的变量,不会存入方法区,而是存入堆
 * 当该类实例化之后，放在堆中，堆中这个实例包含了i,bytes
 */
public class IntroduceJvmConstruction {

    private int i = 0;//非静态基本类型存入堆中(所有线程共享)

    private byte[] bytes = new byte[1024];//非静态对象,存入堆

    private static int counter = 0;//静态counter直接存入方法区

    //JVM will create a thread named "main"
    public static void main(String[] args) {
        /*
           一个线程就是一个虚拟机栈,线程调用的每个方法就是虚拟机栈的一个栈帧,
           每个方法从调用到执行完成这个过程，就对应这一个栈帧在虚拟机栈中的入栈到出栈的过程。
         */
        int j=0;//存入一个栈帧的局部变量表中
        int [] arr = new int[1024];//地址存入一个栈针的局部变量表中 内容存入堆中
        try {
            /*
              递归调用,JVM实际会抛出StackOverFlowError:当然如果JVM试图去
              扩展栈空间的的时候失败，则会抛出OutOfMemoryError。
             */
            add(1);
        } catch (Error e) {
            e.printStackTrace();
            System.out.println(counter);//21578
        }

    }

    private static void add(int i) {
        counter++;
        add(i + 1);
    }

}
