package com.myApp.algorithmproject.arrrylist;

public class ArrayList<E> extends AbstractList<E> {


    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList(int capacity) {
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
        elements = (E[]) new Object[capacity];
        size = 0;
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
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
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }


    /**
     * 保证要有capacity的容量
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity){
        int oldCapacity = elements.length;
        if (oldCapacity > minCapacity) return;

        //新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++){
            newElements[i] = elements[i];
        }

       elements = newElements;
        System.out.println(oldCapacity + " 扩容为 " + newCapacity);
    }

    /**
     * 缩容
     * 剩余空间占总容量一半时，就进行缩容
     */
    private void trim() {
        //30
        int oldCapacity = elements.length;
        //15
        int newCapacity = oldCapacity >> 1;
        if (size >= (newCapacity) || oldCapacity <= DEFAULT_CAPACITY)return;

        System.out.println(oldCapacity + "_" + newCapacity + "_" + size);

        /**
         * 剩余空间量还很多
         */
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < newElements.length; i++){
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 设置index的位置的元素
     * @param index
     * @param element
     * @return 原来的元素
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        /**
         * 最好：O(1)
         * 最坏:O(n)
         * 平均:O(n)
         */
        rangeCheckForAdd(index);

        ensureCapacity(size+1);
        for (int i = size; i > index;i--){
            elements[i] = elements[i-1];
        }
        elements[index] = element;
        size++;
    }//size是数据规模

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldValue = elements[index];
        for (int i = index + 1; i < size; i++){
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;

        /**
         *  缩容有效减少内存浪费
         */
        trim();
        return oldValue;
    }


    /**
     * 复杂度n+1
     * @param element
     * @return
     */
    @Override
    public int indexOf(E element) {

        if (element == null){
            for (int i = 0; i < size; i++){
                if (elements[i]== null) return i;
            }
        }else {
            for (int i = 0 ; i < size; i++){
                if (element.equals(elements[i]))return i;
            }
        }


        return ELEMENT_NOT_FOUND;
    }


    /**
     * 复杂度 2n
     * @param element
     * @return
     */
    public int indexOf2(E element){
        for (int i = 0 ; i < size; i++){
            if (valueEquals(element,elements[i]))return i;
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 做非空和空判断
     */
    private boolean valueEquals(Object o1,Object o2){
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public void clear() {
        size = 0;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=").append(size).append(",[");
        for (int i = 0; i < size; i++){
            if (i != 0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
