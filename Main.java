import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.ArrayList;

public class Main
{
  public static void main(String args[]) throws IOException{
    
    String[] inFiles = {"test1.png", "test2.png", "test3.png", "test4.png",
      "test5.png", "test6.png", "test7.png", "test8.png", "test9.png", "test10.png", "test11.png"};
    
    String[] tlFiles = {"TL1.png", "TL2.png", "TL3.png", "TL4.png",
      "TL5.png", "TL6.png", "TL7.png", "TL8.png", "TL9.png", "TL10.png", "TL11.png"};
    
    String[] cFiles = {"C1.png", "C2.png", "C3.png", "C4.png",
      "C5.png", "C6.png", "C7.png", "C8.png", "C9.png", "C10.png", "C11.png"};
    
    // test from top right corner as source
    for (int i = 0; i < inFiles.length; i++){
      System.out.println("----------Test " + (i + 1) + " ----------");
      dijkstraComparison(inFiles[i], tlFiles[i], "TOP RIGHT");
      System.out.println("----------End Test " + (i + 1) + " ----------");
      System.out.println();
    }
    
    // test from center of image as source
    for (int i = 0; i < inFiles.length; i++){
      System.out.println("----------Test " + (i + 1) + " ----------");
      dijkstraComparison(inFiles[i], cFiles[i], "CENTER");
      System.out.println("----------End Test " + (i + 1) + " ----------");
      System.out.println();
    }
  }
  
  //TODO random locations as source
  
  public static void dijkstraComparison(String inFile, String outFile, String source) throws IOException{
    File file= new File(inFile);
    BufferedImage image = ImageIO.read(file);
    
    // picture width/height 
    int width = image.getWidth();
    int height = image.getHeight();
    
    // each pixel is a "vertex"
    int[] pixels = new int[width * height];
    int[] greyScale = new int[width * height];
    
    int index = 0;
    
    if (source == "TOP LEFT"){
      index = 0;
    } else if (source == "CENTER"){
      index = (width * height)/2 + height/2;
    }
    
    ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    
    // create colour vertices
    for (int i = 0; i < width; i++){
      for (int j = 0; j < height; j++){
        pixels[i*height + j] = image.getRGB(i,j);
        graph.add(new ArrayList<Integer>());
      }
    }
    
    // create graph with edges
    for (int i = 0; i < width * height; i++){
      if (i % height != 0){
        graph.get(i).add(i-1);
      }
      
      if ((i + 1) % height != 0){
        graph.get(i).add(i+1);
      }
      
      if (i - height >=0){
        graph.get(i).add(i-height);
      }
      
      if (i + height < width*height){
        graph.get(i).add(i+height);
      }
    }

    long start = System.currentTimeMillis();
    double[] distances1 = DijkstraAlgorithm.DijkstraDecreaseKeyUpdate(graph, index, pixels);
    System.out.println("Decrease-Key done. That took " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
    start = System.currentTimeMillis();
    double[] distances2 = DijkstraAlgorithm.DijkstraOtherUpdate(graph, index, pixels);
    System.out.println("Re-Enqueue done.   That took " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
    
    double max = 0;
    for (int i=0; i < distances1.length; i++){
      if (distances1[i] != distances2[i]){
        System.out.println("no work " + i);
        break;
      }
      if (distances1[i] > max){
        max = distances1[i];
      }
    }
    
    int greyScaleMod = (int)(max/255) + 1;
          
    for (int i = 0; i < distances1.length; i++){
      int greyScaleVal = 255 - ((int) (distances1[i]/greyScaleMod));
      greyScale[i] = (255 & 0x000000ff) << 24;
      greyScale[i] += (greyScaleVal & 0x000000ff) << 16;
      greyScale[i] += (greyScaleVal & 0x000000ff) << 8;
      greyScale[i] += (greyScaleVal & 0x000000ff);
    }
    
    for (int i = 0; i < width; i++){
      for (int j = 0; j < height; j++){
        image.setRGB(i,j, greyScale[i*height + j]);
      }
    }
    
    ImageIO.write(image, "png", new File(outFile));
    
    //System.out.println("dist1: " + Arrays.toString(distances1));
    //System.out.println("dist2: " + Arrays.toString(distances2));
    
  }
  
  public static double distance(int pix1, int pix2){
    int g = 10;
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

