package com.myApp.algorithmproject.queue;

import com.myApp.algorithmproject.arrrylist.List;
import com.myApp.algorithmproject.linklist.DoubleLinkedList;

/**
 * author: zhouyh
 * created on: 2020-05-20 08:37
 * description: 双端队列  两头都能操作元素
 */
public class Deque<E> {

    private List<E> list = new DoubleLinkedList<>();

    public int size(){
        return list.size();
    }


    public boolean isEmpty(){
        return list.isEmpty();
    }


    /**
     * 从队尾入队
     * @param element
     */
    public void enQueueRear(E element){
        list.add(list.size(),element);
    }

    /**
     * 从队尾出队
     */
    public E deQueueRear(){
        return list.remove(list.size()-1);
    }


    /**
     * 从队头入队
     * @param element
     */
    public void enQueueFront(E element){
        list.add(0,element);
    }

    /**
     * 从队头出队
     */
    public E deQueueFront(){
        return list.remove(0);
    }




    /**
     * 获取队头元素
     * @return
     */
    public E front(){
        return list.get(0);
    }

    /**
     *获取队尾元素
     */
    public E rear(){
        return list.get(list.size() - 1);
    }
}

