package com.code.tree.binarytree;

public class AVLNode<K extends Comparable<K>, V> extends TreeNode<K, V>{
    private int height;

    public AVLNode(K key, V val) {
        super(key, val);
        this.height = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
