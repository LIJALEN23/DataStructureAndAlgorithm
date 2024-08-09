package com.code.hashtable.test;

import com.code.hashtable.OpenAddressingHashTable;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


import java.nio.charset.StandardCharsets;

public class OpenAddressingTest {

    private OpenAddressingHashTable<String, Integer> hashTable;

    @Before
    public void setUp() {
        hashTable = new OpenAddressingHashTable<>();
    }

    @Test
    public void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertEquals(1, (int) hashTable.get("one"));
        assertEquals(2, (int) hashTable.get("two"));
    }

    @Test
    public void testRemove() {
        hashTable.put("three", 3);
        assertTrue(hashTable.containsKey("three"));

        boolean removed = hashTable.remove("three");
        assertTrue(removed);
        assertFalse(hashTable.containsKey("three"));
    }

    @Test
    public void testContainsKey() {
        hashTable.put("four", 4);
        assertTrue(hashTable.containsKey("four"));
        assertFalse(hashTable.containsKey("five"));
    }

    @Test
    public void testSizeAfterOperations() {
        hashTable.put("six", 6);
        hashTable.put("seven", 7);
        hashTable.put("eight", 8);

        assertEquals(3, hashTable.size());

        hashTable.remove("seven");

        assertEquals(2, hashTable.size());
    }
}
