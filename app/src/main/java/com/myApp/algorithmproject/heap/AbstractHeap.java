package com.myApp.algorithmproject.heap;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020/7/30 10:10 AM
 * description: 抽象二叉堆
 */
public  abstract class AbstractHeap<E> implements Heap<E>{

    public int size;
    public Comparator<E> comparator;


    public AbstractHeap() {
        this(null);
    }

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int compare(E e1, E e2){
        return comparator!=null ? comparator.compare(e1,e2) :
                ((Comparable)e1).compareTo(e2);
    }

}
