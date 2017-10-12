package com.binarytree;

/**
 * Created by Roll on 2017/10/11.
 */
public class Test {

    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();

        int[] array = {24, 45, 70, 95, 25, 14, 23, 13, 38, 50, 59, 96};
//        int[] array = { 12, 76, 35, 22, 16, 48, 90, 46, 9, 40 };
        for (int i = 0; i < array.length; i++)
            tree.add(array[i]);

        System.out.println("树的深度是：" + tree.treeDeep(tree.getRoot()));
        System.out.println("叶子节点有：" + tree.nodeNumber(tree.getRoot()));

        order(tree);
        System.out.println();

        order2(tree);

        // --------------------------------------------
        System.out.println("最小值：" + tree.getMinNode().data);
        System.out.println("最大值：" + tree.getMaxNode().data);

    }

    /**
     * 非递归遍历
     *
     * @param tree
     */
    public static void order2(BinaryTree tree) {

        System.out.print("前序非递：");
        tree.preOrder2(tree.getRoot());
        System.out.println();

        System.out.print("中序非递：");
        tree.inOrder2(tree.getRoot());
        System.out.println();

        System.out.print("后序非递：");
        tree.postOrder2(tree.getRoot());
        System.out.println();
    }

    /**
     * 递归遍历
     *
     * @param tree
     */
    public static void order(BinaryTree tree) {

        System.out.print("层次遍历：");
        tree.levelOrder(tree.getRoot());
        System.out.println();
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
    }

}
