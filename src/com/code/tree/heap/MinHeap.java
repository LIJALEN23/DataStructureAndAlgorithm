package com.code.tree.heap;

import java.util.Comparator;

public class MinHeap<T extends Comparable<T>> extends AbstractHeap<T> {
    public MinHeap(int capacity, Comparator<T> comparator) {
        super(capacity, Comparator.reverseOrder());
    }
}
