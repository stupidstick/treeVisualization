package com.bebra.treevisualization.tree;

public interface Iterable<T> {
    Iterator<T> begin();

    Iterator<T> end();

    Iterator<T> rBegin();

    Iterator<T> rEnd();
}
