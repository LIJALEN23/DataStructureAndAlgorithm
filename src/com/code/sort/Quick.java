package com.code.sort;

public class Quick extends SortHelper{
    public static <K extends Comparable<K>> void sort(K[] array) {
        //调用辅助方法
        sort(array, 0, array.length - 1);
    }

    private static <K extends Comparable<K>> void sort(K[] array, int low, int high) {
        //递归条件：low指针再high指针之前
        if (low < high) {
            //计算切分元素的下标
            int partitionIndex = partition(array, low, high);

            //递归调用
            sort(array, low, partitionIndex - 1);
            sort(array, partitionIndex + 1, high);
        }
    }

    private static <K extends Comparable<K>> int partition(K[] array, int low, int high) {
        //将数组的low作为切分元素
        K pivot = array[low];

        //设置指针指向, 指向扫描数组外
        int i = low;
        int j = high + 1;

        //先进行一遍操作，再检查循环条件
        while (true) {
            while (more(pivot, array[++i])) if (i == high) break;
            while (more(array[--j], pivot)) if (j == low) break;
            if (i >= j) break;
            swap(array, i, j);
        }

        swap(array, low, j);

        //切分元素下标
        return j;
    }
}
