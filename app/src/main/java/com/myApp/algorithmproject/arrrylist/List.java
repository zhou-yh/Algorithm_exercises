package com.myApp.algorithmproject.arrrylist;

public interface List<E> {


    int ELEMENT_NOT_FOUND = -1;

    /**
     * 添加元素
     */
    void add(E element);

    int size();

    boolean isEmpty();

    boolean contains(E element);

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(E element);

    /**
     * 清除所有元素
     */
    void clear();


}
