package com.myApp.algorithmproject.map;

/**
 * author: zhouyh
 * created on: 2020-06-08 17:46
 * description:
 */
public interface Map<K,V> {


    int size();

    boolean isEmpty();

    void clear();

    V put(K key,V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    boolean containValue(V value);

    void traversal(Visitor<K,V> visitor);

    static abstract class Visitor<K,V>{

        boolean stop;

        public abstract boolean visit(K key, V value);
    }


    interface Entry<K,V>{


        K getKey();


        V getValue();

    }

}
