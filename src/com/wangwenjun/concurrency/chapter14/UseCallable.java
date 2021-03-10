package com.wangwenjun.concurrency.chapter14;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.wangwenjun.concurrency.chapter14
 * @ClassName: UseCallable
 * @Author: lujieni
 * @Description: 使用callable启动线程
 * @Date: 2021-03-08 14:03
 * @Version: 1.0
 *
 * 1.futureTask.get()会阻塞
 * 2.futureTask会有缓存
 * 3.Callable可以有返回值
 */
public class UseCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();
        new Thread(futureTask).start();//结果会被缓存,效率高

        Integer result = (Integer)futureTask.get();//这个get方法可能会产生阻塞!
        System.out.println(result);
    }

}

class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("call");
        TimeUnit.SECONDS.sleep(10);
        return 1;
    }
}