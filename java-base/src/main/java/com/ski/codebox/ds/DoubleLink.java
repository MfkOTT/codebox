package com.ski.codebox.ds;

/**
 * Created by ski.zhou on 26/03/2018.
 * 用java实现一个双向链表
 */
public class DoubleLink<T> {

    //表头
    private DNode mHead;
    //节点个数
    private int mCount;
    //节点对应结构体
    private class DNode<T>{

        public DNode prev;
        public DNode next;
        public T value;

        public DNode(DNode prev, DNode next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    //构造函数
    public DoubleLink() {
        //创建表头
        mHead = new DNode<T>(null,null,null);
        mHead.prev=mHead.next=mHead;
        //初始化节点个数
        mCount=0;
    }

    //返回节点数目
    public int size(){
        return mCount;
    }

    //判断列表是否为空
    public boolean isEmpty(){
        return mCount==0;
    }

    public T get(int index){
        return getNode(index).value;
    }

    //获取链表index处的节点
    private DNode<T> getNode(int index) {
        if (index<0||index>=mCount)
            throw new IndexOutOfBoundsException();
        //正向查找
        if (index<=mCount/2){
            DNode node = mHead.next;
            for (int i=0;i<index;i++){
                node = node.next;
            }
            return node;
        }
        //反向查找
        DNode node = mHead.prev;
        int rIndex = mCount-index-1;
        for (int j=0;j<rIndex;j++){
            node = node.prev;
        }
        return node;
    }
    //获取第一个节点
    public T getFirst(){
        return getNode(0).value;
    }
    //获取最后一个节点
    public T getLast(){
        return getNode(mCount-1).value;
    }
    //将节点插入index位置前
    public void insert(int index,T t){

        if (index == 0){
            DNode<T> node = new DNode<T>(mHead,mHead.next,t);
            mHead.next.prev = node;
            mHead.next = node;
            mCount++;
            return;
        }
        DNode iNode= getNode(index);
        DNode tNode = new DNode(iNode.prev,iNode,t);
        iNode.prev.next = tNode;
        iNode.prev = tNode;
        mCount++;
        return;
    }

    //插入第一个节点处
    public void insertFirst(T t){
        this.insert(0,t);
    }
    //追加到链表末尾
    public void appendLast(T t){
        DNode<T> node = new DNode<T>(mHead,mHead.next,t);
        mHead.prev.next=node;
        mHead.next=node;
        mCount++;
        return;
    }

    //删除index处的节点
    public void del(int index){
        DNode inode = getNode(index);
        inode.prev.next = inode.prev;
        inode.next.prev = inode.next;
        inode = null;
        mCount--;
    }
    //删除第一个节点
    public void delFirst(){
        del(0);
    }
    //删除最后一个节点
    public void delLast(){
        del(mCount-1);
    }
}

/**
 * 双向链表测试
 */
class DoubleLinkTest{

    //int
    private static void intTest(){
        int[] iarr = {10, 20, 30, 40};
        System.out.println("\n----int_test----");
        DoubleLink<Integer> doubleLink = new DoubleLink<Integer>();
        doubleLink.insert(0,20);
        doubleLink.appendLast(10);
        doubleLink.insertFirst(5);

        System.out.printf("isEmpty()=%b\n", doubleLink.isEmpty());
        System.out.printf("size()=%d\n", doubleLink.size());

        for (int i = 0;i<doubleLink.size();i++){
            System.out.println("doubleLink("+i+")="+ doubleLink.get(i));
        }

    }

    //String
    private static void stringTest(){
        String[] strs = {"one", "two", "three", "four"};
        System.out.println("\n----string_test----");
        DoubleLink<String> doubleLink = new DoubleLink<String>();
        doubleLink.insert(0,strs[1]);
        doubleLink.appendLast(strs[3]);
        doubleLink.insertFirst(strs[2]);

        System.out.printf("isEmpty()=%b\n", doubleLink.isEmpty());
        System.out.printf("size()=%d\n", doubleLink.size());

        for (int i = 0;i<doubleLink.size();i++){
            System.out.println("doubleLink("+i+")="+ doubleLink.get(i));
        }

    }

    //对象
    private static void objTest(){
        Foo[] foos = new Foo[]{new Foo(1,"tom"),
                new Foo(2,"jerry"),
                new Foo(3,"mary"),
                new Foo(4,"fuck")
        };

        System.out.println("\n----obj_test----");
        DoubleLink<Foo> doubleLink = new DoubleLink<Foo>();
        doubleLink.insert(0,foos[2]);
        doubleLink.appendLast(foos[3]);
        doubleLink.insertFirst(foos[2]);

        System.out.printf("isEmpty()=%b\n", doubleLink.isEmpty());
        System.out.printf("size()=%d\n", doubleLink.size());

        for (int i = 0;i<doubleLink.size();i++){
            System.out.println("doubleLink("+i+")="+ doubleLink.get(i).toString());
        }

    }

    //内部类
    private static class Foo{
        private int id;
        private String name;

        public Foo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        intTest();
        stringTest();
        objTest();
    }
}
