package com.wangwenjun.concurrency.chapter2.strategy;


/**
    策略模式接口
 */
@FunctionalInterface
public interface CalculatorStrategy {
    Double calc(Double salary, Double bonus);
}
