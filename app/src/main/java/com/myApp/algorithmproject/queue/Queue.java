package com.myApp.algorithmproject.queue;

import com.myApp.algorithmproject.arrrylist.List;
import com.myApp.algorithmproject.linklist.DoubleLinkedList;

/**
 * author: zhouyh
 * created on: 2020-05-19 19:52
 * description:优先使用链表   队列从头尾操作
 */
public class Queue<E>{



    private List<E> list = new DoubleLinkedList<>();

    public int size(){
        return list.size();
    }


    public boolean isEmpty(){
        return list.isEmpty();
    }


    /**
     * 入队
     * @param element
     */
    public void enQueue(E element){
        list.add(element);
    }


    /**
     * 出队
     * @return
     */
    public E deQueue(){
       return list.remove(0);
    }

    public E front(){

        return list.get(0);
    }

    public void clear(){
        list.clear();
    }
}
