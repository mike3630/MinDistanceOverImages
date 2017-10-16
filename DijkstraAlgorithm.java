import java.util.ArrayList;
import java.util.Arrays;

public class DijkstraAlgorithm
{
  public static void main(String args[]){

  }
  
  public static double[] DijkstraDecreaseKeyUpdate(int[][] graph, int source, int[] pixels){
    
    double [] dist = new double[graph.length];
    int [] prev = new int[graph.length];
    boolean [] removed = new boolean[graph.length];
    ArrayList<FibonacciHeap.Entry<Integer>> entries = new ArrayList<FibonacciHeap.Entry<Integer>>();
      
    FibonacciHeap<Integer> q = new FibonacciHeap<Integer>();
    
    for (int i = 0; i < graph.length; i++){
      dist[i] = Double.POSITIVE_INFINITY;
      prev[i] = -1;
      removed[i] = false;
      
      if (i == source){
        dist[source] = 0;
      }
      
      entries.add(q.enqueue(i,dist[i]));
    }
    
    while(!q.isEmpty()){
      int u = q.dequeueMin().getValue();
      removed[u] = true;
      for (int v = 0; v < graph.length; v++){
        if (graph[u][v] == 1 && !removed[v]){
          double alt = dist[u] + Main.distance(pixels[u],pixels[v]);
          if (alt < dist[v]){
            dist[v] = alt;
            prev[v] = u;
            q.decreaseKey(entries.get(v), alt);
          }
        }
      } 
    }
   
    return dist;
  }
  
  
  public static int[] DijkstraOtherUpdate(int[][] graph, int source, int[] pixels){
    int[] val = new int[0];
   
    return val;
  }
}

