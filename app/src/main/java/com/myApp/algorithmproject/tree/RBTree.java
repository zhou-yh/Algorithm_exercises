package com.myApp.algorithmproject.tree;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020-06-04 08:42
 * description: 红黑树
 * ◼ 红黑树必须满足以下 5 条性质
 * 1. 节点是 RED 或者 BLACK
 * 2. 根节点是 BLACK
 * 3. 叶子节点（外部节点，空节点）都是 BLACK
 * 4. RED 节点的子节点都是 BLACK
 * ✓ RED 节点的 parent 都是 BLACK
 * ✓ 从根节点到叶子节点的所有路径上不能有 2 个连续的 RED 节点
 * 5. 从任一节点到叶子节点的所有路径都包含相同数目的 BLACK 节点
 */
public class RBTree<E> extends BBST<E>{

    private static final boolean RED = false;
    private static final boolean BLACK = true;


    public RBTree() {

    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    /**
     * 红黑树添加情况分析
     *
     * @param node
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        //添加的是根节点
        if (parent == null){
            black(node);
            return;
        }

        //如果父节点是black，满足性质4，不要做任何处理，直接返回
        if (isBlack(parent))return;

        //叔父节点
        Node<E> uncle = parent.sibling();
        //祖父节点
        Node<E> grand = parent.parent;
        if (isRed(uncle)){  //叔父节点是红色  上溢
            black(parent);
            black(uncle);
            //将祖父节点当作一个新添加的节点
            afterAdd(red(grand));
            return;
        }

        //叔父节点不是红色
        if (parent.isLeftChild()){ //L
            red(grand);
            if (node.isLeftChild()){ //LL
                black(parent);
            }else {  //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }else {
            red(grand);
            if (node.isLeftChild()){    //RL
                black(node);
                rotateRight(parent);
            }else {  //RR
                black(parent);
            }
            rotateLeft(grand);
        }

    }

    /**
     * 红黑树删除情况分析
     * a.删除节点是红色，直接删除,不作任何调整
     * b.删除的是黑色节点   3种情况
     *  1.拥有2个red子节点的black节点
     *      不可以被直接删除，因为会找子节点替代删除
     *      因此不考虑这个情况
     *  2.拥有1个red子节点的black节点
     *      判定条件:用以替代的子节点是red
     *      将替代的子节点染成黑色即保存红黑色性质
     *  3.black叶子节点
     *      sibling为black
     *      black叶子节点被删除后，会导致B树节点下溢
     *      如果sibling至少有一个red子节点
     *      进行旋转操作
     *      旋转之后的中心节点继承parent的颜色
     *      旋转之后将左右子节点染成black
     *
     *      判定条件：如果sibling没有一个red子节点
     *      将sibling染成red，parent染成balck即可修复红黑树性质
     *      如果parent是black
     *      会导致parent也下溢，这时需要把parent当做被删除的节点处理即可
     *
     *      如果sibling是red
     *      sibling染成black,parent染成red，进行旋转
     *      于是又回到sibling是black的情况
     * @param node
//     * @param replacement（可去除该参数）
     */
    @Override
    protected void afterRemove(Node<E> node) {
        //如果删除的节点是红色
        //或者用以取代删除node的子节点是红色
        if (isRed(node)){
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        //没有父节点，删除的是根节点
        if (parent == null) return;

        //删除的是黑色叶子节点[下溢]
        //判断被删除的node节点是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left){  //被删除的节点在左边，兄弟节点在右边

            if (isRed(sibling)){    //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
                //先判断父节点是不是黑色
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent);
//                    afterRemove(parent,null);
                }

            }else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
                //三种情况  子节点在左边，子节点在右边，左右都有子节点
                if (isBlack(sibling.right)){
                    //左边是黑色，兄弟要先旋转，左旋转
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                //先染色
                color(sibling,colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }


        }else {  //被删除的节点在右边，兄弟节点在左边

            if (isRed(sibling)){    //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
                //先判断父节点是不是黑色
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent);
//                    afterRemove(parent,null);
                }

            }else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
                //三种情况  子节点在左边，子节点在右边，左右都有子节点
                if (isBlack(sibling.left)){
                    //左边是黑色，兄弟要先旋转，左旋转
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                //先染色
                color(sibling,colorOf(parent));
                black(sibling.left);
                black(parent);
                //再旋转
                rotateRight(parent);

            }
        }
    }
//    protected void afterRemove(Node<E> node,Node<E> replacement) {
//        //如果删除的节点是红色
//        if (isRed(node))return;
//
//        //用以取代node的子节点是红色
//        if (isRed(replacement)){
//            black(replacement);
//            return;
//        }
//        Node<E> parent = node.parent;
//        //没有父节点，删除的是根节点
//        if (parent == null) return;
//
//        //删除的是黑色叶子节点[下溢]
//        //判断被删除的node节点是左还是右
//        boolean left = parent.left == null || node.isLeftChild();
//        Node<E> sibling = left ? parent.right : parent.left;
//        if (left){  //被删除的节点在左边，兄弟节点在右边
//
//            if (isRed(sibling)){    //兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateLeft(parent);
//                //更换兄弟
//                sibling = parent.right;
//            }
//
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)){
//                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
//                //先判断父节点是不是黑色
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack){
//                    afterRemove(parent,null);
//                }
//            }else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
//                //三种情况  子节点在左边，子节点在右边，左右都有子节点
//                if (isBlack(sibling.right)){
//                    //左边是黑色，兄弟要先旋转，左旋转
//                    rotateRight(sibling);
//                    sibling = parent.right;
//                }
//                //先染色
//                color(sibling,colorOf(parent));
//                black(sibling.right);
//                black(parent);
//                rotateLeft(parent);
//
//            }
//        }else {  //被删除的节点在右边，兄弟节点在左边
//
//            if (isRed(sibling)){    //兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateRight(parent);
//                //更换兄弟
//                sibling = parent.left;
//            }
//
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)){
//                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
//                //先判断父节点是不是黑色
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack){
//                    afterRemove(parent,null);
//                }
//
//
//            }else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
//                //三种情况  子节点在左边，子节点在右边，左右都有子节点
//                if (isBlack(sibling.left)){
//                    //左边是黑色，兄弟要先旋转，左旋转
//                    rotateLeft(sibling);
//                    sibling = parent.left;
//                }
//
//                //先染色
//                color(sibling,colorOf(parent));
//                black(sibling.left);
//                black(parent);
//                //再旋转
//                rotateRight(parent);
//            }
//        }
//    }

    /**
     * 重写方法，创建红黑树节点
     * @param element
     * @param parent
     * @return
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private Node<E> color(Node<E> node, boolean color){
        if (node == null) return node;
        ((RBNode<E>)node).color = color;
        return node;
    }


    /**
     * 转换成红色
     * @param node
     * @return
     */
    private Node<E> red(Node<E> node){
        return color(node,RED);
    }

    /**
     * 转换成黑色
     * @param node
     * @return
     */
    private Node<E> black(Node<E> node){
        return color(node,BLACK);
    }

    /**
     * 获取当前节点颜色
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node){
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }


    /**
     * 判断是黑色节点
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node){
        return colorOf(node) == BLACK;
    }

    /**
     * 判断是红色节点
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node){
        return colorOf(node) == RED;
    }

    private static class RBNode<E> extends Node<E>{

        boolean color = RED;

        public RBNode(E element, Node<E> node) {
            super(element, node);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (color == RED) sb.append("R").append("_");

            return sb.append(element).toString();
        }
    }


}
