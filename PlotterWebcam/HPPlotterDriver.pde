import geomerative.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import processing.net.Client;
import java.util.ArrayList;

public class HPPlotterDriver {

  PVector plotterMin = new PVector(0, 0);
  PVector plotterMax = new PVector(10000, 8000);  

  PVector screenSize = new PVector();
  float screenRatio;

  private boolean penDown = true;
  private boolean penUpFirst = true;

  int currentSample = 1;
  int sampleNum = 3;
  
  PrintWriter out;

  private boolean invertX;
  private boolean invertY;
  
  Serial serial;
  
  public void connect(Serial serial) {
   
    

     
  }

  public void setScreenSize(PVector screenSize) {
    this.screenSize = screenSize;
    screenRatio = screenSize.x / screenSize.y;

    if (plotterMax.y == 0) {
      plotterMax.y = plotterMax.x / screenRatio;
    }
  }

  public void setInverts(boolean invertX, boolean invertY) {
    this.invertX = invertX;
    this.invertY = invertY;
  }

  public boolean penUp() {

    if (penDown) {
      penDown = false;
    }

    if (penUpFirst) {
      penUpFirst = false;
      return true;
    }

    return false;
  }


  public boolean sampled() {
    if (currentSample == sampleNum) {
      currentSample = 1;
      return true;
    }

    currentSample++;
    return false;
  }

  public void plotPath(RPath path) {

      RPoint[] points = path.getPoints();
      points = scalePoints(points);
      
      StringBuilder cmd = new StringBuilder();
      //ArrayList<String> cmdList = new ArrayList<String>();
      cmd.append("PU" + floor(points[0].x) + "," + floor(points[0].y) + ";\n");
      cmd.append("PD");
      int index = 0;
      
      for (RPoint point : points) {
         if (index > 0) {
           cmd.append(","); 
         }
        index++;
        cmd.append(floor(point.x) + "," + floor(point.y));

      }

      //cmd.add("PU");

      //System.out.println("HP: " + cmd.toString());
      
      cmd.append(";\n");
      sendCommands(cmd.toString());
    
  }

  private void sendCommands(String commands) {		
    
      println(commands);
      serial.write(commands);
    
    
  }

  private RPoint[] scalePoints(RPoint[] points) {

    for (RPoint point : points) {

      if (!invertX) {
        point.x = PApplet.floor(PApplet.map(point.x, 0, screenSize.x, plotterMin.x, plotterMax.x));
      } 
      else {
        point.x = PApplet.floor(PApplet.map(point.x, 0, screenSize.x, plotterMax.x, plotterMin.x));
      }

      if (!invertY) {
        point.y = PApplet.floor(PApplet.map(point.y, 0, screenSize.y, plotterMin.y, plotterMax.y));
      } 
      else {
        point.y = PApplet.floor(PApplet.map(point.y, 0, screenSize.y, plotterMax.y, plotterMin.y));
      }
    }

    return points;
  }

  public void home() {

    System.out.println("HOMEING");
    //out.println("HOME");
  }
}

