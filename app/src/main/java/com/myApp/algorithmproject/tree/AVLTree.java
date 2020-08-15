package com.myApp.algorithmproject.tree;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020-05-25 22:29
 * description:平衡二叉树
 */
public class AVLTree<E> extends BBST<E> {


    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    protected void afterAdd(Node<E> node) {

        while ((node = node.parent) != null){
            if (isBalance(node)){
                //更新高度
                updateHeight(node);
            }else {
                //恢复平衡
                reBalance(node);
                //整棵树恢复平衡
                break;
            }

        }
    }


    /**
     *
     * @param node
     */
    @Override
    protected void afterRemove(Node<E> node) {

        while ((node = node.parent) != null){
            if (isBalance(node)){
                //更新高度
                updateHeight(node);
            }else{
                //恢复平衡
                /**
                 * 删除复杂度可能O(1)
                 * 最差复杂度O(logn)
                 */
                reBalance(node);
            }
        }
    }


    /**
     * 恢复平衡
     * @param grand  高度最低的那个不平衡节点
     */
    private void reBalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        if (parent.isLeftChild()){   //L
            if (node.isLeftChild()){    //LL
                rotateRight(grand);
            }else {     //LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else { //R
            if (node.isLeftChild()){    //RL
                rotateRight(parent);
                rotateLeft(grand);
            }else {     //RR
                rotateLeft(grand);
            }
        }

    }


    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        //更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 更新节点高度
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }


    /**
     * 判断是否平衡
     * @param node
     * @return
     */
    public boolean isBalance(Node<E> node){
       return Math.abs(((AVLNode<E>)node).balanceFactor())<=1;
    }

    /**
     * 重写方法 创建平衡二叉树节点
     * @param element
     * @param parent
     * @return
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element,parent);
    }


    private static class AVLNode<E> extends Node<E>{
        //传进来的一定是叶子节点 默认高度为1
        int height = 1;

        public AVLNode(E element, Node<E> node) {
            super(element, node);
        }

        /**
         * 计算平衡因子 左子树 - 右子树  0和1 即达到平衡
         * @return
         */
        public int balanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新高度
         */
        public void updateHeight(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            height = 1 + Math.max(leftHeight,rightHeight);
        }


        /**
         * 高度更高的子节点
         * @return
         */
        public Node<E> tallerChild(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }


        @Override
        public String toString() {

            String parentStr = "null";
            if (parent !=null){
                parentStr = parent.element.toString();
            }

            return element + "_p(" + parentStr + ")"+ "_h(" + height+")";
        }
    }

}
