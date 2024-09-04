package com.code.sort;

public class Merge extends SortHelper{
    public static <K extends Comparable<K>> void sort1(K[] array) {
        sort1(array, 0, array.length - 1);
    }

    private static <K extends Comparable<K>> void sort1(K[] array, int low, int high) {
        //即当只有一个或者两个元素时开始递归返回
        if (low < high) {

            int mid = (low + high) / 2;
            sort1(array, low, mid);
            sort1(array, mid + 1, high);

            merge(array, low, mid, high);
        }
    }

    public static <K extends Comparable<K>> void sort2(K[] array) {

        for (int currentSize = 1; currentSize < array.length; currentSize += currentSize) {
            for (int low = 0; low < array.length - currentSize; low += currentSize * 2) {
                int mid = low + currentSize - 1;
                int high = Math.min(low + currentSize + currentSize - 1, array.length - 1);
                merge(array, low, mid, high);
            }
        }
    }

    private static <K extends Comparable<K>> void merge(K[] array, int low, int mid, int high) {
        K[] temp = (K[]) new Comparable[high - low + 1];

        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (more(array[i], array[j])) {
                temp[k++] = array[j++];
            } else {
                temp[k++] = array[i++];
            }
        }

        //有剩余元素，则还需要加到temp中
        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= high) {
            temp[k++] = array[j++];
        }

        //复制到原数组中
        for ( i = low; i <= high; i++) {
            array[i] = temp[i - low];
        }
    }
}
