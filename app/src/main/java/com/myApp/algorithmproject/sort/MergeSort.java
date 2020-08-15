package com.myApp.algorithmproject.sort;

import java.util.Arrays;

/**
 * author: zhouyh
 * created on: 2020/8/5 11:23 AM
 * description:归并排序
 */
public class MergeSort {


    private static int[] array = new int[]{3,5,8,9,23,80,34,34,52,24,56};
    private static int[] leftArray = new int[array.length >> 1];


    public static void main(String[] args){

        for (int a : array){
            System.out.print( a + " ");
        }
        System.out.println(" ");
        sort(0,array.length);

        for (int a : array){
            System.out.print( a + " ");
        }


    }

    /**
     * 排序
     * @param begin
     * @param end
     */
    public static void sort(int begin, int end){
        //至少要2元素
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin,mid);
        sort(mid, end);

    }


    /**
     * 合并
     * @param begin
     * @param mid
     * @param end
     */
    public static void merge(int begin, int mid, int end){
        //左边数组
        int lb = 0;
        int le = mid - begin;
        //右边数组
        int rb = mid;
        int re = end;
        //array索引
        int ai = begin;



        //拷贝左边数组到leftArray
        for (int i = lb; i < le; i++){
            leftArray[i] = array[i];
        }
        while (lb < le){
            if (rb < re && cmp(array[lb],leftArray[lb]) < 0){
                array[ai++] = array[rb++]; //拷贝右边数组到array
            }else {
                array[ai++] = leftArray[lb++];//拷贝左边数组到array
            }
        }



    }

    public static int cmp(int a, int b){
        if (a - b < 0){
            return -1;
        }else if (a - b > 0){
            return 1;
        }else if (a - b == 0){
            return 0;
        }
      return 0;
    }
}
