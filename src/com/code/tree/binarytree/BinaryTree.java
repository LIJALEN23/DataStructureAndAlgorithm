package com.code.tree.binarytree;

public interface BinaryTree<T extends Comparable<T>> {

    void insert(T val);

    boolean delete(T val);

    boolean contains(T val);

    T findMin();

    T findMax();

    void clear();

    int size();

    boolean isEmpty();

    void InOrderTraversal();
}
