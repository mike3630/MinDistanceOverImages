import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.ArrayList;

public class Main
{
  public static void main(String args[]) throws IOException{
  File file= new File("test2.png");
  BufferedImage image = ImageIO.read(file);
  
  // picture width/height 
  int width = image.getWidth();
  int height = image.getHeight();
  
  // each pixel is a "vertex"
  int[] pixels = new int[width * height];
  
  //TODO make an adjacency list....
  //int[][] graph = new int[width*height][width*height];
  ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
  
  //TODO remove colorArr
  int[][] colorArr = new int[width][height];
  
  // create colour vertices
  for (int i = 0; i < width; i++){
    for (int j = 0; j < height; j++){
      colorArr[i][j] =  image.getRGB(i,j);
      pixels[i*height + j] = image.getRGB(i,j);
      graph.add(new ArrayList<Integer>());
    }
  }
  
  // create graph with edges
  for (int i = 0; i < width * height; i++){
    if (i % height != 0){
      graph.get(i).add(i-1);
      //graph.get(i-1).add(i);
      //graph[i][i-1] = 1;
      //graph[i-1][i] = 1;
    }
    
    if ((i + 1) % height != 0){
      graph.get(i).add(i+1);
      //graph.get(i+1).add(i);
      //graph[i][i+1] = 1;
      //graph[i+1][i] = 1;
    }
    
    if (i - height >=0){
      graph.get(i).add(i-height);
      //graph.get(i-height).add(i);
      //graph[i][i-height] = 1;
      //graph[i-height][i] = 1;
    }
    
    if (i + height < width*height){
      graph.get(i).add(i+height);
      //graph.get(i+height).add(i);
      //graph[i][i+height] = 1;
      //graph[i+height][i] = 1;
    }
  }
  for (int i = 0; i < graph.size(); i++){
  //System.out.println(graph.get(i));
  }
  long start = System.currentTimeMillis();
  double[] distances1 = DijkstraAlgorithm.DijkstraDecreaseKeyUpdate(graph, 0, pixels);
  System.out.println("Done. That took " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
  start = System.currentTimeMillis();
  double[] distances2 = DijkstraAlgorithm.DijkstraOtherUpdate(graph, 0, pixels);
  System.out.println("Done. That took " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
  
  for (int i=0; i < distances1.length; i++){
    if (distances1[i] != distances2[i]){
      System.out.println("no work " + i);
      break;
    }
  }
  //System.out.println("dist1: " + Arrays.toString(distances1));
  //System.out.println("dist2: " + Arrays.toString(distances2));
  
  //TODO Cleanup
  /*int  red   = (pixels[1] & 0x00ff0000) >> 16;
  int  green = (pixels[1] & 0x0000ff00) >> 8;
  int  blue  =  pixels[1] & 0x000000ff;
  System.out.println("Red Color value = "+ red);
  System.out.println("Green Color value = "+ green);
  System.out.println("Blue Color value = "+ blue);
  
  System.out.println("dist: " + distance(pixels[0], pixels[1]));*/
  
  }
  
  public static double distance(int pix1, int pix2){
    int g = 1;
    //System.out.println("colordist: " + colordist(pix1,pix2));
    return Math.sqrt(1 + g * colordist(pix1, pix2));
  }
  
  public static double colordist(int pix1, int pix2){
    int  red1   = (pix1 & 0x00ff0000) >> 16;
    int  green1 = (pix1 & 0x0000ff00) >> 8;
    int  blue1  =  pix1 & 0x000000ff;
    
    int  red2   = (pix2 & 0x00ff0000) >> 16;
    int  green2 = (pix2 & 0x0000ff00) >> 8;
    int  blue2  =  pix2 & 0x000000ff;
    
    return Math.sqrt(Math.pow(red2-red1,2) + 
                     Math.pow(green2-green1,2) + 
                     Math.pow(blue2-blue1,2));
  }
}

