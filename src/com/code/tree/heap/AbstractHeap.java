package com.code.tree.heap;

import java.util.Comparator;
import java.util.NoSuchElementException;

public abstract class AbstractHeap<T> {
    private T[] heap;
    private int size;
    private Comparator<T> comparator;

    public AbstractHeap(int capacity, Comparator<T> comparator) {
        this.heap = (T[]) new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private boolean less(int firstIndex, int nextIndex) {
        return comparator.compare(heap[firstIndex], heap[nextIndex]) < 0;
    }

    private void swim(int index) {
        while (index > 0 && less((index - 1) / 2, index)) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void sink(int index) {
        while (index * 2 + 1 <= size) {
            int leftChild = index * 2 + 1;
            if (leftChild < size && less(leftChild, leftChild + 1)) leftChild++;
            if (!less(index, leftChild)) break;
            swap(index, leftChild);
            index = leftChild;
        }
    }

    public void insert(T item) {
        if (size == heap.length) resize(size * 2);
        heap[size++] = item;
        swim(size--);
    }

    public void resize(int newCapacity) {
        T[] newHeap = (T[]) new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    public T delMax() {
        if (isEmpty()) throw new NoSuchElementException("The heap is empty!");
        T max = heap[0];
        swap(0, --size);
        sink(0);
        heap[size] = null;
        if ((size > 0) && (size == heap.length / 4)) resize(heap.length / 2);
        return max;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T getMax() {
        return heap[0];
    }
}
