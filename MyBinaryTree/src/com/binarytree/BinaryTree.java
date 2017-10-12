package com.binarytree;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉树
 * <p>
 * 实现二叉树的添加，删除，查找，最小/大值，遍历（递归、非递归）
 * <p>
 * Created by Roll on 2017/10/11.
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
            }//end while
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

    /**
     * 层次遍历,Queue实现
     * <p>
     * 1.首先将根节点放入队列中。
     * 2.当队列为非空时，循环执行步骤3到步骤5，否则结束；
     * 3.出队列取得一个结点，访问该结点；
     * 4.若该结点的左子树为非空，则将该结点的左子树入队列；
     * 5.若该结点的右子树为非空，则将该结点的右子树入队列；
     *
     * @param node
     */
    public void levelOrder(Node node) {
        //树为空
        if (node == null)
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
        queue.offer(node);//node入队

        while (!queue.isEmpty()) {
            node = queue.poll();//出队一个节点，访问该节点的左右子树
            System.out.print(node.data + " ");

            if (node.left != null)
                queue.offer(node.left);//左子树入队
            if (node.right != null)
                queue.offer(node.right);//右子树入队
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

    /**
     * 删除节点
     * <p>
     * 要删除的节点有三种情况：叶节点、有一个节子点、有两个子节点
     */
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

        // 情况一：删除的节点为 叶子节点
        if (current.left == null && current.right == null) {
            // 节点为根节点
            if (current == root)
                root = null;

            if (isLeft)
                parent.left = null;//该节点为父节点的左节点，设父节点的左节点为null
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
            //保存后继以及其父节点
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


    //前序遍历--非递归
    public void preOrder2(Node node) {
        //根节点不为空
        if (node == null)
            return;

        Stack<Node> stack = new Stack<Node>();
        //根节点入栈
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();
            System.out.print(node.data + " ");

            //右节点先入栈，左节点再入栈。
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }

    }

    /**
     * 中序遍历--非递归
     * @param node
     */
    public void inOrder2(Node node) {
        Stack<Node> stack = new Stack<Node>();

        while (node != null || !stack.isEmpty()) {
            if (node != null) {// 将全部的左子树压入占中
                stack.push(node);
                node = node.left;
            } else {
                // 到这说明左子树已经遍历完
                node = stack.pop();//弹出节点
                System.out.print(node.data + " ");
                node = node.right;//获取该节点的右子树,入栈
            }
        }
    }


    /**
     * 后序遍历--非递归
     *
     * 1、对于任一结点P，将其入栈，然后沿其左子树一直往下搜索，直到搜索到没有左孩子的结点。
     * <p>
     * 2、根据栈顶节点，找到其右孩子temp，判断temp:
     *      若temp为空，或者已经输出。则出栈，输出节点数据，用pre记录该节点信息。
     *      若temp不为空，或未输出。则重复步骤一。
     *
     * @param node
     */

    public void postOrder2(Node node) {
        Stack<Node> stack = new Stack<Node>();
        Node pre = node;//保存已输出过的node节点

        while (node != null || !stack.isEmpty()) {
            //左子树入栈
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            if (!stack.isEmpty()) {
                Node temp = stack.peek().right;//获取栈顶节点的右子树
                //右孩子为空，或右孩子已经输出
                if (temp == null || temp == pre) {
                    node = stack.pop();//出栈
                    System.out.print(node.data + " ");
                    pre = node;//将当前node标记已输出
                    node = null;
                } else {
                    node = temp;//将右子节点赋给node，重复之前操作
                }
            }
        }//end while
    }


}
