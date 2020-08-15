package com.myApp.algorithmproject.stack;

import com.myApp.algorithmproject.arrrylist.ArrayList;
import com.myApp.algorithmproject.arrrylist.List;

/**
 * author: zhouyh
 * created on: 2020-05-19 18:13
 * description:栈
 */
public class Stack<E> {


    private List<E> list = new ArrayList<>();



    public boolean isEmpty(){
        return list.isEmpty();
    }


    public void push(E element){
        list.add(element);
    }

    public E pop(){
        return list.remove(list.size() - 1);
    }


    /**
     * 栈顶元素
     * @return
     */
    public E top(){
        return list.get(list.size()-1);
    }
}
