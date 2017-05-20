package com.lamtev.java_asd.search.binary_tree;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class AVLTreeSet<E> implements SortedSet<E> {

    private final Comparator<? super E> comparator;
    private Node root;
    private boolean alreadyInserted;
    private int size = 0;

    public AVLTreeSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public AVLTreeSet() {
        this(null);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this::compare;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E last() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        E key = (E) o;
        validateValueIsNull(key);
        if (root != null) {
            Node current = root;
            while (current != null) {
                int cmp = compare(current.key, key);
                if (cmp < 0) {
                    current = current.right;
                } else if (cmp > 0) {
                    current = current.left;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new AVLTreeIterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        validateValueIsNull(e);
        alreadyInserted = true;
        root = insert(root, e);
        if (!alreadyInserted) {
            ++size;
        }
        return !alreadyInserted;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private Node insert(Node current, E key) {
        if (current == null) {
            alreadyInserted = false;
            return new Node(key);
        }
        int cmp = compare(key, current.key);
        if (cmp == 0) {
            alreadyInserted = true;
        } else if (cmp < 0) {
            current.left = insert(current.left, key);
        } else {
            current.right = insert(current.right, key);
        }
        return balanced(current);
    }

    private int compare(E c1, E c2) {
        @SuppressWarnings("unchecked")
        Comparable<E> co1 = (Comparable<E>) c1;
        return comparator == null ? co1.compareTo(c2) : comparator.compare(c1, c2);
    }

    private Node balanced(Node current) {
        current.fixupHeight();
        if (current.balanceFactor() == 2) {
            if (current.right.balanceFactor() < 0) {
                current.right = rotateRight(current.right);
            }
            return rotateLeft(current);
        }
        if (current.balanceFactor() == -2) {
            if (current.left.balanceFactor() > 0) {
                current.left = rotateLeft(current.left);
            }
            return rotateRight(current);
        }
        return current;
    }

    private Node rotateLeft(Node current) {
        Node right = current.right;
        current.right = right.left;
        right.left = current;
        Arrays.asList(current, right).forEach(Node::fixupHeight);
        return right;
    }

    private Node rotateRight(Node current) {
        Node left = current.left;
        current.left = left.right;
        left.right = current;
        Arrays.asList(current, left).forEach(Node::fixupHeight);
        return left;
    }

    private void validateValueIsNull(E value) {
        if (value == null) {
            throw new NullPointerException("Method parameter is null");
        }
    }

    private class Node {
        private E key;
        private int height;
        private Node left;
        private Node right;

        private Node(E key) {
            this.key = key;
        }

        private void fixupHeight() {
            int leftHeight = height(left);
            int rightHeight = height(right);
            height = Math.max(leftHeight, rightHeight) + 1;
        }

        private int balanceFactor() {
            return height(right) - height(left);
        }

        private int height(Node node) {
            if (node != null) {
                return node.height;
            }
            return 0;
        }

    }

    public class AVLTreeIterator implements Iterator<E> {

        private Node current = null;
        private Deque<Node> stack = new ArrayDeque<>();

        private AVLTreeIterator() {
            Node node = root;
            fillTheStack(node);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.key;
        }

        private Node findNext() {
            Node node = current == null ? null : current.right;
            fillTheStack(node);
            try {
                return stack.pop();
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        private void fillTheStack(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

    }

}
