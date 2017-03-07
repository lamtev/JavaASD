package com.lamtev.java_asd.search.maximum_subarray;

import java.util.Arrays;

public class MaximumSubarrayBruteforce {

    /**
     * Поиск максимального подмассива за время O(n^2).
     * @param array исходный массив.
     * @return максимальный подмассив.
     */
    public static int[] maximumSubarray(int[] array) {
        int left = 0;
        int right = 0;
        int maximumSum = 0;
        for (int i = 0; i < array.length; ++i) {
            int sum = 0;
            for (int j = i; j < array.length; ++j) {
                sum += array[j];
                if (maximumSum < sum) {
                    maximumSum = sum;
                    left = i;
                    right = j;
                }
            }
        }
        return Arrays.copyOfRange(array, left, right + 1);
    }

}
