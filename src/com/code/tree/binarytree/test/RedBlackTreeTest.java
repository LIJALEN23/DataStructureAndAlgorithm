package com.code.tree.binarytree.test;

import com.code.tree.binarytree.RedBlackTree;

public class RedBlackTreeTest {
    public static void main(String... args)  {
        RedBlackTree<Integer, Integer> tree1 = new RedBlackTree<>();

        for (int i = 1; i <= 10; i++) {
            tree1.insert(i, i);
        }

        tree1.InOrderTraversal();
        System.out.println();

        tree1.delete(1);
        tree1.delete(2);
        tree1.InOrderTraversal();
        System.out.println();

        tree1.delete(3);
        tree1.InOrderTraversal();


    }
}
