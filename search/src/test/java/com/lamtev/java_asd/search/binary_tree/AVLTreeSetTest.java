package com.lamtev.java_asd.search.binary_tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AVLTreeSetTest {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @Test
    public void testAdd() {
        TreeSet<Integer> expected = new TreeSet<>();
        AVLTreeSet<Integer> actual = new AVLTreeSet<>();
        for (int i = 0; i < 1_000_000; ++i) {
            Integer element = RANDOM.nextInt(250_000);
            assertEquals(expected.add(element), actual.add(element));
        }
    }

    @Test
    public void testContains() {
        TreeSet<Integer> expected = new TreeSet<>();
        AVLTreeSet<Integer> actual = new AVLTreeSet<>();
        List<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 100_000; ++i) {
            Integer element = RANDOM.nextInt(100_000);
            expected.add(element);
            actual.add(element);
            elements.add(element);
        }
        for (int i = 0; i < 25_000; ++i) {
            Integer element = RANDOM.nextInt(25_000);
            expected.remove(element);
            actual.remove(element);
        }
        elements.forEach(
                element -> assertEquals(expected.contains(element), actual.contains(element))
        );
    }

    @Test
    public void testToArray() {
        TreeSet<Integer> expected = new TreeSet<>();
        AVLTreeSet<Integer> actual = new AVLTreeSet<>();
        for (int i = 0; i < 1_000_000; ++i) {
            Integer element = RANDOM.nextInt(1_000_000);
            expected.add(element);
            actual.add(element);
        }
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

}