package com.ski.codebox.juc;

/**
 * 测试关键字synchronized
 * Created by ski.zhou on 21/03/2018.
 */
public class TSynchronized {

    public static void main(String[] args) {
//        Runnable runnable = new MyRunable();
//        Thread t1 = new Thread(runnable,"t1");
//        Thread t2 = new Thread(runnable,"t2");
//        t1.start();
//        t2.start();
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        t1.start();
        t2.start();

    }
}

/**
 * 此时synchronized获取到的是MyRunable的同步锁，t1和t2共用一个MyRunable对象
 */
class MyRunable implements  Runnable{

    @Override
    public void run() {
        synchronized (this){
            try {
                for (int i=0;i<5;i++){
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName()+" loop "+i);
                }
            }catch (Exception e){

            }
        }
    }
}
/**
 * 此时synchronized获取到的是MyThread的同步锁，t1和t2拥有各自的MyThread对象
 */
class MyThread extends  Thread{
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this){
        try {
            for (int i=0;i<5;i++){
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+" loop "+i);
            }
        }catch (Exception e){

        }
        }
    }
}
