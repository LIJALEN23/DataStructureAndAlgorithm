package com.code.tree.heap;

import java.util.Comparator;

public class MaxHeap<T extends Comparable<T>> extends AbstractHeap<T>{
    public MaxHeap(int capacity, Comparator<T> comparator) {
        super(capacity, Comparator.naturalOrder());
    }
}
