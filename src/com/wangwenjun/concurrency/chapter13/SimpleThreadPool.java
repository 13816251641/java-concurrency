package com.wangwenjun.concurrency.chapter13;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 自定义线程池
 */
public class SimpleThreadPool extends Thread{
    private int size;//当前线程池大小
    private final int queueSize;
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();//任务队列
    private static volatile int seq = 0;
    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();//工作线程队列
    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;
    private final DiscardPolicy discardPolicy;
    private volatile boolean destroy = false;
    private int min;
    private int max;
    private int active;

    @Override
    public void run(){
         while (!destroy){
             System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                     this.min,this.active,this.max,this.size,TASK_QUEUE.size());
             try {
                 Thread.sleep(2_000L);
                 if(TASK_QUEUE.size() > active && size < active){
                     for(int i = size; i < active;i++){
                         createWorkTask();
                     }
                     System.out.println("The pool incremented to active.");
                     size = active;
                 }else if(TASK_QUEUE.size() > max && size < max){
                     for(int i=size; i < max; i++){
                         createWorkTask();
                     }
                     System.out.println("The pool incremented to max.");
                     size = max;
                 }

                 synchronized (THREAD_QUEUE){
                     if(TASK_QUEUE.isEmpty() && size > active){
                         System.out.println("=========Reduce=========");
                         int releaseSize = size - active;
                         Iterator<WorkerTask> it = THREAD_QUEUE.iterator();
                         while (it.hasNext()){
                             if(releaseSize < 0){
                                 break;
                             }
                             WorkerTask task = it.next();
                             task.close();
                             task.interrupt();
                             it.remove();
                             releaseSize--;
                         }
                         size = active;
                     }
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

         }
    }

    public  final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard This Task.");
    };

    public static class DiscardException extends RuntimeException{
        public DiscardException(String message){
            super(message);
        }
    }

    public interface DiscardPolicy{
        void discard() throws DiscardException;
    }


    public SimpleThreadPool() {
        this(4,8,12,DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    /**
     *
     * @param
     * @param queueSize 任务队列最大大小
     * @param discardPolicy  拒绝策略
     */
    public SimpleThreadPool(int min,int active,int max,int queueSize,DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    public boolean isDestroy(){
        return this.destroy;
    }

    public int getQueueSize(){
        return queueSize;
    }

    /**
     * 创建工作线程
     */
    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }


    public void shutdown() throws InterruptedException{
        while (!TASK_QUEUE.isEmpty()){
            Thread.sleep(50);
        }
        synchronized (THREAD_QUEUE){
            int initVal=THREAD_QUEUE.size();//线程池线程个数
            while (initVal > 0){
                for (WorkerTask task : THREAD_QUEUE){
                    if (task.getTaskState() == TaskState.BLOCKED){ //个人认为这里有问题
                        task.interrupt();
                        task.close();//设为DEAD
                        initVal--;
                    }else{
                        Thread.sleep(10);
                    }
                }
            }
        }
        this.destroy = true;
        System.out.println("The thread pool disposed.");

    }


    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkTask();
        }
        this.size = this.min;
    }

    public void submit(Runnable runnable) {
        if (destroy){
            throw new IllegalStateException("The thread pool already destroy and not allow submit task");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize)
                discardPolicy.discard();
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

    }

    public static void main(String[] args) throws InterruptedException{
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        simpleThreadPool.start();
        IntStream.rangeClosed(0,40).forEach(i->{
            simpleThreadPool.submit(()->{
                System.out.println("The runnable "+i+" be serviced by "+Thread.currentThread().getName()+" start.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable "+i+" be serviced by "+Thread.currentThread().getName()+" end.");
            });
        });
          /*
                Thread.sleep(10000);
                simpleThreadPool.shutdown();//会阻塞
                simpleThreadPool.submit(()->{
                    System.out.println("===========");
                });
            */
    }
}
