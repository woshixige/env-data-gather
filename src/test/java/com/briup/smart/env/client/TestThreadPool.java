package com.briup.smart.env.client;

import org.junit.Test;

import java.util.concurrent.*;

public class TestThreadPool {
    public static void main(String[] args) {
        new TestThreadPool().method();
    }


    public void method(){
        //1.创建一个有界阻塞队列：容量为10 将没有执行的任务存放在阻塞队列中进行保存
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(10);
        //创建一个无界阻塞队列 不限制大小
        LinkedBlockingQueue linkedQueue = new LinkedBlockingQueue<>();

        //2.创建一个线程池对象
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,2, TimeUnit.SECONDS,linkedQueue);
        ExecutorService executor = Executors.newCachedThreadPool();

        //3.创建任务
        Runnable r = () ->{
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name+" 开始执行任务");
                Thread.sleep(1000);
                System.out.println(name+" 结束执行任务");
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        /*
            3.执行2次任务
            当任务数=4  执行线程3个
            当任务数=16 执行线程6个任务 在阻塞队列中任务10个
            当任务数=17 执行线程6个任务 在阻塞队列中任务10个   1个任务不能执行也不能存储 导致异常：RejectedExecutionException
         */
        for (int i = 0; i < 30; i++) {// 3个任务直接执行 10个任务 阻塞队列  1个任务被新的线程Thread-4执行
            executor.execute(r);
           // int num = executor.getActiveCount();
            //int size = linkedQueue.size();
            //System.out.println("活动线程数:"+num+"阻塞队列的大小："+size);
        }
        //4.任务执行结束，关闭线程池，否则一直运行
        executor.shutdown();
    }
}
