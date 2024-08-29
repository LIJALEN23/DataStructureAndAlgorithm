package com.code.sort;

public class Select extends SortHelper{
    public static <K extends Comparable<K>> void sort(K[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (more(array[minIndex], array[j])) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }
}
