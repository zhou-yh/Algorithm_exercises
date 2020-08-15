package com.myApp.algorithmproject.tree;

import com.myApp.algorithmproject.tree.printer.BinaryTreeInfo;
import com.myApp.algorithmproject.tree.printer.BinaryTrees;

/**
 * author: zhouyh
 * created on: 2020-05-24 10:00
 * description:
 */
public class Main {


    public static void main(String[] args) {

        Integer[] data = new Integer[]{
                62, 48, 36, 12, 31, 50, 61, 98, 53, 63

        };
        BST<Integer> bst = new BST<>();

        for (int i = 0; i < data.length;i++){
            bst.add(data[i]);
        }
        bst.preOrder1(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer integer) {
                System.out.println(" element - " + integer);
                return false;
            }
        });
        System.out.println("--------------------------------");
        bst.preOrder2(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer integer) {
                System.out.println(" element - " + integer);
                return false;
            }
        });
//        avlTree.remove(44);
//        bst.remove(3);
//        bst.remove(7);
//        BinaryTrees.println(avlTree);

//        System.out.println(bst);
//        System.out.println(bst.isCompleteTree2());

//        bst.preorderTraversal();
//        bst.inorderTraversal();
//        bst.postorderTraversal();
//        bst.levelOrderTreaversal();
//        bst.preOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//
//                return element == 1 ? true : false;
//            }
//        });
//        System.out.println();
//        bst.inOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return element == 4 ? true : false;
//            }
//        });
//        System.out.println();
//        bst.postOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return element == 1 ? true : false;
//            }
//        });
//        System.out.println();
//        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return element == 2 ? true:false;
//            }
//        });


//        BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new MyComparator());
//        bst1.add(new Person(12));
//        bst1.add(new Person(35));


//        test2();

    }


    public static void test1() {
        Integer[] data = new Integer[]{
                62, 48, 36, 12, 31, 50, 61, 98, 53, 63

        };

        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avlTree.add(data[i]);
        }
        BinaryTrees.println(avlTree);
    }

    public static void test2() {
        Integer[] data = new Integer[]{
                25, 86, 31, 16, 52, 72, 56
//                62, 48, 36, 12, 31, 50, 61, 98, 53, 63
        };

        RBTree<Integer> rbTree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
//            System.out.println(data[i] + "");
            rbTree.add(data[i]);
        }
        BinaryTrees.println(rbTree);
        System.out.println("------------------------------------------------------------------------");


        for (int i = 0; i < data.length; i++) {
            System.out.println("【 " + data[i] + " 】");
            rbTree.remove(data[i]);
            BinaryTrees.println(rbTree);
            System.out.println("------------------------------------------------------------------------");
        }
    }

}
