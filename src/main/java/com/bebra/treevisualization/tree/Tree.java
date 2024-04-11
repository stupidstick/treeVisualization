package com.bebra.treevisualization.tree;

import java.util.List;

public interface Tree<K extends Comparable<K>, V> extends Iterable<V> {

    int size();

    void clear();

    boolean isEmpty();

    void set(K byKey, V val);

    V get(K key);

    void put(K key, V val);

    boolean delete(K key);

    List<K> keys();

    String horizontalTree();

}
