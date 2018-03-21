package com.ski.codebox.juc;

/**
 * synchronized测试2
 * 同步代码块和非同步代码块
 * Created by ski.zhou on 21/03/2018.
 * 结论：当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程仍然可以访问“该对象”的非同步代码块。
 */
public class Tsynchronized1 {
    //含有synchronized代码块方法
    public void syncMethod() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                }
            } catch (Exception e) {

            }
        }
    }

    //含有synchronized代码块方法
    public void noSyncMethod() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " noSyncMethod loop " + i);
            }
        } catch (Exception e) {

        }
    }

}

class Demo {
    public static void main(String[] args) {
        final Tsynchronized1 tsynchronized1 = new Tsynchronized1();

        //新建t1,t2线程，分别调用syncMethod，noSyncMethod方法
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                tsynchronized1.syncMethod();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                tsynchronized1.noSyncMethod();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
