package com.myApp.algorithmproject.tree;

import com.myApp.algorithmproject.stack.Stack;
import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zhouyh
 * created on: 2020-05-25 00:05
 * description: 二叉树
 */
public class BinaryTree<E> implements BinaryTreeInfo{



    protected int size;
    //根节点
    protected Node<E> root;


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public void clear() {
        root = null;
        size = 0;
    }



    /**
     * 前序遍历 递归方式
     *访问顺序   根节点、左子树、右子树
     */
    public void preOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrder(root, visitor);
    }

    private void preOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        visitor.stop = visitor.visit(node.element);
        preOrder(node.left, visitor);
        preOrder(node.right, visitor);

    }

    /**
     * 前序遍历 非递归方式 利用栈（stack）实现
     * @param visitor
     */
    public void preOrder1(Visitor<E> visitor){
        if (visitor == null || root == null) return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        while (true){
            if (node != null){
                //访问node节点
                if(visitor.visit(node.element))return;
                //将右节点入栈
                if (node.right != null){
                    stack.push(node.right);
                }
                //向左走
                node = node.left;
            }else if (stack.isEmpty()){
                return;
            }else {
                //到这里说明左子节点访问完,开始访问右节点
                node = stack.pop();
            }
        }
    }

    /**
     * 前序遍历 非递归方式 利用栈（stack）实现
     * @param visitor
     */
    public void preOrder2(Visitor<E> visitor){
        if (visitor == null || root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node<E> node = stack.pop();
            //访问node节点
            if(visitor.visit(node.element))return;
            if (node.right!=null){
                stack.push(node.right);
            }
            if (node.left!=null){
                stack.push(node.left);
            }
        }

    }

    /**
     * 中序遍历 递归实现
     * 顺序 左子树 根节点  右子树  升序或降序
     */
    public void inOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrder(root, visitor);
    }

    private void inOrder(Node<E> node,Visitor<E> visitor) {

        //visitor.stop 终止递归
        if (node == null || visitor.stop) return;


        inOrder(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inOrder(node.right, visitor);
    }

    /**
     * 中续遍历   非递归方式 用栈实现
     * @param visitor
     */
    public void inOrder1(Visitor<E> visitor){
        if (visitor == null || root == null)return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        while (true){
            if (node!=null){
                stack.push(node);
                node = node.left;
            }else if (stack.isEmpty()){
                return;
            }else {
                node = stack.pop();
                //访问node节点
                if (visitor.visit(node.element))return;
                //将右节点赋值给node进行遍历
                node = node.right;
            }
        }
    }

    /**
     * 后序遍历
     * 顺序  左子树   右子树   根节点
     */
    public void postOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        postOrder(root, visitor);
    }

    private void postOrder(Node<E> node, Visitor<E> visitor) {
        //visitor.stop 终止递归
        if (node == null || visitor.stop) return;

        postOrder(node.left, visitor);
        postOrder(node.right, visitor);

        /**
         * 打印终止
         */
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);

    }

    /**
     * 后序遍历  非递归 用栈实现
     * @param visitor
     */
    public void postOrder1(Visitor<E> visitor){
        if (visitor == null || root == null)return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> prev = null;
        //先将根节点入栈
        stack.push(root);
        while (!stack.isEmpty()){
            //先取出栈顶元素
            Node<E> top = stack.top();
            if (top.isLeaf() || (prev != null && prev.parent == top)){
                prev = stack.pop();
                if (visitor.visit(prev.element))return;
            }else {
                //将右节点入栈
                if (top.right != null){
                    stack.push(top.right);
                }
                //再将左节点入栈
                if (top.left != null){
                    stack.push(top.left);
                }
            }
        }
    }


    /**
     * 层序遍历  Level Order Traversal
     * 层层遍历
     * 实现思路：使用队列
     * 1.将根节点入队
     * 2.循环执行一下操作，直到队列为空
     * 将队列头节点A出队，进行访问
     * 将A的左子节点入队
     * 将A的右子节点入队
     *
     * @param visitor
     */
    public void levelOrder(BinarySearchTree.Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }



    /**
     * 判断是否是完全二叉树  度为0或1  度左子树减右子树
     * 如果树为空  返回false
     * 如果不为空，用层序遍历二叉树(用队列）
     * 如果node.left!=null && node.right!=null,将node.left、node.right按顺序入队
     * 如果node.left==null && node.right!=null,return false
     * 如果node.left!=null && node.right==null,或node.left==null && node.right==null
     * 那么后面遍历的节点都是叶子节点，才是完全二叉树
     * 否则返回false
     */
