package com.briup.smart.env.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool2 {
    public static void main(String[] args) {
        //1.使用一个工具类Executors
        //定长的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        threadPool.execute(()->{});
        ExecutorService pool2 = Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();

    }
}
