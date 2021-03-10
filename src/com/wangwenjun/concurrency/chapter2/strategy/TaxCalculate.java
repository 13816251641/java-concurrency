package com.wangwenjun.concurrency.chapter2.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
   计算税率类
   策略模式上下文
 */
@Data
public class TaxCalculate {
    private Double salary; //工资
    private Double bonus; //奖金
    private CalculatorStrategy calculatorStrategy; //策略接口

    public TaxCalculate(Double salary,Double bonus,CalculatorStrategy calculatorStrategy){
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    public Double calcTax(){
        return calculatorStrategy.calc(salary,bonus);
    }

}
