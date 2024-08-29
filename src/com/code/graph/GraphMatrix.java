package com.code.graph;

import java.util.*;

public class GraphMatrix<V> extends AbstractGraph<V> {
    private int[][] adjMatrix;
    private Map<V, Integer> vertexMap;  //用于将顶点映射到矩阵索引
    private List<V> vertices;   //顶点列表，保持顶点顺序

    public GraphMatrix(int numVertices) {
        super(numVertices);
        this.adjMatrix = new int[numVertices][numVertices];
        this.vertexMap = new HashMap<>();
        this.vertices = new ArrayList<>();
    }

    @Override
    public void addEdge(V src, V dest) throws GraphException {
        //判断图中是否存在当前两个顶点
        if (!vertexMap.containsKey(src)) {
            if (vertices.size() >= numVertices) throw new GraphException("超出储存矩阵的最大容量");

            vertexMap.put(src, vertices.size());
            vertices.add(src);
        }
        if (!vertexMap.containsKey(dest)) {
            if (vertices.size() >= numVertices) throw new GraphException("超出储存矩阵的最大容量");

            vertexMap.put(dest, vertices.size());
            vertices.add(dest);
        }

        //添加边：连接两个顶点(无向图)
        int srcIndex = vertexMap.get(src);
        int destIndex = vertexMap.get(dest);
        adjMatrix[srcIndex][destIndex] = 1;
        adjMatrix[destIndex][srcIndex] = 1;
    }

    @Override
    public void removeEdge(V src, V dest) throws GraphException {
        if (!hasEdge(src, dest)) throw new GraphException("不存在此边");

        int srcIndex = vertexMap.get(src);
        int destIndex = vertexMap.get(dest);
        adjMatrix[srcIndex][destIndex] = 0;
        adjMatrix[destIndex][srcIndex] = 0;
    }

    @Override
    public boolean hasEdge(V src, V dest) {
        if (!vertexMap.containsKey(src) || !vertexMap.containsKey(dest)) {
            return false;
        }

        int srcIndex = vertexMap.get(src);
        int destIndex = vertexMap.get(dest);

        return adjMatrix[srcIndex][destIndex] == 1;
    }

    @Override
    public void printGraph() {
        System.out.println("邻接矩阵为");

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 深度优先
    @Override
    public void dfs(V start) throws GraphException {
        if (!vertexMap.containsKey(start)) throw new GraphException("不存在此顶点");

        boolean[] visited = new boolean[numVertices];
        dfsHelper(vertexMap.get(start), visited);
        System.out.println();
    }

    private void dfsHelper(int vertexIndex, boolean[] visited) {
        visited[vertexIndex] = true;

        System.out.println(vertices.get(vertexIndex) + " ");
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && adjMatrix[vertexIndex][i] == 1) {
                dfsHelper(i, visited);
            }
        }
    }

    // 广度优先
    @Override
    public void bfs(V start) throws GraphException {
        if (!vertexMap.containsKey(start)) throw new GraphException("不存在此顶点");

        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        int startIndex = vertexMap.get(start);
        queue.add(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int vertexIndex = queue.poll();
            System.out.println(vertices.get(vertexIndex) + " ");

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && adjMatrix[vertexIndex][i] == 1) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
        System.out.println();
    }


    public static void main(String[] args) throws GraphException {
        GraphMatrix<String> graph = new GraphMatrix<>(5);

        graph.addEdge("Chongqing", "Sichuan");
        graph.addEdge("Sichuan", "Yunnan");
        graph.addEdge("Chongqing", "Guizhou");
        graph.addEdge("Shanxi", "Chongqing");

        graph.printGraph();
        graph.dfs("Chongqing");
        graph.bfs("Chongqing");
    }
}