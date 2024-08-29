package com.code.graph;

public abstract class AbstractGraph<V> implements Graph<V>, GraphTraversal<V> {
    protected int numVertices;

    public AbstractGraph(int numVertices) {
        this.numVertices = numVertices;
    }

    @Override
    public abstract void    addEdge(V src, V dest);

    @Override
    public abstract void removeEdge(V src, V dest);

    @Override
    public abstract boolean hasEdge(V src, V dest);

    @Override
    public abstract void printGraph();

    @Override
    public abstract void bfs(V start);

    @Override
    public abstract void dfs(V start);
}
