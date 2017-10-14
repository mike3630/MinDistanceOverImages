import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GetPixelColor
{
  public static void main(String args[]) throws IOException{
  File file= new File("pic.png");
  BufferedImage image = ImageIO.read(file);
  
  // picture width/height 
  int width = image.getWidth();
  int height = image.getHeight();
  int[][] colorArr = new int[width][height];
  
  for (int i = 0; i < width; i++){
    for (int j = 0; j < height; j++){
      colorArr[i][j] =  image.getRGB(i,j); 
    }
  }
  
  int  red   = (colorArr[0][0] & 0x00ff0000) >> 16;
  int  green = (colorArr[0][0] & 0x0000ff00) >> 8;
  int  blue  =  colorArr[0][0] & 0x000000ff;
  System.out.println("Red Color value = "+ red);
  System.out.println("Green Color value = "+ green);
  System.out.println("Blue Color value = "+ blue);
  
  System.out.println("dist: " + distance(colorArr[0][0], colorArr[width-1][0]));
  
  }
  
  public static double distance(int pix1, int pix2){
    int g = 1;
    System.out.println("colordist: " + colordist(pix1,pix2));
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

