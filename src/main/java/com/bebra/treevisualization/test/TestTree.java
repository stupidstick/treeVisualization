package com.bebra.treevisualization.test;

import com.bebra.treevisualization.tree.Iterator;
import com.bebra.treevisualization.tree.Tree;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class TestTree<K extends Comparable<K>, V>{
    private Node<K, V> head;
    private int size;

    public TestTree() {

    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public V get(K key, Runnable atIteration) {
        if (head == null || key == null)
            return null;
        var node = findNodeAndParent(key, atIteration).node;
        return node == null ? null : node.val;
    }


    public void put(K key, V val, Runnable atIteration) {
        Objects.requireNonNull(key);
        Node<K, V> newNode = new Node<>(key, val);
        if (head == null) {
            atIteration.run();
            head = newNode;
            size++;
            return;
        }
        Node<K, V> current = head;
        while (true) {
            atIteration.run();
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


    public boolean delete(K key, Runnable atIteration) {
        if (head == null)
            return false;

        NodeAndParent<K, V> nodeAndParent = findNodeAndParent(key, atIteration);
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
            Node<K, V> successor = getSuccessor(toDelete, atIteration);
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

    private NodeAndParent<K, V> findNodeAndParent(K key, Runnable atIteration) {
        Node<K, V> current = head;
        Node<K, V> parent = null;
        while (current != null && current.key.compareTo(key) != 0) {
            atIteration.run();
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return new NodeAndParent<>(current, parent);
    }

    private Node<K, V> getSuccessor(Node<K, V> node, Runnable atIteration) {
        Node<K, V> successorParent = node;
        Node<K, V> successor = node;
        Node<K, V> current = node.right;

        while (current != null) {
            atIteration.run();
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
