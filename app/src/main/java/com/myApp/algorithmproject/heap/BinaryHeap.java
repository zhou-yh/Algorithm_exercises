package com.myApp.algorithmproject.heap;

import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020/7/23 8:09 PM
 * description: 二叉堆（最大堆）
 * 鉴于完全二叉树的一些特性，二叉堆的底层一般用数组实现即可
 * 索引i的规律
 * 如果 i = 0，它是根节点
 * 如果 i > 0, 它的父节点的索引为floor((i-1)/2)
 * 如果2i+1≤n–1，它的左子节点的索引为2i+1
 * 如果2i+1>n–1，它无左子节点
 * 如果2i+2≤n–1，它的右子节点的索引为2i+2
 * 如果2i+2>n–1，它无右子节点
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;


    public BinaryHeap(E[] elements,Comparator<E> comparator){
        super(comparator);
        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            size = elements.length;
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++){
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }

    public BinaryHeap(Comparator<E> comparator) {
       this(null,comparator);
    }


    public BinaryHeap() {
        this(null,null);
    }





    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }


    /**
     * 批量建堆
     */
    private void heapify(){

        //自上而下的上滤 效率较低
        for (int i = 1; i < size;i++){
            siftUp(i);
        }

        //自下而上的下滤
        for (int i = (size >> 1) - 1;i >= 0;i--){
            siftDown(i);
        }
    }


    /**
     * 将让index元素进行上滤
     * @param index
     */
    private void siftUp(int index) {
//        E e = elements[index];
//        //要进行循环首先条件要有父节点  判断条件 index>0
//        while(index > 0){
//            //和父节点比较 取出父节点
//            int pIndex = (index - 1) >> 1;
//            E p = elements[pIndex];
//
//            //小于等于父节点 直接返回
//            if (compare(e,p) <= 0) return;
//
//            //交换index和pindex的内容
//            E tmp = elements[index];
//            elements[index] = elements[pIndex];
//            elements[pIndex] = tmp;
//            //重新赋值索引
//            index = pIndex;
//        }
        E e = elements[index];
        while(index > 0){
            //和父节点比较 取出父节点
            int pIndex = (index - 1) >> 1;
            E p = elements[pIndex];

            //小于等于父节点 直接返回
            if (compare(e,p) <= 0) break;

            elements[index] = p;
            //重新赋值索引
            index = pIndex;
        }
        elements[index] = e;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }


    /**
     * 1.用最后一个节点覆盖根节点
     * 2.删除最后一个节点
     * 3.循环执行以下操作（图中的43简称为node）
     * 如果node<最大的子节点
     * 与最大的子节点交换位置
     * 如果node≥最大的子节点，或者node没有子节点✓退出循环
     * @return 删除堆顶元素
     */
    @Override
    public E remove() {
        emptyCheck();
        E first = elements[0];
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);

        return first;
    }

    /**
     * 将index元素下滤
     * @param index
     */
    private void siftDown(int index) {
        E e = elements[index];
        //循环条件要小于第一个叶子节点的索引
        //第一个叶子节点的索引 == 非叶子节点的数量
        int half = size >> 1;
        while (index < half){  //必须保证index位置是非叶子节点
            //默认是左子节点跟父节点比较
            int childrenIndex = (index << 1) + 1;
            E child = elements[childrenIndex];

            int rightIndex = childrenIndex + 1;
            if (rightIndex < size && compare(elements[rightIndex],child)>0){
                child = elements[childrenIndex = rightIndex];
            }

            if (compare(e,child) >=0 ) break;
            //将子节点存放到index位置
            elements[index] = child;
            //重新赋值index
            index = childrenIndex;
        }
        elements[index] = e;
    }

    @Override
    public E replace(E element) {
        E top = null;
        if (size == 0){
            elements[size++] = element;
        }else {
            top = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return top;
    }

    private void emptyCheck(){
        if (size == 0){
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    /**
     * 保证要有capacity的容量
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity){
        int oldCapacity = elements.length;
        if (oldCapacity > minCapacity) return;

        //新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++){
            newElements[i] = elements[i];
        }

        elements = newElements;
        System.out.println(oldCapacity + " 扩容为 " + newCapacity);
    }

    private void elementNotNullCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) +1;
        return index >= size ?  null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 2;
        return index >= size ?  null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[index];
    }
}
