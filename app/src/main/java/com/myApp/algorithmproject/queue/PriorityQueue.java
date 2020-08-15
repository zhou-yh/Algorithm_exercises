package com.myApp.algorithmproject.queue;

import com.myApp.algorithmproject.heap.BinaryHeap;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020/7/30 4:53 PM
 * description: 优先级队列 底层使用二叉堆实现
 */
public class PriorityQueue<E> {

    private BinaryHeap<E> heap;


    public PriorityQueue(Comparator<E> comparator) {
        this.heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    public int size(){
        return heap.size();
    }


    public boolean isEmpty(){
        return heap.isEmpty();
    }


    /**
     * 入队
     * @param element
     */
    public void enQueue(E element){
        heap.add(element);
    }


    /**
     * 出队
     * @return
     */
    public E deQueue(){
        return heap.remove();
    }

    public E front(){

        return heap.get();
    }

    public void clear(){
        heap.clear();
    }
}
