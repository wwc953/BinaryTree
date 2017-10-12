package com.binarytree;


/**
 * Created by Roll on 2017/10/11.
 * <p>
 * 节点类
 */
public class Node {

    public Node left = null;//左孩子
    public int data;        //数据域
    public Node right = null;//右孩子

    public Node(int data) {
        this.data = data;
    }

}
