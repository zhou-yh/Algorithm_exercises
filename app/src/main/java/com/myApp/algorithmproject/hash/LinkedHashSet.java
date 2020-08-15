package com.myApp.algorithmproject.hash;

import com.myApp.algorithmproject.map.Map;
import com.myApp.algorithmproject.set.Set;

/**
 * author: zhouyh
 * created on: 2020/7/19 10:14 PM
 * description:哈希表实现的set  效率比treeSet高
 */
public class LinkedHashSet<E> implements Set<E> {


    private LinkedHashMap<E,Object> map = new LinkedHashMap<>();


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
        map.put(element,null);
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void traversal(final Visitor<E> visitor) {

        map.traversal(new Map.Visitor<E, Object>() {

            @Override
            public boolean visit(E key, Object value) {
                return visitor.visitor(key);
            }
        });
    }
}
