package com.ski.codebox.juc;

/**
 * 测试wait方法
 * Created by ski.zhou on 21/03/2018.
 */
public class TWait {

    public static void main(String[] args) {
        Thread t1 = new ThreadA("t1");
        synchronized (t1){
            try {
                //启动线程t1
                System.out.println(Thread.currentThread().getName() + " start t1");
                t1.start();
                //主线程等待t1通过notify()唤醒
                System.out.println(Thread.currentThread().getName() + " wait()");
                t1.wait();
                System.out.println(Thread.currentThread().getName()+" continue");
            }catch (Exception e){

            }
        }
    }
}
class ThreadA extends Thread{
    public ThreadA(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+" call notify()");
            notify();
        }
    }
}
