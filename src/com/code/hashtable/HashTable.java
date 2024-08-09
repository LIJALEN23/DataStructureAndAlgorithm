package com.code.hashtable;

public interface HashTable<K, V> {

    void put(K key, V val);

    V get(K key);

    boolean remove(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();
}

