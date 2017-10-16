public class DijkstraAlgorithm
{
  public static void main(String args[]){

  }
  
  public static double[] DijkstraDecreaseKeyUpdate(int[][] graph, int source){
    
    double [] dist = new double[graph.length];
    double [] prev = new double[graph.length];
    
    int [] removed = new int[graph.length];
      
    FibonacciHeap<Integer> q = new FibonacciHeap<Integer>();
    
    for (int i = 0; i < graph.length; i++){
      dist[i] = Double.POSITIVE_INFINITY;
      prev[i] = -1;
      q.enqueue(i,dist[i]);
    }
    
    dist[source] = 0;
    
    while(!q.isEmpty()){
      int u = q.dequeueMin().getValue();
      for (int v = 0; v < graph.length; v++){
        if (graph[u][v] == 1){
          double alt = dist[u] + GetPixelColor.distance(u,v);
          if (alt < dist[v]){
            dist[v] = alt;
            prev[v] = u;
            FibonacciHeap.Entry<Integer> result = new FibonacciHeap.Entry<Integer>(v, dist[v]);
            q.decreaseKey(result);
          }
        }
      }    
    }
   
    return dist;
  }
  
  
  public static int[] DijkstraOtherUpdate(int[][] graph, int source){
    int[] val = new int[0];
   
    return val;
  }
}

