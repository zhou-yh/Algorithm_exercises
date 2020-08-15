package com.myApp.algorithmproject.lianbiao;

/**
 * author: zhouyh
 * created on: 2020-04-25 23:05
 * description:
 */
public class _237_删除链表中的节点 {


    public void deleteNode(LinkNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
