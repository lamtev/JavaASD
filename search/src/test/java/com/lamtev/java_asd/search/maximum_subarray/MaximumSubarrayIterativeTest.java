package com.lamtev.java_asd.search.maximum_subarray;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MaximumSubarrayIterativeTest {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private int[] generateArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; ++i) {
            array[i] = RANDOM.nextInt() % (Integer.MAX_VALUE / length);
        }
        return array;
    }

    @Test
    public void testMaximumSubarray() {
        final int[] LENGTHS = {10, 100, 1000, 10_000};
        for (int i = 0; i < 1000; ++i) {
            for (int length : LENGTHS) {
                int[] array = generateArray(length);

                int[] expectedSubarray = MaximumSubarrayBFS.maximumSubarray(array);
                int[] actualSubarray = MaximumSubarrayIterative.maximumSubarray(array);

                int expectedSum = Arrays.stream(expectedSubarray)
                        .sum();
                int actualSum = Arrays.stream(actualSubarray)
                        .sum();

                assertEquals(expectedSum, actualSum);
            }
        }
    }

}