//    public boolean isCompleteTree(){
//        if (root == null) return false;
//
//        boolean leaf = false;
//        Queue<Node<E>> queue = new LinkedList<>();
//        queue.offer(root);
//        while (!queue.isEmpty()){
//            Node<E> node = queue.poll();
//            if (leaf && !node.isLeaf()){
//                return false;
//            }
//
//            if (node.left != null && node.right != null){
//                queue.offer(node.left);
//                queue.offer(node.right);
//            }else if (node.left == null && node.right != null){
//                return false;
//            }else { //后面遍历的都是叶子节点
//                leaf = true;
//                if (node.left!=null){
//                    queue.offer(node.left);
//                }
//            }
//        }
//        return true;
//    }


    /**
     * 减少重复判断
     *
     * @return
     */
    public boolean isCompleteTree2() {
        if (root == null) return false;


        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (leaf && !node.isLeaf()) return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //node.left == null node.right == null
                //node.left != null node.right == null
                leaf = true;
            }

        }
        return true;

    }

    /**
     * 非递归
     * 二叉树高度
     */

    public int height2() {
        if (root == null) return 0;
        int height = 0;
        //存储每一层的元素数量
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            levelSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }

            /**
             * 访问完一层，下一层的访问个数就是队列的长度
             */
            if (levelSize == 0) {//意味着要访问下一层
                levelSize = queue.size();
                height++;
            }


        }

        return height;
    }


    /**
     * 递归方式
     * 二叉树高度 即根节点高度
     *
     * @return
     */
    public int height() {
        return height(root);
    }

    /**
     * 获取当前节点高度
     *
     * @param node
     * @return
     */
    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }


    /**
     * 外部访问元素接口
     *
     * @param <E>
     */
    public static abstract class Visitor<E> {


        boolean stop;

        /**
         * 增强遍历接口  可以终止遍历
         * return ture 返回  false 继续遍历
         */
        protected abstract boolean visit(E e);


    }


    /**
     * 前驱节点  中序节点前的最后一个节点  左子树最大的一个
     * 如果是二叉搜索树
     * node.left != null
     * predecessor = node.left.right.right....
     * 终止条件 right为空
     * <p>
     * node.left == null && node.parent!=null
     * predecessor = node.parent.parent.parent....
     * 终止条件 node在parent的右子树中
     * <p>
     * node.left == null && node.parent == null
     * 那就是没有前驱
     */
    public Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        //从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 后继节点 中序遍历时的后一个节点   右子树最小的一个
     * 举例：1、8、4
     * 如果是二叉搜索树，后继节点就是后一个比它大的节点
     * node.right!= null
     * predecessor = node.right.left.left....
     * 终止条件 right为空
     * <p>
     * node.right == null && node.parent!=null
     * 举例：7、6、3、11
     * predecessor = node.parent.parent.parent....
     * 终止条件 node在parent的右子树中
     * <p>
     * node.right == null && node.parent == null
     * 那就是没有后继
     * 举例：没有右子树的根节点
     */
    public Node<E> successor(Node<E> node) {
        if (node == null) return null;

        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        //从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }


    /**
     * 创建节点
     * @param element
     * @param parent
     * @return 返回通用节点
     */
    protected Node<E> createNode(E element, Node<E> parent){
        return new Node<>(element,parent);
    }


    protected static class Node<E> {
        E element;
        Node<E> left;//左子节点
        Node<E> right;//右子节点
        Node<E> parent;//父节点
        //创建一个节点必然有父节点
        public Node(E element, Node<E> node) {
            this.element = element;
            this.parent = node;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild(){
            return parent!=null && this== parent.left;
        }

        public boolean isRightChild(){
            return parent!=null && this== parent.right;
        }

        /**
         * 兄弟节点
         * @return
         */
        public Node<E> sibling(){

            //如果是左子节点，右边是兄弟节点
            if (isLeftChild()){
                return parent.right;
            }
            //如果是右子节点，左边是兄弟节点
            if (isRightChild()){
                return parent.left;
            }

            //来到这里，说明没有子节点
            return null;
        }

    }



    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return  ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        String parentStr = "null";
        if (myNode.parent!=null){
            parentStr = myNode.parent.element.toString();
        }

        return myNode.element + "_p(" + parentStr + ")" ;
//        return node;
    }
}
