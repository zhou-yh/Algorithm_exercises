package com.myApp.algorithmproject.map;

/**
 * author: zhouyh
 * created on: 2020-06-08 17:51
 * description:
 */
public class mapDemo {


    public static void main(String[] args){

        test1();

    }


    static void test1(){
        TreeMap<String,Integer> treeMap = new TreeMap<>();

        treeMap.put("class",2);
        treeMap.put("public",5);
        treeMap.put("good",13);
        treeMap.put("public",8);
        treeMap.put("every",8);
        treeMap.put("hello",8);
        treeMap.put("3",8);

        treeMap.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });




    }
}
