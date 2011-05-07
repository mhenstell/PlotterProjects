import com.nootropic.processing.layers.*;
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

int countdown;
int countdownStart;

//RShape shape = new RShape();
//RPath currentPath = new RPath();

//PVector endPoint = new PVector(0, 0);
//PVector startPoint = new PVector(0, 0);

int numPixels;

RShape loadedShape;

AppletLayers layers;

String mode = "GREETING";

public void setup() {
  size((int)screenSize.x, (int)screenSize.y);

  background(255);
  
  layers = new AppletLayers(this);
  LogicLayer ll = new LogicLayer(this);
  WebcamLayer wl = new WebcamLayer(this);
  OverLayer ol = new OverLayer(this);
  
  ol.clipX = 0;
  ol.clipY = height - 100;
  ol.clipWidth = width;
  ol.clipHeight = 100;
  
  layers.addLayer(wl);
  layers.addLayer(ol);
  
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

  
}

// paint method for Processing 1.5 or higher:
void paint(java.awt.Graphics g) {
  // This method MUST be present in your sketch for layers to be rendered!
  if (layers != null) {
    layers.paint(this);
  } else {
    super.paint(g);
  }
}

public void mousePressed() {
//  captureTime = millis();
//  captureSequence();

  fireTheCapture();
}

public void fireTheCapture() {

  mode = "COUNTDOWN";
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

//public void drawShape(RShape inShape) {
//
//  // Likes being converted into points and back into a shape for some reason.
//  // I blame the schools.
//
//  RPoint[][] pointsArray = inShape.getPointsInPaths();
//  RShape newShape = new RShape(pointsArray);
//  shape = newShape;
//  shape.draw();
//}
public void plotShape(RShape inshape) {

  RPoint[][] pointsArray = inshape.getPointsInPaths();

  for (RPoint[] points : pointsArray) {
    RPath path = new RPath(points);
    plotter.plotPath(path);
  }
}

