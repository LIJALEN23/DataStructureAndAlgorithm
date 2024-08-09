package com.code.hashtable;

import java.util.*;
public class OpenAddressingHashTable<K extends Comparable<K>, V> implements HashTable<K, V>{
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.7F;

    private Entry<K, V>[] table;
    private int size;

    public OpenAddressingHashTable() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Entry<K, V> {
        K key;
        V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

    }

    @Override
    public void put(K key, V val) {
        if (size >= table.length * LOAD_FACTOR_THRESHOLD) {
            resize(table.length >> 1);
        }
        int index = findIndex(key);

        if (index == -1) {
            table[hash(key)] = new Entry<>(key, val);
            size++;
        } else {
            table[index].val = val;
        }

    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchElementException();
        }
        return table[hash(key)].val;
    }

    @Override
    public boolean remove(K key) {
        if (!containsKey(key)) {
            throw new NoSuchElementException();
        }
        table[hash(key)] = null;
        size--;
        rehashAfterRemove(hash(key));
        return true;
    }

    @Override
    public boolean containsKey(K key) {
        return findIndex(key) != -1;
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

    private void resize(int newCapacity) {
        Entry<K, V>[]oldTable = table;
        table = new Entry[newCapacity];
        size = 0;
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                put(entry.key, entry.val);
            }
        }
    }

    private int findIndex(K key) {
        int index = hash(key);

        //线性探测法寻址
        while (table[index] != null && !table[index].key.equals(key)) {
            index = (index + 1) % table.length;
        }

        return table[index] == null ? -1 : index;
    }

    private void rehashAfterRemove(int removeIndex) {
        for (int i = (removeIndex + 1) % table.length; i != removeIndex; i = (i + 1) % table.length) {
            if (table[i] != null) {
                Entry<K, V> entry = table[i];
                table[i] = null;
                size--;
                put(entry.key, entry.val);
            }
        }
    }
}

