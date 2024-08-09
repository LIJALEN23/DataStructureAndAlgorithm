package com.code.hashtable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SeparateChainingHashTable<K, V> implements HashTable<K, V>{

    private static final int DEFAULT_CAPACITY = 16;
    private List<Node<K, V>>[] table;

    private int size;

    public SeparateChainingHashTable() {
        table = new ArrayList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = new ArrayList<>();
        }
        size = 0;
    }

    private static class Node<K, V> {
        K key;
        V val;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    @Override
    public void put(K key, V val) {
        int index = hash(key);
        for (Node<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                node.val = val;
                return;
            }
        }
        table[index].add(new Node<>(key, val));
        size++;
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchElementException();
        }
        int index = hash(key);
        for (Node<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                return node.val;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int index = hash(key);
        for (Node<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                table[index].remove(node);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        for (Node<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }
}

