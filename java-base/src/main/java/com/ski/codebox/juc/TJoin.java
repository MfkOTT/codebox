package com.ski.codebox.juc;

/**
 *main线程会等待ThreadA执行完毕之后再执行
 * Created by ski.zhou on 21/03/2018.
 */
public class TJoin {
    public static void main(String[] args) {

        try {
            ThreadA t1 = new ThreadA("t1");
            t1.start();
            //将t1加入到main线程中，main线程会等待t1z执行完毕
            t1.join();
            System.out.printf("%s finish\n", Thread.currentThread().getName());

        }catch (Exception e){}
    }

    static class ThreadA extends Thread{
        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.printf("%s start\n", this.getName());
            //延时操作
            for(int i=0; i <1000000; i++);
            System.out.printf("%s finish\n", this.getName());
        }
    }
}
