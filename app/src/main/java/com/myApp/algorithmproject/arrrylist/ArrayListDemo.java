package com.myApp.algorithmproject.arrrylist;

/**
 * author: zhouyh
 * created on: 2020/7/21 2:25 PM
 * description:
 */
public class ArrayListDemo  {

    public static void main(String[] args){
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(1,3);
        for (int i = 0 ; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}
