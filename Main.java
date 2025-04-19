public class Main {
  public static void main(String[] args) {
    String[] vertices = {
      "Business & Technology",        
      "Technology Learning Center",   
      "Student Services",             
      "Liberal Arts",                 
      "Theatre",                      
      "Health Careers & Sciences",    
      "Health Technologies Center",   
      "Recreation Center",            
      "Advanced Automotive Technology Center"
    };

    int[][] edges = {
      {0, 1}, {0, 2}, {0, 4},
      {1, 2}, {1, 7},
      {2, 3}, {2, 5},
      {3, 4},
      {5, 6},
      {6, 7},
      {7, 8}
    };

    Graph<String> graph = new UnweightedGraph<>(vertices, edges);

    UnweightedGraph<String>.SearchTree dfs = graph.dfs(graph.getIndex("Business & Technology"));

    java.util.List<Integer> searchOrders = dfs.getSearchOrder();
    System.out.println(dfs.getNumberOfVerticesFound() + " vertices are searched in this DFS order:");
    for (int index : searchOrders) {
      System.out.print(graph.getVertex(index) + " ");
    }
    System.out.println("\n");

    for (int index : searchOrders) {
      int parent = dfs.getParent(index);
      if (parent != -1) {
        System.out.println("parent of " + graph.getVertex(index) +
                           " is " + graph.getVertex(parent));
      }
    }
  }
}