package com.myApp.algorithmproject.queue;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * author: zhouyh
 * created on: 2020/7/30 4:57 PM
 * description:
 */
public class PriorityQueueDemo {

    public static void main(String[] args){
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("jack",2));
        queue.enQueue(new Person("jake",15));
        queue.enQueue(new Person("jim",5));
        queue.enQueue(new Person("james",10));

        while (!queue.isEmpty()){
            System.out.println(queue.deQueue());
        }


    }
}
