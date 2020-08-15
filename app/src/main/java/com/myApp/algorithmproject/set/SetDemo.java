package com.myApp.algorithmproject.set;

/**
 * author: zhouyh
 * created on: 2020-06-08 16:42
 * description:集合demo
 */
public class SetDemo {



    public static void main(String[] args){

        test3();

    }

    public static void test1(){

        Set<Integer> set = new ListSet<>();

        set.add(10);
        set.add(11);
        set.add(12);
        set.add(13);
        set.add(14);
        set.add(10);

        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visitor(Integer element) {
                System.out.println("element ==" + element);
                return false;
            }
        });
    }


    public static void test2(){

        Set<Integer> set = new TreeSet<>();

        set.add(11);
        set.add(10);
        set.add(11);
        set.add(12);
        set.add(13);
        set.add(14);
        set.add(12);
        set.add(6);
        set.add(8);
        set.add(9);

        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visitor(Integer element) {
                System.out.println("element ==" + element);
                return false;
            }
        });
    }

    public static void test3(){
        Set<String> set = new TreeSet1<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("a");
        set.add("b");
        set.add("f");
        set.add("c");
        set.add("h");

        set.traversal(new Set.Visitor<String>() {
            @Override
            public boolean visitor(String element) {
                System.out.println("element ==" + element);
                return false;
            }
        });
    }

}
