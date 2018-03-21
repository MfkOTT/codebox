package com.ski.codebox.juc;

/**
 * 实例锁与全局锁演示
 * Created by ski.zhou on 21/03/2018.
 * pulbic class Something {
 * public synchronized void isSyncA(){}
 * public synchronized void isSyncB(){}
 * public static synchronized void cSyncA(){}
 * public static synchronized void cSyncB(){}
 * }
 * 假设Something有两个实例x,y
 * (01) x.isSyncA()与x.isSyncB() 不能同时被访问，x.isSyncA()与x.isSyncB()访问的都是x对象的同步锁
 * (02) x.isSyncA()与y.isSyncA()  可以被访问，x.isSyncA()与y.isSyncA()访问的是不同对象x,y的同步锁
 * (03) x.cSyncA()与y.cSyncB()     不能同时被访问，x.cSyncA()相当于Something.isSyncA()，y.cSyncB()相当于Something.isSyncB()，因此它们共用一个同步锁，不能被同时反问。
 * (04) x.isSyncA()与Something.cSyncA()可以同时被访问 isSyncA()是实例方法，x.isSyncA()使用的是对象x的锁；而cSyncA()是静态方法，Something.cSyncA()可以理解对使用的是“类的锁”。因此，它们是可以被同时访问的。
 */
public class TSynchronized3 {
    public synchronized void isSyncA() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " : isSyncA");
            }
        } catch (InterruptedException ie) {
        }
    }

    public synchronized void isSyncB() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " : isSyncB");
            }
        } catch (InterruptedException ie) {
        }
    }

    public static synchronized void cSyncA() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " : cSyncA");
            }
        } catch (InterruptedException ie) {
        }
    }

    public static synchronized void cSyncB() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " : cSyncB");
            }
        } catch (InterruptedException ie) {
        }
    }
}

/**
 * 测试01情况
 */
class Test1 {
    TSynchronized3 x = new TSynchronized3();
    TSynchronized3 y = new TSynchronized3();

    private void test() {
        Thread t1 = new Thread(() -> x.isSyncA(), "t1");

        Thread t2 = new Thread(() -> x.isSyncB(), "t2");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        Test1 t = new Test1();
        t.test();
    }
}


/**
 * 测试02情况
 */
class Test2 {
    TSynchronized3 x = new TSynchronized3();
    TSynchronized3 y = new TSynchronized3();

    private void test() {
        Thread t1 = new Thread(() -> x.isSyncA(), "t1");

        Thread t2 = new Thread(() -> y.isSyncA(), "t2");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        Test2 t = new Test2();
        t.test();
    }
}

/**
 * 测试03情况
 */
class Test3 {
    TSynchronized3 x = new TSynchronized3();
    TSynchronized3 y = new TSynchronized3();

    private void test() {
        Thread t1 = new Thread(() -> x.cSyncA(), "t1");

        Thread t2 = new Thread(() -> y.cSyncB(), "t2");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        Test3 t = new Test3();
        t.test();
    }
}

/**
 * 测试04情况
 */
class Test4 {
    TSynchronized3 x = new TSynchronized3();
    TSynchronized3 y = new TSynchronized3();

    private void test() {
        Thread t1 = new Thread(() -> x.isSyncA(), "t1");

        Thread t2 = new Thread(() -> TSynchronized3.cSyncA(), "t2");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        Test4 t = new Test4();
        t.test();
    }
}