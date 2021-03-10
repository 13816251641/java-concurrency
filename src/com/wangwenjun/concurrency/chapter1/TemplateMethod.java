package com.wangwenjun.concurrency.chapter1;

/**
 * 模板设计模式,可以类比Thread方法
 */
public abstract class TemplateMethod {

    /**
        final保证子类无法覆写此方法
     */
    public final void print(String message){
        System.out.println("##########################");
        wrapPrint(message);
        System.out.println("##########################");
    }

    protected abstract void wrapPrint(String message);

    public static void main(String[] args) {
        TemplateMethod t = new TemplateMethod(){ //抽象类的实现类
            @Override
            protected void wrapPrint(String message) {
                System.out.println("*@"+message+"@*");
            }
        };
        t.print("hello world"); //print调用的是父类的方法 wrapPrint调用的是子类覆盖的方法
    }


}
