# 图论
图：由顶点以及连接的边所组成
G = (V, E)：V代表了边的集合，E代表了顶点的集合
## 基本概念和术语
### 基本术语：
- **顶点(vertex)**/结点(node)
- **边(edge)**：顶点之间的路径
- **邻接(adjacent)**：两个顶点被同一条边连接
- **关联(incident)**：连接两个顶点的边称为关联到这两个顶点
- **度(degree)**：顶点的度就是与这个顶点关联的边的条数
- **邻居(neighbor)**：如果两个顶点是邻接的
- **环(loop)**：一个顶点连接到自身的边
- **平行边(parallel edge)**：两个顶点可以通过两条或者多条边相连，这些边互为平行边
- **回路(cycle)**：始于一个顶点，然后终于通过一个顶点的封闭路径(没有封闭回路的图是一颗树)

### 图的分类
- **有向图**：边有方向
- **无向图**：边没有指向性
- **加权图**：边有大小
- **简单图**：没有环和平行边的图
- **完全图**：每一个顶点都有边相连
- **子图**：顶点集合和边集合是G的子集
- **生成树(spanning tree)**：是一个G的连通子图，该子图包括G中所有顶点的树

## 实现图
在计算机中实现图，就是要储存顶点和边的信息(数组和链表)。将顶点的信息看作一个整体
这四种表示图的方法各有其特点和适用场景，以下是对它们的简要介绍：

### 1. 邻接矩阵 (Adjacency Matrix)
- **表示方法**：使用一个 \( n \times n \) 的矩阵，其中 \( n \) 是图中顶点的数量。矩阵的每个元素 \( a[i][j] \) 表示顶点 \( i \) 和顶点 \( j \) 之间是否存在边（有边则值为 1，无边则值为 0；在加权图中，值可以是边的权重）。
- **特点**：
  - **优点**：
    - 判断两点之间是否有边非常高效，时间复杂度为 \( O(1) \)。
    - 易于实现和理解。
  - **缺点**：
    - 占用空间多，对于稀疏图（边远少于顶点数的平方）效率不高，空间复杂度为 \( O(n^2) \)。
    - 插入和删除顶点需要重新调整矩阵。

### 2. 邻接表 (Adjacency List)
- **表示方法**：使用一个数组（或链表）来存储每个顶点的邻接顶点列表。每个顶点都有一个链表，链表中的每个节点表示一条边。
- **特点**：
  - **优点**：
    - 对于稀疏图来说，节省空间，空间复杂度为 \( O(V + E) \)，其中 \( V \) 是顶点数，\( E \) 是边数。
    - 插入和删除顶点较为简单。
  - **缺点**：
    - 判断两点之间是否有边较慢，平均时间复杂度为 \( O(V) \)。

### 3. 十字链表 (Orthogonal List)
- **表示方法**：一种用于有向图的拓扑结构表示方法。每个顶点有两个链表，一个存储以该顶点为起点的所有边，另一个存储以该顶点为终点的所有边。每个节点包含指向下一个起点相同的节点和下一个终点相同的节点的指针。
- **特点**：
  - **优点**：
    - 可以高效地查找以某个顶点为起点或终点的所有边。
    - 适用于有向图的各种操作。
  - **缺点**：
    - 结构较为复杂，难以实现和理解。
    - 相较于邻接表，空间开销稍大。

### 4. 邻接多重表 (Adjacency Multilist)
- **表示方法**：一种用于无向图的复杂链表结构。每条边用一个记录表示，每个记录有四个指针，分别指向与边连接的两个顶点的两个邻接链表中的下一个边记录。
- **特点**：
  - **优点**：
    - 可以高效地遍历和管理无向图中的所有边。
    - 对于复杂图结构的操作较为方便。
  - **缺点**：
    - 结构复杂，实现难度较大。
    - 需要额外的存储空间来存储多个指针。

### 总结
- 邻接矩阵适合稠密图，方便快速查找边。
- 邻接表适合稀疏图，节省空间，插入删除顶点和边较为灵活。
- 十字链表适用于有向图，方便查找顶点的所有出边和入边。
- 邻接多重表适用于无向图，能高效管理复杂图结构。

根据具体的应用场景和需求选择合适的表示方法，可以有效提高算法效率和代码可读性。

## 图的算法

图的基本操作包括创建、修改、查询和遍历图的结构和元素。以下是一些常见的基本操作：

### 1. 创建图
- **初始化图**：根据需要选择邻接矩阵、邻接表、十字链表或邻接多重表等数据结构，初始化图的数据结构。
- **添加顶点**：向图中添加新的顶点。
- **添加边**：向图中添加新的边，包括设置边的权重（如果是加权图）。

### 2. 修改图
- **删除顶点**：从图中删除一个顶点及其相关的所有边。
- **删除边**：从图中删除一条边。
- **更新边权**：修改图中某条边的权重。

### 3. 查询图
- **判断顶点是否存在**：检查图中是否存在某个顶点。
- **判断边是否存在**：检查图中是否存在某条边（在邻接矩阵中操作最为简单）。
- **获取顶点的度**：获取某个顶点的度（无向图）或出度、入度（有向图）。
- **获取邻接顶点**：获取某个顶点的所有邻接顶点。

### 4. 遍历图
- **深度优先搜索 (DFS)**：从某个顶点出发，沿着路径尽可能深入，直到到达终点或无路可走，再回溯并继续。
- **广度优先搜索 (BFS)**：从某个顶点出发，先访问所有相邻顶点，再逐层深入，类似于层次遍历。

### 5. 图的算法
- **最短路径算法**：
  - **Dijkstra算法**：计算从单个源点到所有其他顶点的最短路径（适用于加权有向图）。
  - **Bellman-Ford算法**：计算从单个源点到所有其他顶点的最短路径，可以处理边权为负的图。
  - **Floyd-Warshall算法**：计算所有顶点对之间的最短路径。
- **最小生成树 (MST)**：
  - **Kruskal算法**：基于边的权重进行排序，然后构建最小生成树。
  - **Prim算法**：从一个顶点开始，逐步扩展最小生成树。
- **拓扑排序**：对有向无环图 (DAG) 进行顶点排序，使得每条边 \( (u, v) \) 中的顶点 \( u \) 在 \( v \) 之前。
- **连通分量**：
  - **强连通分量**：Kosaraju算法、Tarjan算法，用于找到有向图中的强连通分量。
  - **弱连通分量**：适用于无向图，通常通过DFS或BFS实现。
- **最大流算法**：
  - **Ford-Fulkerson算法**：用于计算流网络中的最大流量。
  - **Edmonds-Karp算法**：Ford-Fulkerson算法的一种实现，使用BFS寻找增广路径。

### 6. 图的实际应用
- **路径规划**：如地图导航中的最短路径计算。
- **网络流分析**：如通信网络中的流量最大化问题。
- **社交网络分析**：如社区发现、好友推荐等。
- **调度和排序**：如任务调度中的拓扑排序。
- **图像处理**：如图像分割中的最小割算法。

这些操作和算法是图数据结构的核心，通过对这些操作和算法的理解和应用，可以解决许多实际问题。