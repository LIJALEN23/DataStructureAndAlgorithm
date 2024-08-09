package com.code.tree.binarytree;

public class BinarySearchTree<T extends Comparable<T>> implements BinaryTree<T> {

    private TreeNode<T> root;

    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    @Override
    public void insert(T val) {
            root = insert(root, val);
    }

    private TreeNode<T> insert(TreeNode<T> node, T val) {
        if (node == null) {
            size++;
            return new TreeNode<>(val);
        }

        int cmp = node.data.compareTo(val);
        if (cmp > 0) {
            return insert(node.left, val);
        } else if (cmp < 0){
            return insert(node.right, val);
        }

        return node;
    }

    @Override
    public boolean delete(T val) {
        return false;
    }

    @Override
    public boolean contains(T val) {
        if (isEmpty()) {
            throw new NullPointerException();
        }

        return contains(root, val);
    }

    private boolean contains(TreeNode<T> node, T val) {
        if (node == null) {
            return false;
        }

        if (node.data.compareTo(val) == 0) {
            return true;
        } else if (node.data.compareTo(val) < 0) {
            return contains(node.right, val);
        } else {
            return contains(node.left, val);
        }
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
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void InOrderTraversal() {

    }
}
