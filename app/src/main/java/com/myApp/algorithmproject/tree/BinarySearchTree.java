package com.myApp.algorithmproject.tree;

import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zhouyh
 * created on: 2020-05-24 09:30
 * description:二叉搜索树  重构前
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {


    private int size;
    //根节点
    private Node<E> root;


    //比较器
    private Comparator<E> comparator;


    /**
     * 比较器可传可不传
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * 构造函数
     *
     * @param comparator 比较器 定制个性化比较
     */
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * 添加节点
     *
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);
        //添加第一个节点
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }

        /**
         * 添加不是第一个节点
         * 找到父节点
         * 创建新的子节点
         * parent.left = node 或者 parent.right = node
         */
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {  //相等
                node.element = element;
                return;
            }
        }

        //看看插入到父节点的哪个位置
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }


        size++;

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
        while(node.parent != null && node == node.parent.left) {
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
        while(node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }


    /**
     * 前序遍历
     * 访问顺序   根节点、左子树、右子树
     *
     * @param
     */
    public void preorderTraversal() {
        preorderTraversal(root);
    }


    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /**
     * 中序遍历
     * 顺序 左子树 根节点  右子树  升序或降序
     */
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;

        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    /**
     * 后序遍历
     * 顺序  左子树   右子树   根节点
     */
    public void postorderTraversal() {
        postorderTraversal(root);
    }


    private void postorderTraversal(Node<E> node) {
        if (node == null) return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
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
     */
    public void levelOrderTreaversal() {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        //入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll(); //出队
            System.out.println(node.element);

            if (node.left != null) {
                queue.offer(node.left);
            }


            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }


    /**
     * return 等于0 相等   大于0 e1>e2   小于0   e1<e2
     *
     * @param e1
     * @param e2 用传入进来的比较器进行比较的逻辑
     * @return
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }


    /**
     * 删除节点
     * 叶子节点 直接删除
     * node == node.parent.left   则 node.parent.left = null
     * node == node.parent.right  则node.parent.right = null
     * node == node.parent     删除根节点 则 root = null
     *
     * 删除节点  度为1的节点 用子节点替代原节点的位置
     * child是node.left 或node.right
     * 用child替代node的位置
     * 如果node是左子节点
     * child.parent = node.parent  node.parent.left = child
     * 如果node是右子节点
     * child.parent = node.parent  node.parent.right = child
     * 如果node是根节点
     * root = child  child.parent = null
     *
     * 删除节点  度为2的节点
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }


    private void remove(Node<E> node){
        if (node == null )return;

        if (node.hasTwoChildren()){ //度为2 有2个子节点
            //找到后继节点
            Node<E> s = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然为1或为0）
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null){ //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left、right的指向
            if (node.parent == null){ //node是度为1的节点并且是根节点
                root = replacement;
            }else if (node == node.parent.left){
                node.parent.left = replacement;
            }else{
                node.parent.right = replacement;
            }
        }else if (node.parent == null){ //node是叶子节点并且是根节点
            root = null;
        }else { //node是叶子节点，但不是根节点
            if (node == node.parent.left){
                node.parent.left = null;
            }else { //node == node.parent.right
                node.parent.right = null;
            }
        }
        size--;
    }
    /**
     * 根据元素查找节点
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node !=null){
            int cmp = compare(element,node.element);
            if (cmp == 0) return node;
            if (cmp > 0){
                node =  node.right;
            }else { //cmp < 0
                node = node.left;
            }
        }
        return null;

    }

    public void clear() {
        root = null;
        size = 0;
    }


    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 判断不能为空
     *
     * @param element
     */
    public void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element mush not be null");
        }
    }

    /**
     * 前序遍历
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
     * 中序遍历
     */
    public void inOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrder(root, visitor);
    }

    private void inOrder(Node<E> node, Visitor<E> visitor) {

        //visitor.stop 终止递归
        if (node == null || visitor.stop) return;


        inOrder(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inOrder(node.right, visitor);


    }

    /**
     * 后序遍历
     */
    public void postOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrder(root, visitor);
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
     * 层序遍历
     *
     * @param visitor
     */
    public void levelOrder(Visitor<E> visitor) {
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
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        return myNode.element;
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

private static class Node<E> {
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "");
        return sb.toString();
    }

    private void toString(Node<E> node, StringBuilder sb, String prefix) {

        if (node == null) return;

        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix + "L---");
        toString(node.right, sb, prefix + "R---");
    }
}
