package com.wangwenjun.concurrency.chapter1;

/**
 * @Package: com.wangwenjun.concurrency.chapter1
 * @ClassName: Race
 * @Author: lujieni
 * @Description: 龟兔赛跑
 * @Date: 2021-02-23 13:53
 * @Version: 1.0
 */
public class Race implements Runnable {

    private String winnerName;

    @Override
    public void run() {
        for(int i = 0;i<=100;i++){
            if("兔子".equals(Thread.currentThread().getName()) && i%10 == 0){ //Thread.currentThread().getName()获得当前线程的名字
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(gameOver(i)){
                break;
            }
            System.out.println(Thread.currentThread().getName()+":跑了"+i);
        }
    }

    private boolean gameOver(int i){
        if(winnerName != null){
            return true;
        }
        if(i == 100){
            if("兔子".equals(Thread.currentThread().getName())){
                System.out.println("兔子赢了");
                winnerName = "兔子";
                return true;
            }
            if("乌龟".equals(Thread.currentThread().getName())){
                System.out.println("乌龟赢了");
                winnerName = "乌龟";
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }
}