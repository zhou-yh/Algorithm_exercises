package com.myApp.algorithmproject.lianbiao;

/**
 * author: zhouyh
 * created on: 2020-04-27 08:46
 * description:利用快慢指针 判断是否有环    慢指针 1步   快指针 2步
 */
public class _141_环形链表 {

    public boolean hasCircle(LinkNode head){
        if (head == null || head.next == null) return false;

        LinkNode slow = head;
        LinkNode fast = head.next;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)return true;
        }
        return false;
    }
}
