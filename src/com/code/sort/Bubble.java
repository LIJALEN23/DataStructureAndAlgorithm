package com.code.sort;

public class Bubble extends SortHelper{
    public static <K extends Comparable<K>> void sort(K[] array) {
        boolean swapFlag = false;   //若第一趟没有交换元素，说明数组已经有序

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (more(array[j], array[j + 1])) {
                    swap(array, j, j + 1);
                    swapFlag = true;
                }
            }

            if (!swapFlag) {
                return;
            }
        }
    }
}
