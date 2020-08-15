package com.myApp.algorithmproject.linklist;

import com.myApp.algorithmproject.arrrylist.AbstractList;

/**
 * 双向链表
 * @param <E>
 */
public class DoubleLinkedList<E> extends AbstractList<E> {



    transient Node<E> first;
    transient Node<E> last;



    private static class Node<E>{
        E element;
        Node<E> pre;
        Node<E> next;

        public Node(Node<E> pre,E element,Node<E> next) {
            this.pre = pre;
            this.element = element;
            this.next = next;
        }
    }


    @Override
    public void add(E element) {
        this.add(size,element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public boolean contains(E element) {
        return false;
    }


    @Override
    public E get(int index) {
        return node(index).element;
    }

    /**
     * 获取index对应节点上的对象
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        rangeCheck(index);

        if (index < (size>>1)){
            Node<E> node = first ;
            for (int i = 0; i < index; i++){
                node = node.next;
            }
            return node;
        }else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--){
                node = node.pre;
            }
            return node;
        }

    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;

        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) //往最后面添加元素
        {
            Node<E> oldLast = last;
            /**
             * 新添加则last
             */
            last = new Node<>(last,element,null);
            if (oldLast == null){
                first = last;  //如果刚开始为空，则first == last
            }else {
                oldLast.next = last;
            }
        }else {
            Node<E> next = node(index); //0
            Node<E> prev = next.pre;   //null
            Node<E> node = new Node<>(prev,element,next);
            next.pre = node;
            if (prev == null){ //index =0
                first = node;
            }else {
                prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = node(index);
        Node<E> prev = node.pre;
        Node<E> next = node.next;

        if (prev == null){ //index = 0
            first = next;
        }else{
            prev.next = next;
        }

        if (next == null){  //index = size - 1
            last = prev;
        }else {
            next.pre = prev;
        }
        size--;

        return node.element;
    }

    @Override
    public int indexOf(E element) {
        if (element == null){
            Node<E> node = first;
            for (int i = 0; i < size; i++){
                if (node.element == null)return i;
                node = node.next;
            }
        }else {
            Node<E> node = first;
            for (int i = 0; i < size; i++){
                if (node.element.equals(element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=").append(size).append(",[");
        Node<E> node = first;
        for (int i = 0; i < size; i++){
            if (i != 0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(node.element);
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
