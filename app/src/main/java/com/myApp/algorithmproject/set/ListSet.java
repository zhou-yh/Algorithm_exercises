package com.myApp.algorithmproject.set;

import com.myApp.algorithmproject.arrrylist.List;
import com.myApp.algorithmproject.linklist.DoubleLinkedList;

/**
 * author: zhouyh
 * created on: 2020-06-08 16:50
 * description: 链表实现集合
 */
public class ListSet<E> implements Set<E> {


    private List<E> list = new DoubleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }


    //复杂度O(n)
    @Override
    public boolean contains(E element) {
        return list.contains(element) ;
    }


    //复杂度O(n)
    @Override
    public void add(E element) {
        if (element == null){
            throw new NullPointerException("element is null");
        }
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND){
            list.set(index,element);
        }else {
            list.add(element);
        }


    }


    //复杂度O(n)
    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND){
            list.remove(index);
        }

    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null ) return;

        int size = list.size();
        for (int i  = 0 ; i < size ; i++){
            if (visitor.visitor(list.get(i)))return;

        }

    }
}
