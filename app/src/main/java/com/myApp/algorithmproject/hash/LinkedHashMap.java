package com.myApp.algorithmproject.hash;

import java.util.Objects;

/**
 * author: zhouyh
 * created on: 2020-06-11 18:12
 * description:链表哈希表
 */
public class LinkedHashMap<K,V> extends HashMap<K,V> {

    LinkedNode<K,V> first;
    LinkedNode<K,V> last;


    @Override
    public void clear() {
        super.clear();

        /**
         * 销毁头尾节点
         */
        first = null;
        last = null;
    }


    @Override
    public boolean containValue(V value) {
        LinkedNode<K,V> node = first;
        while (node!=null){
            if (Objects.equals(value,node.value))return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;

        LinkedNode<K,V> node = first;
        while (node != null){
            if (visitor.visit(node.key,node.value))return;
            node = node.next;
        }
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
        LinkedNode<K,V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K,V> node2 = (LinkedNode<K, V>) removeNode;

        //不相等说明是度为2的节点
        if (willNode != removeNode) {
            //交换两者在链表中的位置
            //交换prev
            LinkedNode<K,V> tmp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = tmp;
            if (node1.prev == null){
                first = node1;
            }else {
                node1.prev.next = node1;
            }

            if (node2.prev == null){
                first = node2;
            }else {
                node2.prev.next = node2;
            }

            //交换next
            tmp = node1.next;
            node1.next = node2.next;
            node2.next = tmp;
            if (node1.next == null){
                last = node1;
            }else {
                node1.next.prev = node1;
            }

            if (node2.next == null){
                last = node2;
            }else {
                node2.next.prev = node2;
            }
        }

        LinkedNode<K,V> linkedNode = (LinkedNode<K, V>) removeNode;
        LinkedNode<K,V> prev = linkedNode.prev;
        LinkedNode<K,V> next = linkedNode.next;
        if (prev == null){ //删除的是头节点
            first = next;
        }else {
            prev.next = next;
        }

        if (next == null){ //删除的是尾节点
            last = prev;
        }else {
            next.prev = prev;
        }
    }



    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode node =  new LinkedNode(key, value, parent);

        if (first == null){
            first = last = node;
        }else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkedNode<K,V> extends Node<K,V>{

        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;

        public LinkedNode(K key, V value, Node<K, V> node) {
            super(key, value, node);
        }
    }
}
