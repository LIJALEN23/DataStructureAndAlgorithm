package com.code.sort;

public class Shell extends SortHelper{
    public static <K extends Comparable<K>> void sort(K[] array) {
        int h = 1;

        // 计算增量
        while (h < array.length / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                K item = array[i];
                int j = i;

                while (j >= h && more(array[j - h], item)) {
                    array[j] = array[j - h];
                    j -= h;
                }
                array[j] = item;
            }

            h = (h - 1) / 3;
        }
    }
}
