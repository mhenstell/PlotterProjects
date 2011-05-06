import geomerative.*;
import org.apache.batik.svggen.font.table.*;
import org.apache.batik.svggen.font.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.ConnectException;
import java.util.Arrays;
import processing.net.Client;
import javax.swing.JFileChooser;
import processing.serial.*;

  /* Alter These Settings to Taste */

  PVector screenSize = new PVector(750, 1000); 

  MegaPlotterDriver plotter = new MegaPlotterDriver();
  //HPPlotterDriver plotter = new HPPlotterDriver();

  ///String plotterIP = "127.0.0.1";
  //int plotterPort = 2000;

  boolean invertX = false;
  boolean invertY = true;

  /* OK Stop Altering Shit Now */


  RShape shape = new RShape();
  RPath currentPath = new RPath();

  PVector endPoint = new PVector(0, 0);
  PVector startPoint = new PVector(0, 0);
  
  public void setup() {
    size((int)screenSize.x, (int)screenSize.y);

    background(255);

    plotter.setScreenSize(screenSize);
    plotter.setInverts(invertX, invertY);
    
    println(Serial.list()[0]);
    plotter.connect(this);    

    RG.init(this);
    

  }

  public void draw() {
  }


  public void mouseMoved() {

    startPoint.set((float)mouseX, (float)mouseY, 0);
  }
  public void mouseDragged() {

    endPoint.set((float)mouseX, (float)mouseY, (float)0);

    line(startPoint.x, startPoint.y, endPoint.x, endPoint.y);

    currentPath.addLineTo(mouseX, mouseY);

    startPoint.set(endPoint.x, endPoint.y, endPoint.z);
  }

  public void mousePressed() {
    currentPath = new RPath(mouseX, mouseY);
    currentPath.addLineTo(mouseX, mouseY);
  }
  public void mouseReleased() {
    shape.addPath(currentPath);
    plotter.plotPath(currentPath);
  }

  public void keyPressed(KeyEvent e)
  {
    int mods = e.getModifiers();
    String key = KeyEvent.getKeyText(e.getKeyCode());

    //println("Mods: " + mods + " Key: " + key);

    if (mods == 2 || mods == 4) {
      if (key.equalsIgnoreCase("s")) {
        saveShape();
      }
      else if (key.equalsIgnoreCase("o")) {
        openShape();
      }
      else if (key.equalsIgnoreCase("p")) {
        plotShape();
      }
    }
  }

  public void drawShape(RShape inShape) {

    // Likes being converted into points and back into a shape for some reason.
    // I blame the schools.

    RPoint[][] pointsArray = inShape.getPointsInPaths();
    RShape newShape = new RShape(pointsArray);
    shape = newShape;
    shape.draw();
  }
  public void plotShape() {

    RPoint[][] pointsArray = shape.getPointsInPaths();

    for (RPoint[] points : pointsArray) {
      RPath path = new RPath(points);
      plotter.plotPath(path);
    }
  }
  public void openShape() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(chooser.getAcceptAllFileFilter());

    int returnVal = chooser.showOpenDialog(null);

    if (returnVal == JFileChooser.APPROVE_OPTION) 
    {
      println("Opening file: " + chooser.getSelectedFile().getName());

      RShape loadedShape = RG.loadShape(chooser.getSelectedFile().getAbsolutePath());
      drawShape(loadedShape);
    }
  }
  public void saveShape() {

    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(chooser.getAcceptAllFileFilter());
    chooser.setSelectedFile(new File("save.svg"));
    int returnVal = chooser.showSaveDialog(null);

    if (returnVal == JFileChooser.APPROVE_OPTION) 
    {
      println("Saving file: " + chooser.getSelectedFile().getName());

      RG.saveShape(chooser.getSelectedFile().getAbsolutePath(), shape);
    }
  }







