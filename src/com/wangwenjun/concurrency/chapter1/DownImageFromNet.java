package com.wangwenjun.concurrency.chapter1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Package: com.wangwenjun.concurrency.chapter1
 * @ClassName: DownImageFromNet
 * @Author: lujieni
 * @Description: 从网上下载图片
 * @Date: 2021-02-23 10:09
 * @Version: 1.0
 */
public class DownImageFromNet implements Runnable {

    private String url;
    private String path;

    public DownImageFromNet(String url,String path){
        this.url = url;
        this.path = path;
    }

    public void downImageFromNet(String url, String path){
        try {
            FileUtils.copyURLToFile(new URL(url),new File(path));
        } catch (IOException e) {
            System.out.println("下载图片报错:");
        }
    }

    @Override
    public void run() {
        downImageFromNet(url,path);
    }

    public static void main(String[] args) {
        DownImageFromNet t1 = new DownImageFromNet("https://bkimg.cdn.bcebos.com/pic/96dda144ad3459829291943b06f431adcbef847b?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg","f:/1.jpg");
        DownImageFromNet t2 = new DownImageFromNet("https://bkimg.cdn.bcebos.com/pic/96dda144ad3459829291943b06f431adcbef847b?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg","f:/2.jpg");
        DownImageFromNet t3 = new DownImageFromNet("https://bkimg.cdn.bcebos.com/pic/96dda144ad3459829291943b06f431adcbef847b?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg","f:/3.jpg");

        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();

    }

}



