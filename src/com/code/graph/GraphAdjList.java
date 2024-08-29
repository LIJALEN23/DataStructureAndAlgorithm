package com.code.graph;

import java.util.*;

public class GraphAdjList<V> extends AbstractGraph<V> {
    private Map<V, List<V>> adjList;

    public GraphAdjList(int numVertices) {
        super(numVertices);
        adjList = new HashMap<>();
    }

//    public GraphAdjList() {
//        super(0);
//        adjList = new HashMap<>();
//    }

    @Override
    public void addEdge(V src, V dest) throws GraphException {
        adjList.computeIfAbsent(src, k -> new LinkedList<>());
        adjList.computeIfAbsent(dest, k -> new LinkedList<>());

        if (adjList.get(src).contains(src)) throw new GraphException("此边以存在");
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
    }

    @Override
    public void removeEdge(V src, V dest) throws GraphException {
        if (!hasEdge(src, dest)) throw new GraphException("此边不存在");

        adjList.get(src).remove(dest);
        adjList.get(dest).remove(src);
    }

    @Override
    public boolean hasEdge(V src, V dest) {
        return adjList.containsKey(src) && adjList.get(src).contains(dest);
    }

    @Override
    public void printGraph() {
        for (V vertex : adjList.keySet()) {
            System.out.print(vertex + " : ");
            for (V neighbor : adjList.get(vertex)) {
                System.out.println(neighbor + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void bfs(V start) {
        if (!adjList.containsKey(start)) throw new GraphException("此顶点不存在");

        Set<V> visited = new HashSet<>();
        Queue<V> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            V vertex = queue.poll();
            System.out.print(vertex + " ");

            for (V neighbor : adjList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    @Override
    public void dfs(V start) throws GraphException {
        if (!adjList.containsKey(start)) throw new GraphException("此顶点不存在");

        Set<V> visited = new HashSet<>();
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(V vertex, Set<V> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");

        for (V neighbor : adjList.get(vertex)) {
            if (!visited.contains(neighbor)) dfsHelper(neighbor, visited);
        }
    }


    public static void main(String[] args) throws GraphException {

    }
}