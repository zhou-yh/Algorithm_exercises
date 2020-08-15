package com.myApp.algorithmproject.set;

import com.myApp.algorithmproject.tree.BinaryTree;
import com.myApp.algorithmproject.tree.RBTree;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020-06-08 17:13
 * description: 红黑树实现集合  局限性必须具备可比较性
 * 复杂度  log(n)
 */
public class TreeSet<E> implements Set<E> {

    private RBTree<E> tree;

    public TreeSet(){
        this(null);
    }

    public TreeSet(Comparator<E> comparator){

        tree = new RBTree<>(comparator);

    }
    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(final Visitor<E> visitor) {

        tree.inOrder(new BinaryTree.Visitor<E>() {
            @Override
            protected boolean visit(E element) {
                return visitor.visitor(element);
            }
        });
    }
}
