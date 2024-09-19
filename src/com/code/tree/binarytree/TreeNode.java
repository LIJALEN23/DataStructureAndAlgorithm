package com.code.tree.binarytree;

public class TreeNode<K extends Comparable<K>, V> extends AbstractNode<K, V>{
    public TreeNode(K key, V val) {
        super(key, val);
    }
}
