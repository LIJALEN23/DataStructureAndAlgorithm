package com.code.tree.binarytree.test;

import com.code.tree.binarytree.BinarySearchTree;
import org.junit.Test;

public class BinarySearchTreeTest {

    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> tree1 = new BinarySearchTree<>();
        for (int i = 0; i < 10; i++) {
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
