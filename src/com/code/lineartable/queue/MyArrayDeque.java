package com.code.lineartable.queue;

import java.util.*;

public class MyArrayDeque<E> extends AbstractCollection<E> implements Deque<E> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75F;

    private Object[] elements;

    private int head;

    private int tail;

    private int size;

    public MyArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<E>{
        private int curIndex = head;
        @Override
        public boolean hasNext() {
            return curIndex != tail;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = (E) elements[curIndex];
            curIndex = (curIndex + 1) % elements.length;
            return e;
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    private class DescendingIterator implements Iterator {
        private int curIndex = tail;
        @Override
        public boolean hasNext() {
            return curIndex != head;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = (E) elements[(curIndex - 1 + elements.length) % elements.length];
            curIndex = (curIndex - 1 + elements.length) % elements.length;
            return e;
        }

    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("e is a null pointer");
        }

        if (size == elements.length) {
            grow();
        }
        elements[tail] = e;     //指向最后一个元素的后一位
        tail = (tail + 1) % elements.length;
        size++;
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException("e is a null pointer");
        }

        if (size == elements.length) {
            grow();
        }
        head = (head + elements.length - 1) % elements.length;
        elements[head] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty!");
        }
        E e = (E) elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;

        if (size > 0 && size < elements.length * LOAD_FACTOR) {
            shrink();
        }
        return e;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty!");
        }

        int last = (tail - 1 + elements.length) % elements.length;
        E e = (E) elements[last];
        elements[last] = null;
        tail = last;  //更新尾指针
        size--;
        if (size > 0 && size < elements.length * LOAD_FACTOR) {
            shrink();
        }
        return e;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return removeLast();
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty!");
        }

        return (E) elements[head];
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty!");
        }

        return (E) elements[(tail - 1 + elements.length) % elements.length];
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return getFirst();
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException("removeFirstOccurrence not supported!");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException("removeLastOccurrence not supported!");
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }



    private void grow() {
        int newLength = elements.length >> 1;
        if (newLength < 0) {
            throw new IllegalStateException("The deque is too large to grow!");
        }

        Object[] newElements = new Object[newLength];
        System.arraycopy(elements, head, newElements, 0, elements.length - head);
        System.arraycopy(elements, tail, newElements, elements.length - head, tail);
        head = 0;
        tail = elements.length - 1;
        elements = newElements;
    }

    private void shrink() {
        int newLength = elements.length << 1;
        if (newLength < DEFAULT_CAPACITY) {
            newLength = DEFAULT_CAPACITY;
        }

        Object[] newElements = new Object[newLength];
        System.arraycopy(elements, head, newElements, 0, elements.length - head);
        System.arraycopy(elements, tail, newElements, elements.length - head, tail);
        head = 0;
        tail = elements.length - 1;
        elements = newElements;
    }

}

