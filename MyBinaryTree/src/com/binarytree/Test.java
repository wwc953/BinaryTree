package com.binarytree;

/**
 * Created by Roll on 2017/10/11.
 */
public class Test {

    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();

        initTree(tree);

        System.out.print("层次遍历：");
        tree.levelOrder(tree.getRoot());
        System.out.println();

        System.out.print("前序遍历：");
        tree.preOrder(tree.getRoot());
        System.out.println();

        System.out.print("中序遍历：");
        tree.inOrder(tree.getRoot());
        System.out.println();

        System.out.print("后序遍历：");
        tree.postOrder(tree.getRoot());
        System.out.println();

        // --------------------------------------------
        System.out.println("最小值：" + tree.getMinNode().data);
        System.out.println("最大值：" + tree.getMaxNode().data);

        // --------------验证后继节点--------------------
        int number = 45;
        Node node = tree.findSuccessor(tree.findNode(number));
        System.out.println(number+"的后继节点是:" + node.data);


    }

    /**
     * 初始化二叉树
     *
     * @param tree
     */
    public static void initTree(BinaryTree tree) {
        tree.add(24);
        tree.add(14);
        tree.add(45);
        tree.add(13);
        tree.add(23);
        tree.add(25);
        tree.add(70);
        tree.add(38);
        tree.add(50);
        tree.add(95);
        tree.add(59);
        tree.add(96);
    }
}
