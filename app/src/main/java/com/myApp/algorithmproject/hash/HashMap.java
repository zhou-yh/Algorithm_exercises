package com.myApp.algorithmproject.hash;

import com.myApp.algorithmproject.map.Map;
import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;
import com.myApp.algorithmproject.tree.printer.BinaryTrees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * author: zhouyh
 * created on: 2020-06-10 08:58
 * description: 自己实现HashMap
 */
public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 1 << 4; //数组长度设计为2^n

    //默认扩容因子
    private static final float DEFAULT_LOCAL_FACTOR = 0.75f;


    private int size;
    private Node<K, V>[] table;
    private Comparator<K> comparator;


    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        
        resize();

        int index = index(key);
        // //取出index对应的红黑树节点
        Node<K, V> root = table[index];
        if (root == null) {  //根节点处理
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            black(root);
            return null;
        }

        /**
         * 添加不是第一个节点
         * 找到父节点
         * 创建新的子节点
         * parent.left = node 或者 parent.right = node
         */
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K,V> result = null;
        boolean searched = false;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                cmp = 1;
            }else if (h1 < h2){
                cmp = -1;
            }else if (Objects.equals(k1,k2)){
                cmp = 0;
            }else if (k1 != null && k2 !=null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp =((Comparable) k1).compareTo(k2))!= 0){

            }else if (searched){//先扫描，然后再根据内存地址大小决定左右
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }else {  //searched = false
                if (node.right != null && (result = node(node.right,k1))!= null
                        ||node.left != null && (result = node(node.left,k1))!= null){
                    //已经存在这个key
                    node = result;
                    cmp = 0;
                }else {  //不存在这个key
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }
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
        } while (node != null);

        //看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        size++;

        //添加新节点之后处理
        fixAfterPut(newNode);


        return null;
    }

    /**
     * 创建节点
     * @param key
     * @param value
     * @param parent
     * @return 返回通用节点
     */
    protected Node<K,V> createNode(K key,V value, Node<K,V> parent){
        return new Node<>(key,value,parent);
    }


    /**
     * 扩容
     */
    private void resize() {
        //如果小于 0.75
        if (size / table.length <= DEFAULT_LOCAL_FACTOR) return;

        Node<K,V>[]  oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++){
            if (oldTable[i] == null) continue;
            queue.offer(oldTable[i]);
            while (!queue.isEmpty()){
                Node<K,V> node = queue.poll();
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
                moveNode(node);

            }
        }
    }

    /**
     * 挪动节点
     * @param newNode
     */
    private void moveNode(Node<K,V> newNode) {

        //重制
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;


        int index = index(newNode);
        //取出index位置对应的红黑树节点
        Node<K,V> root = table[index];
        if (root == null){
            root = newNode;
            table[index] = root;
            fixAfterPut(newNode);
            return;
        }

        Node<K,V> parent = root;
        Node<K,V> node = root;

        int cmp = 0;
        K k1 = newNode.key;
        int h1 = hash(k1);
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                cmp = 1;
            }else if (h1 < h2){
                cmp = -1;
            }else if (k1 != null && k2 !=null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp =((Comparable) k1).compareTo(k2))!= 0){

            }else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        //看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        //添加新节点之后处理
        fixAfterPut(newNode);


    }


    @Override
    public V get(K key) {


        Node<K, V> node = node(key);


        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {

        return remove(node(key));
    }


    protected V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K,V> willNode = node;

        size--;

        V oldValue = node.value;

        if (node.hasTwoChildren()) { //度为2 有2个子节点
            //找到后继节点
            Node<K, V> s = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然为1或为0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if (replacement != null) { //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left、right的指向
            if (node.parent == null) { //node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            //删除之后的处理  不要合并到最后，后面其他数需要进行区分，参数可能会有改动
            //能来到这里说明只有一个子节点
            fixAfterRemove(replacement);

        } else if (node.parent == null) { //node是叶子节点并且是根节点
            table[index] = null;
            //删除之后的处理
            fixAfterRemove(node);
        } else { //node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { //node == node.parent.right
                node.parent.right = null;
            }
            //删除之后的处理
            fixAfterRemove(node);
        }
        
        //交给子类去处理
        afterRemove(willNode,node);

        return oldValue;
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
    public Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> p = node.right;
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

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containValue(V value) {

        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);

            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                //需要判断value是否为null
                if (Objects.equals(value, node.value)) {
                    return true;
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }

            }
        }


        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {

        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                //需要判断value是否为null
                if (visitor.visit(node.key, node.value)) return;
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }


    public void print(){

        if (size == 0) return;


        for (int i = 0; i < table.length; i++){
            final Node<K,V> root = table[i];
            BinaryTrees.println(new BinaryTreeInfo() {

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K,V>)node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K,V>)node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
        }
    }


//    private Node<K, V> node(K key) {
//        Node<K, V> node = table[index(key)];
//        int h1 = key == null ? 0 : key.hashCode();
//        while (node != null) {
//
//            int cmp = compare(key, node.key, h1, node.hash);
//            if (cmp == 0) return node;
//            if (cmp > 0) {
//                node = node.right;
//            } else if (cmp < 0) {
//                node = node.left;
//            }
//        }
//        return null;
//    }

    /**
     * 重写node
     */
    private Node<K,V> node(K k1){
        Node<K,V> node = table[index(k1)];
        return node == null ? null : node(node,k1);
    }



    private Node<K,V> node(Node<K,V> node, K k1){
        int h1 = hash(k1);
        Node<K,V> result = null;
        int cmp = 0;
        while (node != null){
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                node = node.right;
            }else if (h1 < h2){
                node = node.left;
            }else if (Objects.equals(k1,k2)){
                return node;
            } else if (k1 != null && k2 !=null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2))!= 0){
                node = cmp > 0 ? node.right : node.left;
            }else if (node.right != null && (result = node(node.right,k1))!= null){  //哈希值相等，不具备可比较性,也不equal
                return result;
            }else {  //右边没找到只能左边找
                node = node.left;
            }
