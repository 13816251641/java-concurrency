package com.wangwenjun.concurrency.chapter9;


import java.util.*;

/**
 * 启动10个线程,但最多只能有5个线程开始业务操作,其他等待
 * 这里是自己做的嘛,有局限性。其实完全可以只启动5个线程,可以利用线程池
 * 技术来实现,不需要启动10个线程。
 *
 * 你切换线程的状态就会消耗资源
 */
public class CaptureService {

    private static final LinkedList<Control> CONTROLS = new LinkedList<>();//公用资源要加锁

    private static final int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();//10个线程
        Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()//10个线程
                .map(CaptureService::createCaptureThread)
                .forEach(t->{
                    t.start();//线程开始执行任务
                    worker.add(t);//将线程放入list
                });
        worker.stream().forEach(t->{
            try {
                t.join();//main线程等到t线程完成后才可以继续运行下去
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All of capture work finished").ifPresent(System.out::println);
    }

    /**
     *
     * @param name 线程名字
     * @return 线程
     */
    private static Thread createCaptureThread(String name){
        return new Thread(()->{
           Optional.of("The worker["+Thread.currentThread().getName()+"] begin capture data").ifPresent(System.out::println);
           synchronized (CONTROLS){
                while (CONTROLS.size() > MAX_WORKER){ //
                    try {
                        CONTROLS.wait();//立即释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               CONTROLS.add(new Control());//list不是线程安全的,对于list的操作在多线程的环境下都要加锁
           }
           Optional.of("The worker["+Thread.currentThread().getName()+"] is working...").ifPresent(System.out::println);
           /*
              正常的业务代码
            */
           try {
               Thread.sleep(10000);
           } catch (InterruptedException e) {
                e.printStackTrace();
           }

           synchronized (CONTROLS){
               Optional.of("The worker["+Thread.currentThread().getName()+"] end capture data.").ifPresent(System.out::println);
               CONTROLS.removeFirst();//删掉的不是自己,list不是线程安全的,对于list的操作在多线程的环境下都要加锁
               CONTROLS.notifyAll();
           }

        },name);

    }

    private static class Control{

    }

}


