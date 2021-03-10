package com.wangwenjun.concurrency.chapter2;

/**
    叫号器代码,这里虽然new多个Thread,但因为MAX和index都是静态变量
    所以只是多个线程共用这2个变量而已
 */
public class TicketWindow extends Thread {
    private static final int MAX = 50;//静态常量
    /*
       阿君说static变量随着类被加载就会存在于jvm中,
       当类被销毁后仍然会存在,直到jvm结束才会消亡
     */
    private static int index = 1;

    private final String name;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println("柜台:" + name + "当前的号码是:" + (index++));
        }
    }

    /**
     * System.out.println乱序输出
     * @param args
     */
    public static void main(String[] args) {
        TicketWindow t1 = new TicketWindow("一号柜台");
        TicketWindow t2 = new TicketWindow("二号柜台");
        TicketWindow t3 = new TicketWindow("三号柜台");
        TicketWindow t4 = new TicketWindow("四号柜台");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
