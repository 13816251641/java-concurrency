package com.wangwenjun.concurrency.chapter2.strategy;

/**
    策略模式客户端代码
 */
public class TaxCalculateMain {
    public static void main(String[] args) {
        TaxCalculate tc = new TaxCalculate(3000D,7000D,(a,b)-> a*0.5+b*0.5);
        double calc = tc.calcTax();
        System.out.println(calc);
    }
}
