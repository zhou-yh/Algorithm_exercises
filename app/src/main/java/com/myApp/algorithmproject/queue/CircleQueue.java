package com.myApp.algorithmproject.queue;

/**
 * author: zhouyh
 * created on: 2020-05-20 09:57
 * description:循环队列
 * 底层用动态数组实现 各项接口可以优化到O(1)复杂度
 */
public class CircleQueue<E> {


    //队列头下标
    private int front;

    private int size;

    private E[] elements;


    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size(){
        return size;
    }


    public boolean isEmpty(){
        return size == 0;
    }


    /**
     * 入队
     * @param element
     */
    public void enQueue(E element){
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }


    /**
     * 出队
     * @return
     */
    public E deQueue(){
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;

    }

    public E front(){
        return elements[0];
    }


    /**
     * 保证要有capacity的容量
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity){
        int oldCapacity = elements.length;
        if (oldCapacity >= minCapacity) return;

        //新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++){
            newElements[i] = elements[index(i)];
        }

        elements = newElements;
        front = 0;
        System.out.println(oldCapacity + " 扩容为 " + newCapacity);
    }


    /**
     * 索引映射
     * @param index
     * @return
     */
    private int index(int index){
        return (front + index) % elements.length;
    }
    /**
     * 清除
     */
    public void clear(){
        size = 0;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("capacity = ").append(elements.length)
                .append("size = ").append(size)
                .append(" front =").append(front)
                .append(",[");
        for (int i = 0; i < elements.length; i++){
            if (i != 0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


}
