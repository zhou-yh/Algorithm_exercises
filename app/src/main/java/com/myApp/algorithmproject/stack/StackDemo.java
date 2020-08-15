package com.myApp.algorithmproject.stack;

/**
 * author: zhouyh
 * created on: 2020-05-19 17:51
 * description:
 */
public class StackDemo {

    public static void main(String[] args){

        Stack<Integer> stacks = new Stack<>();
        stacks.push(11);
        stacks.push(22);
        stacks.push(33);
        while (!stacks.isEmpty()){
            System.out.println(stacks.pop());
        }

    }
}
