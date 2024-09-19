package com.code.tree.binarytree;

public class BinarySearchTree<K extends Comparable<K>, V> implements BinaryTree<K, V> {

    protected Node<K, V> root;

    public BinarySearchTree() {
        root = null;
    }

    @Override
    public void insert(K key, V val) {
        root = insert(root, key, val);
    }

    private Node<K, V> insert(Node<K, V> current, K key, V val) {
        if (current == null) {
            return new TreeNode<>(key, val);
        }

        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) {
            current.setLeft(insert(current.getLeft(), key, val));
        } else if (cmp < 0){
            current.setRight(insert(current.getRight(), key, val));
        }

        return current;
    }

    @Override
    public void delete(K key) {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");
        root = delete(root, key);
    }

    private Node<K, V> delete(Node<K, V> current, K key) {
        if (current == null) return null;

        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) {
            current.setLeft(delete(current.getLeft(), key));
        } else if (cmp < 0) {
            current.setRight(delete(current.getRight(), key));
        } else {
            if (current.getRight() == null && current.getLeft() == null) {
                current = null;
            } else if (current.getRight() != null && current.getLeft() != null) {
                var left = current.getLeft();
                var right = current.getRight();
                current = findMin(current.getRight());
                current.setLeft(left);
                current.setRight(delete(current.getRight(), current.getKey()));
            } else {
                current = (current.getLeft() == null ? current.getRight() : current.getLeft());
            }
        }

        return current;
    }

    @Override
    public boolean contains(K val) {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");
        return contains(root, val);
    }

    private boolean contains(Node<K, V> current, K key) {
        if (current == null) {
            return false;
        }

        if (current.getKey().compareTo(key) == 0) {
            return true;
        } else if (current.getKey().compareTo(key) < 0) {
            return contains(current.getRight(), key);
        } else {
            return contains(current.getLeft(), key);
        }
    }

    @Override
    public Node<K, V> findMin(Node<K, V> current) {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");

        if (current.getLeft() == null) return current;

        return findMin(current.getLeft());
    }

    @Override
    public Node<K, V> findMax(Node<K, V> current) {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");

        if (current.getRight() == null) return current;

        return findMax(current.getRight());
    }

    @Override
    public V getVal(K key) {
        return getVal(root, key);
    }

    private V getVal(Node<K, V> current, K key) {
        if (current == null) return null;

        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) return getVal(current.getLeft(), key);
        else if (cmp < 0) return getVal(current.getRight(), key);
        else return current.getVal();
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {return root == null; }

    @Override
    public void InOrderTraversal() {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");
        InOrderTraversal(root);
    }

    private void InOrderTraversal(Node<K, V> current) {
        if (current == null) return;

        InOrderTraversal(current.getLeft());
        System.out.print(current.getVal() + " ");
        InOrderTraversal(current.getRight());
    }

}