//            else if (node.left != null && (result = node(node.left,k1))!= null){
//                return result;
//            }else {
//                return null;
//            }
        }

        return null;
    }

    /**
     * return 等于0 相等   大于0 e1>e2   小于0   e1<e2
     *
     * @param k1
     * @param k2 用传入进来的比较器进行比较的逻辑
     *           h1 k1的hashcode
     *           h2 k2的hashcode
     * @return
     */
    private int compare(K k1, K k2, int h1, int h2) {

        //比较哈希值
        int result = h1 - h2;
        //哈希值相等直接返回
        if (result != 0) return result;


        //比较equals 如果为true 说明equal相等
        if (Objects.equals(k1, k2)) return 0;


        //哈希值相等，equal不相等

        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {
//            String k1Class = k1.getClass().getName();
//            String k2Class = k2.getClass().getName();
//            result = k1Class.compareTo(k2Class);
//            if (result != 0) return result;

            //另一种类型并且具备可比性
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }


        //同一种类型，哈希值相等，但不具备可比性
        //k1不为null ，k2为null
        //k1为null， k2不为null
        //比较内存地址  System.identityHashCode(k1)利用内存地址计算出的哈希值
//        System.identityHashCode(k1) - System.identityHashCode(k2);

        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    /**
     * 修复红黑树性质
     *
     * @param node
     */
    private void fixAfterRemove(Node<K, V> node) {
        //如果删除的节点是红色
        //或者用以取代删除node的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<K, V> parent = node.parent;
        //没有父节点，删除的是根节点
        if (parent == null) return;

        //删除的是黑色叶子节点[下溢]
        //判断被删除的node节点是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) {  //被删除的节点在左边，兄弟节点在右边

            if (isRed(sibling)) {    //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
                //先判断父节点是不是黑色
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }

            } else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
                //三种情况  子节点在左边，子节点在右边，左右都有子节点
                if (isBlack(sibling.right)) {
                    //左边是黑色，兄弟要先旋转，左旋转
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                //先染色
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }


        } else {  //被删除的节点在右边，兄弟节点在左边

            if (isRed(sibling)) {    //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下与兄弟合并
                //先判断父节点是不是黑色
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }

            } else {   //兄弟节点必然有一个是红色子节点,向兄弟节点借元素
                //三种情况  子节点在左边，子节点在右边，左右都有子节点
                if (isBlack(sibling.left)) {
                    //左边是黑色，兄弟要先旋转，左旋转
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                //先染色
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                //再旋转
                rotateRight(parent);
            }
        }
    }


    /**
     *
     * @param willNode  想删除的节点
     * @param removeNode 真正删除的节点
     */
    protected void afterRemove(Node<K,V> willNode,Node<K,V> removeNode){ }

    /**
     * 修复红黑树性质
     *
     * @param node
     */
    private void fixAfterPut(Node<K, V> node) {

        Node<K, V> parent = node.parent;

        //添加的是根节点
        if (parent == null) {
            black(node);
            return;
        }

        //如果父节点是black，满足性质4，不要做任何处理，直接返回
        if (isBlack(parent)) return;

        //叔父节点
        Node<K, V> uncle = parent.sibling();
        //祖父节点
        Node<K, V> grand = parent.parent;
        if (isRed(uncle)) {  //叔父节点是红色  上溢
            black(parent);
            black(uncle);
            //将祖父节点当作一个新添加的节点
            fixAfterPut(red(grand));
            return;
        }

        //叔父节点不是红色
        if (parent.isLeftChild()) { //L
            red(grand);
            if (node.isLeftChild()) { //LL
                black(parent);
            } else {  //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            red(grand);
            if (node.isLeftChild()) {    //RL
                black(node);
                rotateRight(parent);
            } else {  //RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }


    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }


    /**
     * 转换成红色
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 转换成黑色
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 获取当前节点颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }


    /**
     * 判断是黑色节点
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 判断是红色节点
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    /**
     * 左旋转
     *
     * @param grand 还需要维护的内容
     *              T1、p、g的parent属性
     *              先后更新g、p的高度
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;


        afterRotate(grand, parent, child);


    }


    /**
     * 右旋转
     *
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;

        grand.left = parent.right;
        parent.right = grand;


        afterRotate(grand, parent, child);
    }

    /**
     * 旋转之后的处理
     *
     * @param grand
     * @param parent
     * @param child
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        //让parent成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            table[index(grand)] = parent;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;

    }

    private int hash(K key){
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>>16);
    }

    /**
     * 根据key生成对应索引
     *
     * @param key
     * @return
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }


    protected static class Node<K, V> {

        int hash;
        K key;
        V value;
        boolean color = RED;

        Node<K, V> left;//左子节点
        Node<K, V> right;//右子节点
        Node<K, V> parent;//父节点

        //创建一个节点必然有父节点
        public Node(K key, V value, Node<K, V> node) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
            this.value = value;
            this.parent = node;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 兄弟节点
         *
         * @return
         */
        public Node<K, V> sibling() {

            //如果是左子节点，右边是兄弟节点
            if (isLeftChild()) {
                return parent.right;
            }
            //如果是右子节点，左边是兄弟节点
            if (isRightChild()) {
                return parent.left;
            }

            //来到这里，说明没有子节点
            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
}
