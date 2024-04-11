package com.bebra.treevisualization.tree;

public interface Iterator<T> {

    void next();
    void prev();

    boolean hasNext();
    boolean hasPrev();

    T get();
    void set(T val);

}
