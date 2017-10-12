package com.binarytree;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉树
 *
 * 实现二叉树的添加，删除，查找，遍历，最小/大值
 *
 * Created by Roll on 2017/10/11.
 *
 */
public class BinaryTree {

    // 根节点
    private Node root;

    // 添加节点
    public void add(int data) {

        Node newNode = new Node(data);

        if (root == null) {//root为空，新建的节点为根节点
            root = newNode;
        } else {

            Node current = root;
            Node parent;

            while (true) {

                parent = current;

                // 小值,向左放
                if (data < current.data) {

                    current = current.left;

                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }

                } else {// 大值，向右放

                    current = current.right;

                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }

            }

        }
    }

    // 查找节点
    public Node findNode(int data) {
        Node current = root;

        while (current.data != data) {

            //data小于当前节点的值，往左子树查询
            if (data < current.data)
                current = current.left;
            else
                current = current.right;

            //未找到节点
            if (current == null)
                return null;
        }

        return current;

    }

    // 获取根节点
    public Node getRoot() {
        return root;
    }

    // 前序遍历
    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // 中序遍历
    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    // 后序遍历
    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }

    // 层次遍历,Queue实现
    /**
     *  1.首先将根节点放入队列中。
     *  2.当队列为非空时，循环执行步骤3到步骤5，否则执行6；
     *  3.出队列取得一个结点，访问该结点；
     *  4.若该结点的左子树为非空，则将该结点的左子树入队列；
     *  5.若该结点的右子树为非空，则将该结点的右子树入队列；
     *  6.结束。
     * @param root
     */
    public void levelOrder(Node root) {

        //树为空
        if (root == null)
            return;

        /**
         *
         * 线程不安全
         * LinkedList：实现Queue接口
         *
         * 线程安全
         * LinkedBlockingQueue：阻塞，使用ReentrantLock
         * ConcurrentLinkedQueue：非阻塞，底层机器级别的原子指令来取代锁，CAS
         *
         */
        Queue<Node> queue = new LinkedBlockingQueue<Node>();

        queue.offer(root);//root入队

        while (!queue.isEmpty()) {

            Node pollNode = queue.poll();//出队一个节点，访问该节点的左右子树

            System.out.print(pollNode.data+" ");

            if (pollNode.left != null)
                queue.offer(pollNode.left);//左子树入队
            if (pollNode.right != null)
                queue.offer(pollNode.right);//右子树入队

        }

    }

    // 获取最小节点---遍历左子树
    public Node getMinNode() {
        Node current = root;
        Node minNode = null;

        while (current != null) {
            minNode = current;
            current = current.left;
        }

        return minNode;
    }

    // 获取最大节点---遍历右子树
    public Node getMaxNode() {
        Node current = root;
        Node maxNode = null;

        while (current != null) {
            maxNode = current;
            current = current.right;
        }
        return maxNode;
    }

    // 删除一个节点
    public boolean delete(int data) {

        Node current = root;// 当前节点
        Node parent = root;// 父节点
        boolean isLeft = true;// 该节点是父节点的左子树

        // while循环，找到要删除的节点current
        while (current.data != data) {

            parent = current;

            // data小于当current的data，说明该data在current的左子树
            if (data < current.data) {
                current = current.left;
            } else {
                isLeft = false;
                current = current.right;
            }

            // 未找到data
            if (current == null)
                return false;
        }

        /**
         * 要删除的节点有三种情况：叶节点、有一个节子点、有两个子节点
         */

        // 情况一：删除的节点为 叶子节点
        if (current.left == null && current.right == null) {
            // 节点为根节点
            if (current == root)
                root = null;

            if (isLeft)
                parent.left = null;
            else
                parent.right = null;

        } else if (current.right == null) {// 情况二-1：只存在一个左节点

            if (current == root)
                root = current.left;// 当前 节点的左节点设置为root节点
            else if (isLeft)// 当前节点相对于父节点为左子节点
                parent.left = current.left;
            else
                parent.right = current.left;

        } else if (current.left == null) {// 情况二-2：只存在一个右节点

            if (current == root)
                root = current.right;
            else if (isLeft)
                parent.left = current.right;
            else
                parent.right = current.right;

        } else {// 情况三：节点有两个子节点

            // 先找到该节点的后继节点。该节点的右子节点的左子节点，左子节点的左子节点.....

            Node successor = findSuccessor(current);

            if (current == root)
                root = successor;
            else if (isLeft)
                parent.left = successor;
            else
                parent.right = successor;

            successor.left = current.left;

        }

        return true;
    }

    // 查找 后继节点
    public Node findSuccessor(Node delNode) {

        Node successorParent = delNode;// 后继节点的父节点
        Node successor = delNode;// 后继节点

        Node current = delNode.right;// 右节点

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != delNode.right) {
            // 如果 后继节点有 右子节点，右子节点 付给 后继节点父节点 的左子节点。
            successorParent.left = successor.right;
            // 删除节点的右子节点 给 后继节点的右子节点
            successor.right = delNode.right;
        }

        return successor;
    }

}
