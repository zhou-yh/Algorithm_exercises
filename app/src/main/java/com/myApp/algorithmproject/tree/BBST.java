package com.myApp.algorithmproject.tree;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020-06-08 09:51
 * description:平衡二叉搜索树
 */
public class BBST<E> extends BST<E> {


    public BBST() {
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }


    /**
     * 左旋转
     * @param grand
     * 还需要维护的内容
     * T1、p、g的parent属性
     * 先后更新g、p的高度
     */
    protected void rotateLeft(Node<E> grand){
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;


        afterRotate(grand,parent,child);


    }


    /**
     * 右旋转
     * @param grand
     */
    protected void rotateRight(Node<E> grand){
        Node<E> parent = grand.left;
        Node<E> child = parent.right;

        grand.left = parent.right;
        parent.right = grand;


        afterRotate(grand,parent,child);
    }

    /**
     * 旋转之后的处理
     * @param grand
     * @param parent
     * @param child
     */
    protected void afterRotate(Node<E> grand,Node<E> parent,Node<E> child){
        //让parent成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {
            root = parent;
        }

        //更新child的parent
        if (child!=null){
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;

    }
}
