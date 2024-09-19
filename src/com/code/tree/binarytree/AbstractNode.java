package com.code.tree.binarytree;

public abstract class AbstractNode<K extends Comparable<K>, V> implements Node<K, V> {

    private K key;

    private V val;

    private int size;

    private Node<K, V> left;

    private Node<K, V> right;

    public AbstractNode(K key, V val) {
        this.key = key;
        this.val = val;
        left = null;
        right = null;
    }

    @Override
    public K getKey() { return this.key; }

    @Override
    public void setKey(K key) { this.key = key; }

    @Override
    public V getVal() { return this.val; }

    @Override
    public void setVal(V val) { this.val = val; }

    @Override
    public void setLeft(Node<K, V> left) { this.left = left; }

    @Override
    public Node<K, V> getLeft() { return this.left; }

    @Override
    public void setRight(Node<K, V> right) { this.right = right; }

    @Override
    public Node<K, V> getRight() { return this.right; }

}
