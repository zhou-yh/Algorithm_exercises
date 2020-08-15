package com.myApp.algorithmproject.tree.leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zhouyh
 * created on: 2020-05-24 18:23
 * description:
 */
public class _226_翻转二叉树 {


    /**
     * 前序遍历 反转
     * @param root
     * @return
     */
//    public TreeNode invertTree(TreeNode root) {
//
//        if (root == null) return root;
//
//        TreeNode tmp = root.left;
//        root.left = root.right;
//        root.right = tmp;
//
//        invertTree(root.left);
//        invertTree(root.right);
//        return root;
//    }


    /**
     * 后序遍历 反转
     */
//    public TreeNode invertTree(TreeNode root) {
//
//        if (root == null) return root;
//
//        invertTree(root.left);
//        invertTree(root.right);
//
//        TreeNode tmp = root.left;
//        root.left = root.right;
//        root.right = tmp;
//
//
//        return root;
//    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
//    public TreeNode invertTree(TreeNode root) {
//
//        if (root == null) return root;
//
//        invertTree(root.left);
//
//
//        TreeNode tmp = root.left;
//        root.left = root.right;
//        root.right = tmp;
//
//        invertTree(root.left);
//
//        return root;
//    }


    /**
     * 层序遍历
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) return root;


        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return root;
    }
}
