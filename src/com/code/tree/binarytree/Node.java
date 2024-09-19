package com.code.tree.binarytree;

public interface Node<K extends Comparable<K>, V> {
    K getKey();

    void setKey(K key);

    V getVal();

    void setVal(V val);

    void setLeft(Node<K, V> left);

    Node<K, V> getLeft();

    void setRight(Node<K, V> right);

    Node<K, V> getRight();

}
