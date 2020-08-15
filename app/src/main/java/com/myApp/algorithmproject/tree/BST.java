package com.myApp.algorithmproject.tree;

import java.util.Comparator;

/**
 * author: zhouyh
 * created on: 2020-05-25 08:55
 * description:二叉搜索树
 */
public class BST<E> extends BinaryTree<E> {

    //比较器
    protected Comparator<E> comparator;
    /**
     * 比较器可传可不传
     */
    public BST() {
        this(null);
    }

    /**
     * 构造函数
     *
     * @param comparator 比较器 定制个性化比较
     */
    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
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
            root = createNode(element, null);
            size++;

            //新添加节点后处理
            afterAdd(root);
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
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        size++;

        //添加新节点之后处理
        afterAdd(newNode);

    }


    protected void afterAdd(Node<E> node){

    }

    protected void afterRemove(Node<E> node){

    }

    /**
     * 删除节点
     * 叶子节点 直接删除
     * node == node.parent.left   则 node.parent.left = null
     * node == node.parent.right  则node.parent.right = null
     * node == node.parent     删除根节点 则 root = null
     * <p>
     * 删除节点  度为1的节点 用子节点替代原节点的位置
     * child是node.left 或node.right
     * 用child替代node的位置
     * 如果node是左子节点
     * child.parent = node.parent  node.parent.left = child
     * 如果node是右子节点
     * child.parent = node.parent  node.parent.right = child
     * 如果node是根节点
     * root = child  child.parent = null
     * <p>
     * 删除节点  度为2的节点
     *
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }


    private void remove(Node<E> node) {
        if (node == null) return;

        if (node.hasTwoChildren()) { //度为2 有2个子节点
            //找到后继节点
            Node<E> s = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然为1或为0）
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) { //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left、right的指向
            if (node.parent == null) { //node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            //删除之后的处理  不要合并到最后，后面其他数需要进行区分，参数可能会有改动
//            afterRemove(node,replacement);

            //能来到这里说明只有一个子节点
            afterRemove(replacement);

        } else if (node.parent == null) { //node是叶子节点并且是根节点
            root = null;
            //删除之后的处理
            afterRemove(node);
//            afterRemove(node,null);
        } else { //node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { //node == node.parent.right
                node.parent.right = null;
            }
            //删除之后的处理
            afterRemove(node);
//            afterRemove(node,null);
        }
        size--;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }


    /**
     * 根据元素查找节点
     *
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else { //cmp < 0
                node = node.left;
            }
        }
        return null;

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
     * 判断不能为空
     *
     * @param element
     */
    public void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element mush not be null");
        }
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
