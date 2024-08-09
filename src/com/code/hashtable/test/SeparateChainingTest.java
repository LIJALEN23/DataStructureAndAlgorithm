package com.code.hashtable.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import com.code.hashtable.SeparateChainingHashTable;

public class SeparateChainingTest {

    private SeparateChainingHashTable<String, Integer> table;

    @Before
    public void setUp() {
        table = new SeparateChainingHashTable<>();
    }

    @Test
    public void testAddAndGet() {
        table.put("lijalen", 1);
        table.put("wuling", 2);

        assertEquals(1, (int) table.get("lijalen"));
        assertEquals(2, (int) table.get("wuling"));
    }
}
