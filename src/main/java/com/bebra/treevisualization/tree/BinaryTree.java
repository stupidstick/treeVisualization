package com.bebra.treevisualization.tree;

import java.util.*;

public class BinaryTree<K extends Comparable<K>, V> implements Tree<K, V> {
    private Node<K, V> head;
    private int size;

    public BinaryTree() {

    }

    public BinaryTree(BinaryTree<K, V> original) {
        if (original == null)
            throw new NullPointerException();
        if (original.size == 0)
            return;
        List<Node<K, V>> nodes = nodes();
        int startIndex = nodes.indexOf(original.head);

        for (int i = startIndex; i >= 0; i--) {
            var current = nodes.get(i);
            put(current.key, current.val);
        }
        //for (int i = startIndex + 1; i)
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void set(K byKey, V val) {
        Objects.requireNonNull(byKey);
        if (head == null)
            throw new NoSuchElementException("Key " + byKey + " not found");
        var node = findNodeAndParent(byKey).node;
        if (node == null)
            throw new NoSuchElementException("Key " + byKey + " not found");
        node.val = val;
    }

    @Override
    public V get(K key) {
        if (head == null || key == null)
            return null;
        var node = findNodeAndParent(key).node;
        return node == null ? null : node.val;
    }

    @Override
    public void put(K key, V val) {
        Objects.requireNonNull(key);
        Node<K, V> newNode = new Node<>(key, val);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        Node<K, V> current = head;
        while (true) {
            int compare = current.key.compareTo(key);
            if (compare == 0) {
                current.val = val;
                break;
            }
            if (compare > 0) {
                if (current.left == null) {
                    current.left = newNode;
                    size++;
                    break;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = newNode;
                    size++;
                    break;
                }
                current = current.right;
            }
        }
    }

    @Override
    public boolean delete(K key) {
        if (head == null)
            return false;

        NodeAndParent<K, V> nodeAndParent = findNodeAndParent(key);
        Node<K, V> toDelete = nodeAndParent.node;
        Node<K, V> parent = nodeAndParent.parent;

        if (toDelete == null)
            return false;
        if (toDelete.left == null && toDelete.right == null) {
            if (toDelete == head) {
                head = null;
            } else if (parent.left == toDelete) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (toDelete.right == null) {
            if (toDelete == head) {
                head = toDelete.left;
            } else if (parent.left == toDelete) {
                parent.left = toDelete.left;
            } else {
                parent.right = toDelete.left;
            }
        } else if (toDelete.left == null) {
            if (toDelete == head) {
                head = toDelete.right;
            } else if (parent.left == toDelete) {
                parent.left = toDelete.right;
            } else {
                parent.right = toDelete.right;
            }
        } else {
            Node<K, V> successor = getSuccessor(toDelete);
            if (toDelete == head) {
                head = successor;
            } else if (parent.left == toDelete) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = toDelete.left;
        }
        size--;
        return true;
    }



    @Override
    public List<K> keys() {
        if (head == null)
            return Collections.emptyList();
        List<K> keys = new ArrayList<>();
        Stack<Node<K, V>> passed = new Stack<>();
        Node<K, V> current = head;
        while (current != null || !passed.isEmpty()) {
            while (current != null) {
                passed.push(current);
                current = current.left;
            }
            current = passed.pop();
            keys.add(current.key);
            current = current.right;
        }
        return keys;
    }

    public List<Node<K, V>> nodes() {
        if (head == null)
            return Collections.emptyList();
        List<Node<K, V>> nodes = new ArrayList<>();
        Stack<Node<K, V>> passed = new Stack<>();
        Node<K, V> current = head;
        while (current != null || !passed.isEmpty()) {
            while (current != null) {
                passed.push(current);
                current = current.left;
            }
            current = passed.pop();
            nodes.add(current);
            current = current.right;
        }
        return nodes;
    }

    @Override
    public String horizontalTree() {
        if (head == null)
            return " ";
        StringBuilder builder = new StringBuilder();
        Node<K, V> current = head;
        if (current.right != null) {
            appendTree(builder, current.right, true, "");
        }
        builder.append(current.key.toString()).append('\n');
        if (current.left != null) {
            appendTree(builder, current.left, false, "");
        }
        return builder.toString();
    }

    private void appendTree(StringBuilder builder, Node<K, V> node, boolean isRight, String indent) {
        if (node.right != null) {
            appendTree(builder, node.right, true, indent + (isRight ? "        " : " |      "));
        }
        builder.append(indent);
        if (isRight) {
            builder.append(" /");
        } else {
            builder.append(" \\");
        }
        builder.append("----- ")
                .append(node.key.toString())
                .append('\n');
        if (node.left != null) {
            appendTree(builder, node.left, false, indent + (isRight ? " |      " : "        "));
        }
    }

    public Node<K, V> getHead() {
        return head;
    }


    private NodeAndParent<K, V> findNodeAndParent(K key) {
        Node<K, V> current = head;
        Node<K, V> parent = null;
        while (current != null && current.key.compareTo(key) != 0) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return new NodeAndParent<>(current, parent);
    }

    private Node<K, V> getSuccessor(Node<K, V> node) {
        Node<K, V> successorParent = node;
        Node<K, V> successor = node;
        Node<K, V> current = node.right;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) {
            successorParent.left = successor.right;
            successor.right = node.right;
        }

        return successor;
    }

    @Override
    public Iterator<V> begin() {
        return new TreeIterator<>(nodes(), 0);
    }

    @Override
    public Iterator<V> end() {
        List<Node<K, V>> nodes = nodes();
        return new TreeIterator<>(nodes, nodes.size() - 1);
    }

    @Override
    public Iterator<V> rBegin() {
        List<Node<K, V>> nodes = nodes();
        return new ReverseTreeIterator<>(nodes, nodes.size() - 1);
    }

    @Override
    public Iterator<V> rEnd() {
        return new ReverseTreeIterator<>(nodes(), 0);
    }

    private static class TreeIterator<K, V> implements Iterator<V> {
        private int currentIndex;
        List<Node<K, V>> nodes;

        public TreeIterator(List<Node<K, V>> nodes, int beginIndex) {
            this.nodes = nodes;
            this.currentIndex = beginIndex;
        }

        @Override
        public void next() {
            currentIndex++;
        }

        @Override
        public void prev() {
            currentIndex--;
        }

        @Override
        public boolean hasNext() {
            return currentIndex != nodes.size() - 1;
        }

        @Override
        public boolean hasPrev() {
            return currentIndex != 0;
        }

        @Override
        public V get() {
            return nodes.get(currentIndex).val;
        }

        @Override
        public void set(V val) {
            nodes.get(currentIndex).val = val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TreeIterator<K, V> compareIterator = (TreeIterator<K, V>) o;
            if (currentIndex != compareIterator.currentIndex
                    || nodes.size() != compareIterator.nodes.size()
            ) return false;
            return nodes.equals(compareIterator.nodes);
        }
    }

    private static class ReverseTreeIterator<K, V> implements Iterator<V> {
        private int currentIndex;
        List<Node<K, V>> nodes;

        public ReverseTreeIterator(List<Node<K, V>> nodes, int beginIndex) {
            this.nodes = nodes;
            this.currentIndex = beginIndex;
        }

        @Override
        public void next() {
            currentIndex--;
        }

        @Override
        public void prev() {
            currentIndex++;
        }

        @Override
        public boolean hasNext() {
            return currentIndex != 0;

        }

        @Override
        public boolean hasPrev() {
            return currentIndex != nodes.size() - 1;
        }

        @Override
        public V get() {
            return nodes.get(currentIndex).val;
        }

        @Override
        public void set(V val) {
            nodes.get(currentIndex).val = val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (getClass() != o.getClass()) {
                return false;
            }
            ReverseTreeIterator<K, V> compareIterator = (ReverseTreeIterator<K, V>) o;
            if (currentIndex != compareIterator.currentIndex
                    || nodes.size() != compareIterator.nodes.size()
            ) return false;
            return nodes.equals(compareIterator.nodes);
        }
    }

    public static class Node<K, V> {
        private K key;
        private V val;
        private Node<K, V> left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "Key: " + key + "\n" +
                    "Val: " + val + "\n";
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public Node<K, V> getRight() {
            return right;
        }
    }

    private static class NodeAndParent<K, V> {
        Node<K, V> node;
        Node<K, V> parent;

        public NodeAndParent(Node<K, V> node, Node<K, V> parent) {
            this.node = node;
            this.parent = parent;
        }
    }
}
