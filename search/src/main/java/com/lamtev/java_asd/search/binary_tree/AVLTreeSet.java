package com.lamtev.java_asd.search.binary_tree;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class AVLTreeSet<E> implements SortedSet<E> {

    private final Comparator<? super E> comparator;
    private Node root;
    private int size;
    private boolean alreadyInserted;
    private boolean successfullyDeleted;

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
    public E first() {
        makeSureThatSetIsNotEmpty();
        return first(root).key;
    }

    @Override
    public E last() {
        makeSureThatSetIsNotEmpty();
        return last(root).key;
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
        makeSureThatKeyIsNotNull(key);
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
    public boolean add(E e) {
        makeSureThatKeyIsNotNull(e);
        alreadyInserted = true;
        root = insert(root, e);
        if (!alreadyInserted) {
            ++size;
        }
        return !alreadyInserted;
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        E key = (E) o;
        makeSureThatKeyIsNotNull(key);
        successfullyDeleted = false;
        root = remove(root, key);
        if (successfullyDeleted) {
            --size;
        }
        return successfullyDeleted;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
        alreadyInserted = false;
        successfullyDeleted = false;
    }

    private Node insert(Node toNode, E key) {
        if (toNode == null) {
            alreadyInserted = false;
            return new Node(key);
        }
        int cmp = compare(key, toNode.key);
        if (cmp == 0) {
            alreadyInserted = true;
        } else if (cmp < 0) {
            toNode.left = insert(toNode.left, key);
        } else {
            toNode.right = insert(toNode.right, key);
        }
        return balanced(toNode);
    }

    private int compare(E c1, E c2) {
        @SuppressWarnings("unchecked")
        Comparable<E> co1 = (Comparable<E>) c1;
        return comparator == null ? co1.compareTo(c2) : comparator.compare(c1, c2);
    }

    private Node balanced(Node node) {
        node.fixupHeight();
        if (node.balanceFactor() == 2) {
            if (node.right.balanceFactor() < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        if (node.balanceFactor() == -2) {
            if (node.left.balanceFactor() > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        return node;
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        Arrays.asList(node, right).forEach(Node::fixupHeight);
        return right;
    }

    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        Arrays.asList(node, left).forEach(Node::fixupHeight);
        return left;
    }

    private Node remove(Node fromNode, E value) {
        if (fromNode == null) {
            successfullyDeleted = false;
            return null;
        }
        int cmp = compare(value, fromNode.key);
        if (cmp < 0)
            fromNode.left = remove(fromNode.left, value);
        else if (cmp > 0) {
            fromNode.right = remove(fromNode.right, value);
        } else {
            successfullyDeleted = true;
            Node left = fromNode.left;
            Node right = fromNode.right;
            if (right == null) {
                return left;
            }

            Node first = first(right);
            first.right = removeFirst(right);
            first.left = left;
            return balanced(first);
        }
        return balanced(fromNode);
    }

    private Node removeFirst(Node fromNode) {
        if (fromNode.left == null) {
            return fromNode.right;
        }
        fromNode.left = removeFirst(fromNode.left);
        return balanced(fromNode);
    }

    private Node first(Node withinNode) {
        if (withinNode.left == null) {
            return withinNode;
        }
        return first(withinNode.left);
    }

    private Node last(Node withinNode) {
        if (withinNode.right == null) {
            return withinNode;
        }
        return last(withinNode.right);
    }

    private void makeSureThatKeyIsNotNull(E key) {
        if (key == null) {
            throw new NullPointerException("Method parameter is null");
        }
    }

    private void makeSureThatSetIsNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Set is empty");
        }
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
