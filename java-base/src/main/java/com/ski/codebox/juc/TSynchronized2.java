package com.ski.codebox.juc;

/**
 *
 * Created by ski.zhou on 21/03/2018.
 */
public class TSynchronized2 {
    public synchronized void syncMethod(){
        for (int i=0;i<1000000;i++){

        }
    }
    public void syncBlock(){
       synchronized (this){
           for (int i=0;i<1000000;i++){

           }
       }
    }

    public static void main(String[] args) {
        TSynchronized2 tSynchronized2 = new TSynchronized2();
        long start,diff;
        start = System.currentTimeMillis();
        tSynchronized2.syncMethod();
        diff = System.currentTimeMillis()-start;
        System.out.println("syncMethod()"+diff);

        start = System.currentTimeMillis();
        tSynchronized2.syncBlock();
        diff = System.currentTimeMillis()-start;
        System.out.println("syncBlock()"+diff);
    }
}
