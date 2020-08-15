package com.myApp.algorithmproject.lianbiao;

/**
 * author: zhouyh
 * created on: 2020-04-25 23:03
 * description:
 */
public class LinkNode {


    public int val;
    public LinkNode next;
    public LinkNode() {}
    public LinkNode(int val) { this.val = val; }
    public LinkNode(int val, LinkNode next) { this.val = val; this.next = next; }


    public LinkNode mergeLinkNode(LinkNode l1,LinkNode l2){
        if (l1==null){
            return l2;
        }

        if (l2 == null){
            return l1;
        }

        if (l1.val <= l2.val){
            l1.next = mergeLinkNode(l1.next,l2);
            return l1;
        }else {
            l2.next = mergeLinkNode(l1,l2.next);
            return l2;
        }
    }
}
