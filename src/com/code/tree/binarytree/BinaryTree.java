package com.code.tree.binarytree;

public interface BinaryTree<K extends Comparable<K>, V> {

    void insert(K key, V val);

    void delete(K val);

    boolean contains(K val);

    Node<K, V> findMin(Node<K, V> node);

    Node<K, V> findMax(Node<K, V> node);

    V getVal(K key);

    void clear();

    boolean isEmpty();

    void InOrderTraversal();
}
