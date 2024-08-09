package com.code.lineartable.queue.test;

import com.code.lineartable.queue.MyArrayDeque;
import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;

public class MyArrayDequeTest {
    @Test
    public void testAddAndRemove() {
        MyArrayDeque<Integer> deque = new MyArrayDeque<>();

        // Add elements to the deque
        Assert.assertTrue(deque.add(1));
        Assert.assertTrue(deque.add(2));
        Assert.assertTrue(deque.add(3));

        // Check the size and elements
        Assert.assertEquals(3, deque.size());
        Assert.assertEquals(Integer.valueOf(1), deque.getFirst());
        Assert.assertEquals(Integer.valueOf(3), deque.getLast());

        // Remove elements from the deque
        Assert.assertEquals(Integer.valueOf(1), deque.removeFirst());
        Assert.assertEquals(Integer.valueOf(3), deque.removeLast());

        // Check the remaining element
        Assert.assertEquals(1, deque.size());
        Assert.assertEquals(Integer.valueOf(2), deque.getFirst());
        Assert.assertEquals(Integer.valueOf(2), deque.getLast());
    }

    @Test
    public void testIterator() {
        MyArrayDeque<Integer> deque = new MyArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);

        Iterator<Integer> iterator = deque.iterator();
        Assert.assertEquals(Integer.valueOf(1), iterator.next());
        Assert.assertEquals(Integer.valueOf(2), iterator.next());
        Assert.assertEquals(Integer.valueOf(3), iterator.next());

        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testDescendingIterator() {
        MyArrayDeque<Integer> deque = new MyArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);

        Iterator<Integer> iterator = deque.descendingIterator();
        Assert.assertEquals(Integer.valueOf(3), iterator.next());
        Assert.assertEquals(Integer.valueOf(2), iterator.next());
        Assert.assertEquals(Integer.valueOf(1), iterator.next());

        Assert.assertFalse(iterator.hasNext());
    }

}
