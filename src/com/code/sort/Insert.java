package com.code.sort;

public class Insert extends SortHelper{
    public static <K extends Comparable<K>> void sort1(K[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (more(array[j - 1], array[j])) {
                    swap(array, j - 1, j);
                }
            }
        }
    }

    public static <K extends Comparable<K>> void sort2(K[] array) {
        for (int i = 1; i < array.length; i++) {
            K item = array[i];

            int j = i - 1;
            while (j >= 0 && more(array[j], item)) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = item;
        }
    }
}
