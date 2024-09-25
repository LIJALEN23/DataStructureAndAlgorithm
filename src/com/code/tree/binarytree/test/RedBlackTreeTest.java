package com.code.tree.binarytree.test;

import com.code.tree.binarytree.RedBlackTree;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedBlackTreeTest {
    private RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

    @Test
    public void testInsert() {
        for (int i = 1; i <= 10; i++) {
            tree.insert(i, i);
        }

        for (int i = 1; i <= 10; i++) {
            assertEquals(i, (int) tree.getVal(i));
        }
    }
}
