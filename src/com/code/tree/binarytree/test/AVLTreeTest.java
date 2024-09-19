package com.code.tree.binarytree.test;

import com.code.tree.binarytree.AVLTree;

public class AVLTreeTest {
    public static void main(String... args) {
        AVLTree<Integer, Integer> tree1 = new AVLTree<>();
        for (int i = 1; i <= 10; i++) {
            tree1.insert(i, i);
        }
        System.out.println();
        tree1.InOrderTraversal();
        System.out.println();

        tree1.delete(5);
        System.out.println();
        tree1.InOrderTraversal();
        System.out.println();

        tree1.clear();
    }
}
