package com.code.tree.heap;

import java.util.Comparator;

public class MyPriorityQueue<T> extends AbstractHeap<T> {
    public MyPriorityQueue(int capacity, Comparator<T> comparator) {
        super(capacity, comparator);
    }

    public void add(T item) {
        insert(item);
    }

    public T remove() {
        return delMax();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }
}
