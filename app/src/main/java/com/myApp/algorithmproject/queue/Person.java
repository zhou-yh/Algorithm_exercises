package com.myApp.algorithmproject.queue;

/**
 * author: zhouyh
 * created on: 2020/7/30 4:56 PM
 * description:
 */
public class Person implements Comparable<Person>{

    private String name;
    private int boneBreak;

    public Person(String name, int boneBreak) {
        this.name = name;
        this.boneBreak = boneBreak;
    }

    @Override
    public int compareTo(Person o) {
        return this.boneBreak - o.boneBreak;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", boneBreak=" + boneBreak +
                '}';
    }
}
