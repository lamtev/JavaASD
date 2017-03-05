package com.lamtev.java_asd.search.maximum_subarray;

import java.util.Arrays;

public class MaximumSubarrayIterative {

    /**
     * Поиск максимального подмассива за время O(n).
     * @param array исходный массив.
     * @return максимальный подмассив.
     */
    public static int[] maximumSubarray(int[] array) {
        int maximumSum = 0;
        int previousIterationSum = 0;
        int currentIterationLeft = 0;
        int left = 0;
        int right = 0;

        for (int i = 0; i < array.length; ++i) {
            int currentIterationSum = previousIterationSum + array[i];

            if (currentIterationSum < 0) {
                previousIterationSum = 0;
            } else {
                if (previousIterationSum == 0) {
                    currentIterationLeft = i;
                }
                previousIterationSum = currentIterationSum;
            }

            if (maximumSum < previousIterationSum) {
                maximumSum = previousIterationSum;
                left = currentIterationLeft;
                right = i;
            }
        }
        return Arrays.copyOfRange(array, left, right + 1);
    }

}
