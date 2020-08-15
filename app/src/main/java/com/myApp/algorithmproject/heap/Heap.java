package com.myApp.algorithmproject.heap;

/**
 * author: zhouyh
 * created on: 2020/7/23 8:03 PM
 * description: 二叉堆 大顶堆  小顶堆
 */
public interface Heap<E> {

    int size();//元素个数
    boolean isEmpty();//是否为空
    void add(E element); //添加元素
    void clear();//清空
    E get();//获取堆顶元素
    E remove();//删除堆顶元素
    E replace(E element);//删除堆顶元素的同时插入一个新元素

}
