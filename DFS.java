import java.util.ArrayList;
import java.util.List;
public class DFS {
    public static void DepthFS(int node, ArrayList<ArrayList<Integer>> adj, boolean[] visited)
    {
        visited[node] = true;
        System.out.print(node+" ");
        for(int it:adj.get(node))
        {
            if(!visited[it])
            {   
                DepthFS(it, adj, visited);
            }
        }
      
    }
    public static void main(String[] args)
    {   ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        adj.add(new ArrayList<>(List.of(1, 2))); 
        adj.add(new ArrayList<>(List.of(0, 3, 4))); 
        adj.add(new ArrayList<>(List.of(0, 4))); 
        adj.add(new ArrayList<>(List.of(1))); 
        adj.add(new ArrayList<>(List.of(1, 2))); 
        int n = adj.size();
        boolean[] visited = new boolean[n];
        DepthFS(0,adj,visited);
    }
}
