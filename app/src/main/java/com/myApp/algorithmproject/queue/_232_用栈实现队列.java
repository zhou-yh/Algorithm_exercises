package com.myApp.algorithmproject.queue;

import java.util.Stack;

/**
 * author: zhouyh
 * created on: 2020-05-19 20:05
 * description:
 */
public class _232_用栈实现队列 {


    private Stack<Integer> inStack;
    private Stack<Integer> outStack;

    public _232_用栈实现队列() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }


    /**
     * 入队
     *
     * @param x
     */
    public void push(int x) {
        inStack.push(x);
    }


    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        checkOutStack();
        return outStack.pop();
    }


    public int peek() {
        checkOutStack();
        return outStack.peek();
    }


    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    public void checkOutStack() {
        while (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}
