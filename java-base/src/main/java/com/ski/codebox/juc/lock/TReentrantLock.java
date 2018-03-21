package com.ski.codebox.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试互斥锁ReentrantLock
 * Created by ski.zhou on 21/03/2018.
 */
public class TReentrantLock {
    public static void main(String[] args) {
        Depot depot = new Depot(40);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);
        producer.produce(10);
        producer.produce(50);
        consumer.consume(20);
        consumer.consume(50);
    }


}
class Depot{
    private int size;//仓库实际数量
    private int capacity;//仓库容量
    private Lock lock;//独占锁
    private Condition fullCondition;//生产条件
    private Condition emptyCondition;//消费条件

    public Depot(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.fullCondition = lock.newCondition();
        this.emptyCondition = lock.newCondition();
    }
    public void produce(int value){
        lock.lock();
        try {
            //left表示想要生产的数量，数量可能超过capacity，此时需要分多次生产
            int left = value;

            while (left>0){
                //库存已满时，等待消费者消费产品
                while (size>=capacity){
                    fullCondition.await();
                }
                // 获取“实际生产的数量”(即库存中新增的数量)
                // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                // 否则“实际增量”=“想要生产的数量”
                int inc = size+left>capacity?capacity-size:left;
                size+=inc;
                left-=inc;
                System.out.printf("%s produce(%d) --> size=%d\n",
                        Thread.currentThread().getName(), value, size);
            }
            //通知消费者可以消费产品了
           emptyCondition.signal();

        }catch (Exception e){}
        finally {
            lock.unlock();
        }

    }
    public void consume(int value){
        lock.lock();
        try {
            //想要消费的数量，此数量可能超过剩余库存，此时需要分多次消费
            int left = value;
            //
            while (left>0){
                //库存小于等于0时，等待生产者生产产品
                while (size==0){
                    emptyCondition.await();
                }
                // 获取“实际消费的数量”(即库存中减少的数量)
                // 如果“库存”-“想要消费的数量”<=“0”，则“实际减少量”=“当前容量”。(此时清空仓库)
                // 否则“实际消费量”=“想要消费的数量”
                int dec = size-left<=0?size:left;
                size-=dec;
                left-=dec;
                System.out.printf("%s consume(%d) --> size=%d\n",
                        Thread.currentThread().getName(), value, size);
                fullCondition.signal();
            }

        }catch (Exception e){}
        finally {
            lock.unlock();
        }

    }
}

/**
 * 生产者
 */
class Producer{
    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }
    //新建一个线程往仓库生产产品
    public void produce(final int val){
        new Thread(() -> depot.produce(val)).start();
    }
}

/**
 * 消费者
 */
class Consumer{
    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }
    //新建一个线程从仓库消费产品
    public void consume(final int val){
        new Thread(() -> depot.consume(val)).start();
    }
}