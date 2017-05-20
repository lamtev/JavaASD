package com.lamtev.java_asd.search.binary_tree;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AVLTreeSetTest {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @Test
    public void testAdd() {
        for (int i = 0; i < 10; ++i) {
            TreeSet<Integer> expected = new TreeSet<>();
            AVLTreeSet<Integer> actual = new AVLTreeSet<>();
            for (int j = 0; j < 1_000_000; ++j) {
                Integer element = RANDOM.nextInt(250_000);
                assertEquals(expected.add(element), actual.add(element));
            }
        }
    }

    @Test
    public void testRemove() {
        for (int i = 0; i < 10; ++i) {
            TreeSet<Integer> expected = new TreeSet<>();
            AVLTreeSet<Integer> actual = new AVLTreeSet<>();
            for (int j = 0; j < 1_000_000; ++j) {
                Integer element = RANDOM.nextInt(250_000);
                expected.add(element);
                actual.add(element);
            }
            for (int j = 0; j < 1_000_000; ++j) {
                Integer element = RANDOM.nextInt(250_000);
                assertEquals(expected.remove(element), actual.remove(element));
            }
        }
    }

    @Test
    public void testContains() {
        for (int i = 0; i < 10; ++i) {
            TreeSet<Integer> expected = new TreeSet<>();
            AVLTreeSet<Integer> actual = new AVLTreeSet<>();
            List<Integer> elements = new ArrayList<>();
            for (int j = 0; j < 100_000; ++j) {
                Integer el = RANDOM.nextInt(100_000);
                expected.add(el);
                actual.add(el);
                elements.add(el);
            }
            elements.forEach(el -> assertEquals(expected.contains(el), actual.contains(el)));
        }
    }

    @Test
    public void testIterator() {
        for (int i = 0; i < 10; ++i) {
            Set<Integer> expected = new TreeSet<>();
            Set<Integer> actual = new AVLTreeSet<>();
            for (int j = 0; j < 100_000; ++j) {
                int elem = RANDOM.nextInt(100_000);
                expected.add(elem);
                actual.add(elem);
            }
            Iterator<Integer> actualIterator = actual.iterator();
            Iterator<Integer> expectedIterator = expected.iterator();
            while (expectedIterator.hasNext()) {
                assertEquals(expectedIterator.next(), actualIterator.next());
            }
        }
    }

    @Test
    public void testFirst() {
        for (int i = 0; i < 10; ++i) {
            TreeSet<Integer> expected = new TreeSet<>();
            AVLTreeSet<Integer> actual = new AVLTreeSet<>();
            for (int j = 0; j < 1_000_000; ++j) {
                Integer element = RANDOM.nextInt(250_000);
                expected.add(element);
                actual.add(element);
                assertEquals(expected.first(), actual.first());
            }
        }
    }

    @Test
    public void testLast() {
        for (int i = 0; i < 10; ++i) {
            TreeSet<Integer> expected = new TreeSet<>();
            AVLTreeSet<Integer> actual = new AVLTreeSet<>();
            for (int j = 0; j < 1_000_000; ++j) {
                Integer element = RANDOM.nextInt(250_000);
                expected.add(element);
                actual.add(element);
                assertEquals(expected.last(), actual.last());
            }
        }
    }

}