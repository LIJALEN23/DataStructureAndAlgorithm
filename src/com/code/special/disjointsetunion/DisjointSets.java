package com.code.special.disjointsetunion;

/**========================
 *      并查集的简单实现
 * 完成时间：2024.6.12
 * 问题：未测试
 *
 * 难点：理解优化后每次查找根节点时会将树的高度优化
 *
 * 借鉴于ChatGPT
 * ========================
 * */

public class DisjointSets {
    private int[] parent;
    private int[] rank;

    /*初始化并查集
     * parent数组：储存每一个元素的父节点。初始化时，每个元素的父节点都是自己。
     * rank数组：储存每一个树的高度(也称为秩)。在合并时用于优化，保证树的高度尽量的小
     */
    public DisjointSets(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /*查找val所在的集合
     * 如果val的父节点不是自己，则递归的寻找val的父节点，直到找到根节点
     * 路径压缩优化：在查找过程中，将访问的所有节点直接连接到根节点，以减少后续操作中树的高度。
     */
    public int find(int val) {
        if (parent[val] != val) {
            parent[val] = find(parent[val]);    //路径压缩，将所有元素都连接到根节点(通过递归的返回过程以返回根节点)。
        }

        return parent[val];
    }

    /*将val1和val2所在的集合合并
     * 首先使用find(int val)找到各自的所在的集合(根节点root1 和 root2)
     * 如果root1 != root2，则它俩所在的集合不同需要合并
     * 按执合并优化：将秩小的集合的根节点连接到秩大的根节点下，以保持树的秩尽量的小。如果两个树的秩一样，则随便选一个连接
     */
    public void union(int val1, int val2) {
        int root1 = find(val1);
        int root2 = find(val2);

        if (root1 == root2) {
            return;
        }

        if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else {
            parent[root1] = root2;
            rank[root2] += 1;   //这两个树一样高，将root1连接到root2上，则root2的高度会增加1
        }
    }

    /*判断两个元素是否在同一个集合
     * 通过查找它们的根节点是否相同
     */
    public boolean connected(int val1, int val2) {
        return find(val1) == find(val2);
    }
}