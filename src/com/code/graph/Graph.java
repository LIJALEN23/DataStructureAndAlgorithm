package com.code.graph;

public interface Graph<V> {

    void addEdge(V src, V dest);

    void removeEdge(V src, V dest);

    boolean hasEdge(V src, V dest);

    void printGraph();
}
