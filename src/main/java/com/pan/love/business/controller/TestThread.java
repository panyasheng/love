package com.pan.love.business.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 测试线程（单线程，多线程，线程池）
 *
 * @author pan
 * @date 2019/11/13
 */
public class TestThread {
    static ArrayBlockingQueue arrayBlockingQueue=new ArrayBlockingQueue(3);


    public static void main(String[] args) {
        TestThread testThread=new TestThread();
//        testThread.singleThread();
//        testThread.multiThread();
//        testThread.multiThread();
//        testThread.multiThread();



        ThreadPool(3,arrayBlockingQueue).execute(
                () -> {

                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                    testThread.TestTimeThread();
                }

        );
        ThreadPool(3,arrayBlockingQueue).execute(
                () -> {
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                    testThread.TestTimeThread2();
                }

        );




        ThreadPool(3,arrayBlockingQueue).shutdown();

    }

    public void singleThread(){
        long begin=System.currentTimeMillis();
        for(int i=0;i<=1000;i++){
            System.out.println("测试："+i);
        }
        System.out.println(System.currentTimeMillis()-begin);
    }

    public void multiThread(){
        long begin=System.currentTimeMillis();
        for(int j=0;j<=10;j++){
            System.out.println("线程："+j);
            new Thread(){
                @Override
                public void run() {

                    for(int i=0;i<=1000;i++){
                        System.out.println("测试："+i);
                    }
                }
            }.start();
        }

        System.out.println(System.currentTimeMillis()-begin);
    }

    public static ExecutorService ThreadPool(int num,ArrayBlockingQueue arrayBlockingQueue){
        ExecutorService fixedThreadPool =new ThreadPoolExecutor(
                num,
                num,
                1,
                TimeUnit.SECONDS,
                arrayBlockingQueue,
                new ThreadFactoryBuilder().setNameFormat("myTest-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        fixedThreadPool.execute(()->System.out.println(Thread.currentThread().getName()));
        return fixedThreadPool;

    }

    public void TestTimeThread(){
        int i=0;
        while (i<100){
            i++;
            int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    System.out.println("测试线程1"+ finalI);
                    try {
                        Thread.sleep(100000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }

    public void TestTimeThread2(){
        int i=0;
        while (i<100){
            i++;
            int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    System.out.println("测试线程2"+ finalI);
                    try {
                        Thread.sleep(100000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
}
