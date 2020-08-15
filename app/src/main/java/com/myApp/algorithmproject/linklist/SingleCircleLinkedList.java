package com.myApp.algorithmproject.linklist;


import com.myApp.algorithmproject.arrrylist.AbstractList;

/**
 * 单向循环链表
 * @param <E>
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {


    transient Node<E> first;



    private static class Node<E>{
        E element;
        Node<E> next;

        public Node(E element,Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("element").append("_").append(next.element);
            return stringBuilder.toString();
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

        Node<E> node = first ;
        for (int i = 0; i < index; i++){
            node = node.next;
        }
        return node;
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
        if (index == 0) {
            first = new Node<>(element,first);
            //拿到最后一个节点
            Node<E> last = (size == 0) ? first : node(size - 1);
            last.next = first;
        }else{
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element,prev.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = first;
        if (index == 0){

            if (size == 1) {
                first = null;
            }else {
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }
        }else{
            Node<E> pre = node(index - 1);
            node = pre.next;
            pre.next = node.next;
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
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
//        first.toString();
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
