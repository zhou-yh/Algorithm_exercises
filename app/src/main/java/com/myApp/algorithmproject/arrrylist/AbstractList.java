package com.myApp.algorithmproject.arrrylist;

public abstract class AbstractList<E> implements List<E> {



    public int size;
    public E[] elements;


    public void rangeCheck(int index){
        if (index<0 || index >= size){
            outOfBound(index);
        }
    }

    public void rangeCheckForAdd(int index){
        if (index < 0 || index > size){
            outOfBound(index);
        }
    }

    public void outOfBound(int index){
        throw new IndexOutOfBoundsException("Index:" + index + "size:" + size);
    }
}
