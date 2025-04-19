import java.util.*;

public class UnweightedGraph<V> implements Graph<V> {
  protected List<V> vertices = new ArrayList<>();
  protected List<List<Edge>> neighbors = new ArrayList<>();

  public UnweightedGraph() {}

  public UnweightedGraph(V[] vertices, int[][] edges) {
    for (V vertex : vertices) {
      addVertex(vertex);
    }

    for (int[] edge : edges) {
      addEdge(edge[0], edge[1]);
    }
  }

  @Override
  public int getSize() {
    return vertices.size();
  }

  @Override
  public List<V> getVertices() {
    return vertices;
  }

  @Override
  public V getVertex(int index) {
    return vertices.get(index);
  }

  @Override
  public int getIndex(V v) {
    return vertices.indexOf(v);
  }

  @Override
  public List<Integer> getNeighbors(int index) {
    List<Integer> result = new ArrayList<>();
    for (Edge e : neighbors.get(index)) {
      result.add(e.v);
    }
    return result;
  }

  @Override
  public int getDegree(int v) {
    return neighbors.get(v).size();
  }

  @Override
  public void printEdges() {
    for (int u = 0; u < neighbors.size(); u++) {
      System.out.print(getVertex(u) + " (" + u + "): ");
      for (Edge e : neighbors.get(u)) {
        System.out.print("(" + getVertex(e.u) + ", " + getVertex(e.v) + ") ");
      }
      System.out.println();
    }
  }

  @Override
  public void clear() {
    vertices.clear();
    neighbors.clear();
  }

  @Override
  public boolean addVertex(V vertex) {
    if (!vertices.contains(vertex)) {
      vertices.add(vertex);
      neighbors.add(new ArrayList<>());
      return true;
    }
    return false;
  }

  @Override
  public boolean addEdge(int u, int v) {
    return addEdge(new Edge(u, v));
  }

  @Override
  public boolean addEdge(Edge e) {
    if (!neighbors.get(e.u).contains(e)) {
      neighbors.get(e.u).add(e);
    }
    return true;
  }

  @Override
  public boolean remove(V v) {
    int index = getIndex(v);
    if (index != -1) {
      vertices.remove(index);
      neighbors.remove(index);
      for (List<Edge> list : neighbors) {
        list.removeIf(edge -> edge.v == index);
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(int u, int v) {
    return neighbors.get(u).remove(new Edge(u, v));
  }

  @Override
  public SearchTree dfs(int v) {
    List<Integer> searchOrder = new ArrayList<>();
    int[] parent = new int[vertices.size()];
    Arrays.fill(parent, -1);
    boolean[] isVisited = new boolean[vertices.size()];

    dfs(v, parent, searchOrder, isVisited);

    return new SearchTree(v, parent, searchOrder);
  }

  private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
    searchOrder.add(v);
    isVisited[v] = true;

    for (Edge e : neighbors.get(v)) {
      if (!isVisited[e.v]) {
        parent[e.v] = v;
        dfs(e.v, parent, searchOrder, isVisited);
      }
    }
  }

  @Override
  public SearchTree bfs(int v) {
    List<Integer> searchOrder = new ArrayList<>();
    int[] parent = new int[vertices.size()];
    Arrays.fill(parent, -1);
    boolean[] isVisited = new boolean[vertices.size()];
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(v);
    isVisited[v] = true;

    while (!queue.isEmpty()) {
      int u = queue.poll();
      searchOrder.add(u);
      for (Edge e : neighbors.get(u)) {
        if (!isVisited[e.v]) {
          queue.offer(e.v);
          parent[e.v] = u;
          isVisited[e.v] = true;
        }
      }
    }

    return new SearchTree(v, parent, searchOrder);
  }

  public class SearchTree {
    private int root;
    private int[] parent;
    private List<Integer> searchOrder;

    public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
      this.root = root;
      this.parent = parent;
      this.searchOrder = searchOrder;
    }

    public int getRoot() {
      return root;
    }

    public int getParent(int v) {
      return parent[v];
    }

    public List<Integer> getSearchOrder() {
      return searchOrder;
    }

    public int getNumberOfVerticesFound() {
      return searchOrder.size();
    }
  }
}