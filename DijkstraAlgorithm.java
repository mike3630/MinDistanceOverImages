import java.util.ArrayList;
import java.util.Arrays;

public class DijkstraAlgorithm
{
  public static void main(String args[]){

  }
  
  public static double[] DijkstraDecreaseKeyUpdate(ArrayList<ArrayList<Integer>> graph, int source, int[] pixels){
    
    double [] dist = new double[graph.size()];
    int [] prev = new int[graph.size()];
    boolean [] removed = new boolean[graph.size()];
    ArrayList<FibonacciHeap.Entry<Integer>> entries = new ArrayList<FibonacciHeap.Entry<Integer>>();
      
    FibonacciHeap<Integer> q = new FibonacciHeap<Integer>();
    
    for (int i = 0; i < graph.size(); i++){
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
      for (int i = 0; i < graph.get(u).size(); i++){
        int v = graph.get(u).get(i);
        if (!removed[v]){
          double alt = dist[u] + Main.distance(pixels[u],pixels[v]);
          if (alt < dist[v]){
            dist[v] = alt;
            prev[v] = u;
            q.decreaseKey(entries.get(v), dist[v]);
          }
        }
      } 
    }
   
    return dist;
  }
  
  
  public static double[] DijkstraOtherUpdate(ArrayList<ArrayList<Integer>> graph, int source, int[] pixels){
    double [] dist = new double[graph.size()];
    int [] prev = new int[graph.size()];
    boolean [] removed = new boolean[graph.size()];
    ArrayList<FibonacciHeap.Entry<Integer>> entries = new ArrayList<FibonacciHeap.Entry<Integer>>();
      
    FibonacciHeap<Integer> q = new FibonacciHeap<Integer>();
    
    for (int i = 0; i < graph.size(); i++){
      dist[i] = Double.POSITIVE_INFINITY;
      prev[i] = -1;
      removed[i] = false;
      
      if (i == source){
        dist[source] = 0;
      }
      
      entries.add(q.enqueue(i,dist[i]));
    }
    
    while(!q.isEmpty()){
      FibonacciHeap.Entry<Integer> a = q.dequeueMin();
      int u = a.getValue();
      double distu = a.getPriority();
      if (dist[u] < distu && dist[u] != 0){
        continue;
      }
      removed[u] = true;
      for (int i = 0; i < graph.get(u).size(); i++){
        int v = graph.get(u).get(i);
        if (!removed[v]){
          double alt = dist[u] + Main.distance(pixels[u],pixels[v]);
          if (alt < dist[v]){
            dist[v] = alt;
            prev[v] = u;
            q.enqueue(v, dist[v]);
          }
        }
      } 
    }
   
    return dist;
  }
}
  

