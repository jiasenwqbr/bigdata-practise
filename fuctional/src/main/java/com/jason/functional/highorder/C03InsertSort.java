package com.jason.functional.highorder;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月04日 09:49
 */
public class C03InsertSort {
    public static void main(String[] args) {
        Integer[] arr = {5, 2, 4, 6, 1, 3};
        insertionSort(arr, (a, b) -> a < b);
        System.out.println(Arrays.toString(arr));
    }

    private static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // 如果数组为空或只有一个元素，不需要排序
        if (arr == null || arr.length <= 1) {
            return;
        }
        for(int i = 1;i<arr.length;i++){
            T rightVal = arr[i]; // 当前要插入的元素
            int left = i - 1;
            // 将当前元素与它之前的元素比较，如果当前元素小，则将前一个元素后移
            while (left >= 0 &&  comparator.lessThan(rightVal, arr[left]) ) {
                arr[left + 1] = arr[left];
                left--;
            }

            // 将当前元素插入到正确的位置
            arr[left + 1] = rightVal;
        }


    }
    interface Comparator<T> {
        boolean lessThan(T a, T b);
    }
}
