package com.myApp.algorithmproject.lianbiao;

/**
 * author: zhouyh
 * created on: 2020-04-25 23:03
 * description:
 */
public class _206_反转链表 {


    /**
     * 递归
     * @param head
     * @return
     */
    public LinkNode reverseNode(LinkNode head){
        if (head==null && head.next == null) return head;
        LinkNode newNode = reverseNode(head);
        newNode.next.next = head;
        newNode.next = null;
        return newNode;
    }

    /**
     * 非递归迭代
     */
    public LinkNode reverseNode1(LinkNode head){
        if (head == null && head.next == null) return head;
        LinkNode newHead = null;
        while (head.next!=null){
            LinkNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }

        return newHead;
    }




}
