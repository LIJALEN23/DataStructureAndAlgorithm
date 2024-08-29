package com.code.sort;

public class SortHelper {
    protected static <K> void swap(K[] arr, int firstIndex, int nextIndex) {
        K temp = arr[firstIndex];
        arr[firstIndex] = arr[nextIndex];
        arr[nextIndex] = temp;
    }

    protected static <K extends Comparable<K>> boolean more(K first, K next) {
        return first.compareTo(next) > 0;
    }

    protected static <K extends Comparable<K>> boolean isSorted(K[] array) {
        for (int i = 1; i < array.length; i++) {
            if (more(array[i - 1], array[i])) return false;
        }

        return true;
    }
}
