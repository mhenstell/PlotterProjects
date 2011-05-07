import processing.video.*;
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

PVector screenSize = new PVector(1000, 772); 

HPPlotterDriver plotter = new HPPlotterDriver();

boolean invertX = false;
boolean invertY = true;

/* OK Stop Altering Shit Now */


RShape shape = new RShape();
RPath currentPath = new RPath();

PVector endPoint = new PVector(0, 0);
PVector startPoint = new PVector(0, 0);

int numPixels;
Capture video;

int captureCountdown = 0;
float captureTime;
boolean captureMode = false;
boolean plottingMode = false;
boolean shapeLoaded = false;
boolean shapePlotted = false;
int rectAlpha = 0;
int rectAlphaDirection = 0;
int rectAlphaStep = 10;
RShape loadedShape;

public void setup() {
  size((int)screenSize.x, (int)screenSize.y);

  background(255);

  plotter.setScreenSize(screenSize);
  plotter.setInverts(invertX, invertY);

  println(Serial.list()[0]);

  try {
    Serial serial = new Serial(this, Serial.list()[0], 9600);
    plotter.connect(serial);
  } 
  catch (Exception e) {
    println("Could not connect to plotter on " + Serial.list()[0]);
  }

  RG.init(this);

  ////size(640, 480); // Change size to 320 x 240 if too slow at 640 x 480
  strokeWeight(5);
  // Uses the default video input, see the reference if this causes an error
  video = new Capture(this, width, height, 24);
  numPixels = video.width * video.height;
  noCursor();
  smooth();
}

public void draw() {

  //  stroke(255,0,0);
  //  noFill();
  //  strokeWeight(10);
  //  rect(100,100,100,100);

  if (captureMode == true) {
    if (captureCountdown > 0) {
      captureCountdown -= millis() - captureTime;
      captureTime = millis();
      println(ceil(captureCountdown/1000) + 1);
    } 
    else {

      //PImage b = loadImage("white.jpg");
      //image(b, 0, 0);
      fill(255);
      rect(0, 0, width, height);

      saveImage();
      runScript();
      println("Captured!");

      captureMode = false;
      plottingMode = true;
    }
  } //capturemode

    if (plottingMode == true) {

    //noStroke();
    //fill(255);
    //rect(0, 500, width, 100);
    if (!shapeLoaded) {
      background(255);
      try {
        File shapeFile = new File("/Users/max/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.svg");
        loadedShape = RG.loadShape(shapeFile.getPath());
        loadedShape.transform(0, 0, width, height);
        drawShape(loadedShape);
        shapeFile.delete();
        shapeLoaded = true;
      } 
      catch (Exception e) {
        println("Not ready to load the SVG yet." + e);
      }
    } 
    else {
      if (!shapePlotted) {
        plotShape(loadedShape); 
        shapePlotted = true;
      }
    }
  } 
  else {
    if (video.available()) {
      video.read();
      image(video, 0, 0);
    }
  }
}


public void mousePressed() {
  captureTime = millis();
  captureSequence();
}

public void captureSequence() {

  captureCountdown = 3000;
  captureMode = true;
  rectAlphaDirection = 1;
}

public void saveImage() {
  PImage cp = video.get();
  video.save("capture.png");
}

public void runScript() {
  try {
    Runtime.getRuntime().exec("/Users/max/Dropbox/Projects/PlotterProjects/PlotterWebcam/test.sh");
  }
  catch(Exception e) {
    println(e);
  }
}

public void mouseReleased() {
}

//  public void keyPressed(KeyEvent e)
//  {
//    int mods = e.getModifiers();
//    String key = KeyEvent.getKeyText(e.getKeyCode());
//
//    println("Mods: " + mods + " Key: " + key);
//
//    if (mods == 2 || mods == 4) {
//      if (key.equalsIgnoreCase("s")) {
//        saveShape();
//      }
//      else if (key.equalsIgnoreCase("o")) {
//        openShape();
//      }
//      else if (key.equalsIgnoreCase("p")) {
//        plotShape();
//      }
//    }
//  }

public void drawShape(RShape inShape) {

  // Likes being converted into points and back into a shape for some reason.
  // I blame the schools.

  RPoint[][] pointsArray = inShape.getPointsInPaths();
  RShape newShape = new RShape(pointsArray);
  shape = newShape;
  shape.draw();
}
public void plotShape(RShape inshape) {

  RPoint[][] pointsArray = inshape.getPointsInPaths();

  for (RPoint[] points : pointsArray) {
    RPath path = new RPath(points);
    plotter.plotPath(path);
  }
}

