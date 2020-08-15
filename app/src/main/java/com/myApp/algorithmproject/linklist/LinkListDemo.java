package com.myApp.algorithmproject.linklist;

import com.myApp.algorithmproject.lianbiao.LinkNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * author: zhouyh
 * created on: 2020-06-11 10:45
 * description: 链表demo
 */
public class LinkListDemo {


    public static void main(String[] args) {


//        SingleCircleLinkedList<Integer> singleCircleLinkedList = new SingleCircleLinkedList<>();
//        singleCircleLinkedList.add(1);
//        singleCircleLinkedList.add(3);
//        singleCircleLinkedList.add(5);
//        System.out.println(singleCircleLinkedList.toString());

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(8);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(6);

        ListNode l4 = new ListNode(3);
        l4.next = new ListNode(7);

        ListNode[] listNodes = new ListNode[]{l1,l2,l3,l4};

        ListNode newNode  = mergeKLists(listNodes);



        while (newNode.next!=null){
            System.out.println(newNode.val);
            newNode = newNode.next;
        }

        System.out.println(newNode.val);


    }


    public static ListNode mergeKLists(ListNode[] lists) {
//        if (lists.length == 0 || lists == null) return null;
//        return merge(lists, 0, lists.length - 1);

        if (lists.length == 0) {
            return null;
        }

        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        for (ListNode list : lists) {
            if (list == null) {
                continue;
            }
            pq.add(list);
        }

        while (!pq.isEmpty()) {
            ListNode nextNode = pq.poll();
            curr.next = nextNode;
            curr = curr.next;
            if (nextNode.next != null) {
                pq.add(nextNode.next);
            }
        }
        return dummyHead.next;
    }

    public static ListNode merge(ListNode[] lists, int left, int right) {
        if (left == right) return lists[left];
        int mid = left + (right - left) / 2;
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);
        return merger2Lists(l1, l2);
    }

    public static ListNode merger2Lists(ListNode l1, ListNode l2) {


//         ListNode dummyHead = new ListNode(-1);
//         ListNode cur = dummyHead;
//         while (l1 != null && l2 != null) {
//             if (l1.val<l2.val){
//                 cur.next = l1;
//                 cur = cur.next;
//                 l1 = l1.next;
//             }else {
//                 cur.next = l2;
//                 cur = cur.next;
//                 l2 = l2.next;
//             }
//             System.out.println(cur.val);
//         }
//         if (l1!=null){
//             cur.next = l1;
//         }else {
//             cur.next = l2;
//         }
//         return dummyHead.next;

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val <= l2.val) {
            l1.next = merger2Lists(l1.next, l2);
            return l1;
        } else {
            l2.next = merger2Lists(l1, l2.next);
            return l2;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


}
