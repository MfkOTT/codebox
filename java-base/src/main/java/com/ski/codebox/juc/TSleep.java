package com.ski.codebox.juc;

/**
 * 测试线程休眠
 * Created by ski.zhou on 21/03/2018.
 */
public class TSleep {
    public static void main(String[] args) {
        ThreadC t1 = new ThreadC("t1");
        t1.start();
    }


}
class ThreadC extends Thread {
    public ThreadC(String name) {
        super(name);
    }

    @Override
    public synchronized void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
                // i整除4时，调用yield
                if (i % 4 == 0)
                    Thread.sleep(3000);
            }
        }catch (Exception e){

        }

    }
}


/**
 * 测试sleep()不会释放锁
 */
class SleepLockTest{

    private static Object obj = new Object();

    public static void main(String[] args){
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

    static class ThreadA extends Thread{
        public ThreadA(String name){
            super(name);
        }
        public void run(){
            // 获取obj对象的同步锁
            synchronized (obj) {
                try {
                    for(int i=0; i <10; i++){
                        System.out.printf("%s: %d\n", this.getName(), i);
                        // i能被4整除时，休眠100毫秒
                        if (i%4 == 0)
                            Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}