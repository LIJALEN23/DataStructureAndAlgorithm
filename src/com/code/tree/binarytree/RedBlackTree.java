package com.code.tree.binarytree;

import javax.naming.BinaryRefAddr;

public class RedBlackTree<T extends Comparable<T>> implements BinaryTree<T> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node<T extends Comparable<T>> extends TreeNode<T> {
        boolean color;

        public Node(T data) {
            super(data);
        }
    }

    @Override
    public void insert(T val) {

    }

    @Override
    public boolean delete(T val) {
        return false;
    }

    @Override
    public boolean contains(T val) {
        return false;
    }

    @Override
    public T findMin() {
        return null;
    }

    @Override
    public T findMax() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void InOrderTraversal() {

    }
}
