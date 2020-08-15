package com.myApp.algorithmproject.set;

/**
 * author: zhouyh
 * created on: 2020-06-08 16:44
 * description:
 * 实现方式：
 * 1. 用动态数组方式
 * 2. 用链表方式
 * 3. 用红黑树方式
 *
 */
public interface Set<E> {


    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E element);
    void add(E element);
    void remove(E element);
    void traversal(Visitor<E> visitor);


    static abstract class Visitor<E>{


        //停止遍历标志
        boolean stop;

        //提供外界访问接口
        public abstract boolean visitor(E element);
    }
}
