package com.myApp.algorithmproject.heap;

import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;
import com.myApp.algorithmproject.tree.printer.BinaryTrees;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020/7/30 9:14 AM
 * description:
 */
public class main {


    public static void main(String[] args){
        test();
    }

    static void test1(){
        Integer[] data = { 57, 76, 68, 89, 45, 27, 55, 50, 28, 18, 93, 23, 43, 84, 77};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);
    }

    static void test2(){
        Integer[] data = { 57, 76, 68, 89, 45, 27, 55, 50, 28, 18, 93, 23, 43, 84, 77};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        BinaryTrees.println(heap);
    }

    static void test3(){
        //新建一个小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int k = 3;
        Integer[] data = { 14, 2, 61, 32, 30, 95, 29, 25, 89, 4, 47, 58, 74, 64, 75, 87, 31, 83, 50, 92, 23, 35, 60, 6, 33};
        for (int i = 0; i < data.length; i++){
            if (heap.size < k){ //前k个小顶堆
                heap.add(data[i]);  //时间复杂度logk
            }else {
                if (data[i] > heap.get()){ //如果是第k+1个元素，并且大于堆顶元素
                    heap.replace(data[i]);  //时间复杂度logk
                }
            }
        }
        BinaryTrees.println(heap);
    }


    static void test(){
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
        binaryHeap.add(68);
        binaryHeap.add(72);
        binaryHeap.add(43);
        binaryHeap.add(50);
        binaryHeap.add(38);
        binaryHeap.add(10);
        binaryHeap.add(90);
        binaryHeap.add(65);
        BinaryTrees.println(binaryHeap);
        binaryHeap.replace(66);
        BinaryTrees.println(binaryHeap);
    }
}
