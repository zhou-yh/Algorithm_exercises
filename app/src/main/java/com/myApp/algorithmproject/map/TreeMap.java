package com.myApp.algorithmproject.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zhouyh
 * created on: 2020-06-08 17:53
 * description: 映射
 */
public class TreeMap<K,V> implements Map<K,V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private int size;
    private Node<K,V> root;
    private Comparator<K> comparator;

//    private static class KV<K,V>{
//        K key;
//        V value;
//    }
//
//    private RBTree<KV<K,V>> tree = new RBTree<>();

    public TreeMap(){
        this(null);
    }


    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);
        if (root == null) {
            root = new Node(key,value,null);
            size++;

            //新添加节点后处理
            afterPut(root);
            return null;
        }

        /**
         * 添加不是第一个节点
         * 找到父节点
         * 创建新的子节点
         * parent.left = node 或者 parent.right = node
         */
        Node<K,V> parent = root;
        Node<K,V> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {  //相等
                node.key = key;
                V oldValue = node.value;
                node.value = value;

                return oldValue;
            }
        }

        //看看插入到父节点的哪个位置
        Node<K,V> newNode = new Node(key,value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        size++;

        //添加新节点之后处理
        afterPut(newNode);

        return null; //第一次插入进去，所以返回空
    }

    /**
     * 根据元素查找节点
     *
     * @param key
     * @return
     */
    private Node<K,V> node(K key) {
        Node<K,V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
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
     * 修复红黑树性质
     * @param node
     */
    private void afterPut(Node<K,V> node) {

        Node<K,V> parent =  node.parent;

        //添加的是根节点
        if (parent == null){
            black(node);
            return;
        }

        //如果父节点是black，满足性质4，不要做任何处理，直接返回
        if (isBlack(parent))return;

        //叔父节点
        Node<K,V> uncle = parent.sibling();
        //祖父节点
        Node<K,V> grand = parent.parent;
        if (isRed(uncle)){  //叔父节点是红色  上溢
            black(parent);
            black(uncle);
            //将祖父节点当作一个新添加的节点
            afterPut(red(grand));
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

    private Node<K,V> color(Node<K,V> node, boolean color){
        if (node == null) return node;
        node.color = color;
        return node;
    }


    /**
     * 转换成红色
     * @param node
     * @return
     */
    private Node<K,V> red(Node<K,V> node){
        return color(node,RED);
    }

    /**
     * 转换成黑色
     * @param node
     * @return
     */
    private Node<K,V> black(Node<K,V> node){
        return color(node,BLACK);
    }

    /**
     * 获取当前节点颜色
     * @param node
     * @return
     */
    private boolean colorOf(Node<K,V> node){
        return node == null ? BLACK : node.color;
    }


    /**
     * 判断是黑色节点
     * @param node
     * @return
     */
    private boolean isBlack(Node<K,V> node){
        return colorOf(node) == BLACK;
    }

    /**
     * 判断是红色节点
     * @param node
     * @return
     */
    private boolean isRed(Node<K,V> node){
        return colorOf(node) == RED;
    }


    /**
     * 左旋转
     * @param grand
     * 还需要维护的内容
     * T1、p、g的parent属性
     * 先后更新g、p的高度
     */
    private void rotateLeft(Node<K,V> grand){
        Node<K,V> parent = grand.right;
        Node<K,V> child = parent.left;
        grand.right = child;
        parent.left = grand;


        afterRotate(grand,parent,child);


    }


    /**
     * 右旋转
     * @param grand
     */
    private void rotateRight(Node<K,V> grand){
        Node<K,V> parent = grand.left;
        Node<K,V> child = parent.right;

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
    private void afterRotate(Node<K,V> grand,Node<K,V> parent,Node<K,V> child){
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

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containValue(V value) {
        if (root == null) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<K,V> node = queue.poll();
            //需要判断value是否为null
           if (valEquals(value,node.value)){
               return true;
           }

           if (node.left != null){
               queue.offer(node.left);
           }

           if (node.right != null){
               queue.offer(node.right);
           }

        }
        return false;
    }


    private boolean valEquals(V v1, V v2){
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        //使用中序遍历  从小到大
        traversal(root,visitor);


    }

    private void traversal(Node<K,V> node,Visitor<K,V> visitor){
        if (node == null || visitor.stop) return;


        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.key,node.value);
        traversal(node.right, visitor);
    }



    private V remove(Node<K,V> node){

        if (node == null) return null;

        size--;

        V oldValue = node.value;

        if (node.hasTwoChildren()) { //度为2 有2个子节点
            //找到后继节点
            Node<K,V> s = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然为1或为0）
        Node<K,V> replacement = node.left != null ? node.left : node.right;
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
            //能来到这里说明只有一个子节点
            afterRemove(replacement);

        } else if (node.parent == null) { //node是叶子节点并且是根节点
            root = null;
            //删除之后的处理
            afterRemove(node);
        } else { //node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { //node == node.parent.right
                node.parent.right = null;
            }
            //删除之后的处理
            afterRemove(node);
        }

        return oldValue;

    }


    private void afterRemove(Node<K,V> node){
        //如果删除的节点是红色
        //或者用以取代删除node的子节点是红色
        if (isRed(node)){
            black(node);
            return;
        }
        Node<K,V> parent = node.parent;
        //没有父节点，删除的是根节点
        if (parent == null) return;

        //删除的是黑色叶子节点[下溢]
        //判断被删除的node节点是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K,V> sibling = left ? parent.right : parent.left;
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
    public Node<K,V> predecessor(Node<K,V> node) {
        if (node == null) return null;

        Node<K,V> p = node.left;
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
    public Node<K,V> successor(Node<K,V> node) {
        if (node == null) return null;

        Node<K,V> p = node.right;
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
     * return 等于0 相等   大于0 e1>e2   小于0   e1<e2
     *
     * @param k1
     * @param k2 用传入进来的比较器进行比较的逻辑
     * @return
     */
    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        return ((Comparable<K>) k1).compareTo(k2);
    }


    /**
     * 判断key不能为空
     *
     * @param key
     */
    public void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key mush not be null");
        }
    }




    private static class Node<K,V>{

        K key;
        V value;
        boolean color = RED;

        Node<K,V> left;//左子节点
        Node<K,V> right;//右子节点
        Node<K,V> parent;//父节点
        //创建一个节点必然有父节点
        public Node(K key ,V value, Node<K,V> node) {
            this.key = key;
            this.value = value;
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
        public Node<K,V> sibling(){

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
}
