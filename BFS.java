import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
public class BFS {
    public static void BreadthFS(ArrayList<ArrayList<Integer>> adj)
    {
      Queue<Integer> q = new LinkedList<Integer>();
      int n = adj.size();
      boolean[] visited = new boolean[n];
      ArrayList<Integer> bfs = new ArrayList<Integer>();
      q.add(0);
      visited[0] = true;
      while(!q.isEmpty())
      { int node = q.remove();
        bfs.add(node);
        for(int it:adj.get(node))
        {
          if(!visited[it])
          {
            q.add(it);
            visited[it] = true;
          }
        }
      }
      System.out.println(bfs);
    }
    public static void main(String[] args)
    {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        adj.add(new ArrayList<>(List.of(1, 2))); 
        adj.add(new ArrayList<>(List.of(0, 3, 4))); 
        adj.add(new ArrayList<>(List.of(0, 4))); 
        adj.add(new ArrayList<>(List.of(1))); 
        adj.add(new ArrayList<>(List.of(1, 2))); 
        BreadthFS(adj);
    } 
}
