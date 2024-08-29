package com.code.sort;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        Character[] arr = {'E', 'X', 'A', 'M', 'P', 'L', 'E'};

        Shell.sort(arr);
        if (SortHelper.isSorted(arr)) System.out.println(Arrays.toString(arr));
        else System.out.println("Error : The array is not sorted!");
    }
}
