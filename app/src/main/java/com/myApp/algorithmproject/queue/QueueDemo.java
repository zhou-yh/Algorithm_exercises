package com.myApp.algorithmproject.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * author: zhouyh
 * created on: 2020-05-19 17:30
 * description:
 */
public class QueueDemo {

    public static void main(String[] args){

//        Queue<Integer> queue = new Queue<>();
//
//        queue.enQueue(11);
//        queue.enQueue(22);
//        queue.enQueue(33);
//        queue.enQueue(44);
//
//        while (!queue.isEmpty()){
//            System.out.println(queue.deQueue());
//        }

//        Deque<Integer> queue = new Deque<>();
//
//        queue.enQueueFront(11);
//        queue.enQueueRear(22);
//        queue.enQueueFront(33);
//        queue.enQueueFront(44);
//
//        while (!queue.isEmpty()){
//            System.out.println(queue.deQueueRear());
//        }

//        testCircleQueue();

//        testCircleDeque();

        Deque<Object> queue = new ArrayDeque<>();
//        queue.offer(1);
//        queue.offer(2);
//        queue.offer(5);
//        queue.offer(9);

        System.out.println(queue.peekLast());
        System.out.println(queue.pollLast());


    }


    static void testCircleDeque(){
        CircleDeque<Integer> circleDeque = new CircleDeque<>();
        for (int i = 0 ; i < 5; i++){
            circleDeque.enQueue(i);
        }
        System.out.println(circleDeque);
        for (int i = 5 ; i < 10; i++){
            circleDeque.enQueueFront(i);
        }
        System.out.println(circleDeque);

        for (int i = 0; i < 2; i++){
            circleDeque.deQueueFront();
        }

        System.out.println(circleDeque);
        for (int i = 0; i < 3; i++){
            circleDeque.deQueueRear();
        }
        System.out.println(circleDeque);

    }




    static void testCircleQueue(){
        CircleQueue<Integer> circleQueue = new CircleQueue<Integer>();
        //0 1 2 3 4 5 6 7 8 9
        for (int i = 0 ; i < 10; i++){
            circleQueue.enQueue(i);
        }
        circleQueue.enQueue(null);

        //null null null null null 5 6 7 8 9
        for (int i = 0; i < 5; i++){
            circleQueue.deQueue();
        }

        for (int i = 15; i < 23; i++){
            circleQueue.enQueue(i);
        }

//        System.out.println(circleQueue);

        while (!circleQueue.isEmpty()){
            System.out.println(circleQueue.deQueue());
        }

    }
}
