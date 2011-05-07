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

boolean shapeLoaded = false;

int plottedTime;
int countdown;
int countdownStart;
int countdownDuration = 3000;
int countdownLastTime;


//RPath currentPath = new RPath();

//PVector endPoint = new PVector(0, 0);
//PVector startPoint = new PVector(0, 0);

int numPixels;

RShape loadedShape;

AppletLayers layers;
OverLayer ol;
WebcamLayer wl;
RShape shape;


String mode = "GREETING";
//String mode = "";

public void setup() {
  shape = new RShape();
  //HPPlotterDriver plotter = new HPPlotterDriver();
  size((int)screenSize.x, (int)screenSize.y);

  background(255);

  layers = new AppletLayers(this);
  LogicLayer ll = new LogicLayer(this);
  wl = new WebcamLayer(this);
  ol = new OverLayer(this);

  layers.addLayer(ll);
  layers.addLayer(wl);
  layers.addLayer(ol);


  ol.update();

  plotter.setScreenSize(screenSize);
  plotter.setInverts(invertX, invertY);

  RG.init(this);

  println(Serial.list()[0]);

  try {
    Serial serial = new Serial(this, Serial.list()[0], 9600);
    plotter.connect(serial);
  } 
  catch (Exception e) {
    println("Could not connect to plotter on " + Serial.list()[0]);
  }


  ////size(640, 480); // Change size to 320 x 240 if too slow at 640 x 480
  strokeWeight(5);
  // Uses the default video input, see the reference if this causes an error
  video = new Capture(this, width, height, 24);
  numPixels = video.width * video.height;
  noCursor();
  smooth();
}

void draw() {
}

void reset() {
  mode = "GREETING";
  wl.setVisible(true);
}
// paint method for Processing 1.5 or higher:
void paint(java.awt.Graphics g) {
  // This method MUST be present in your sketch for layers to be rendered!
  if (layers != null) {
    layers.paint(this);
  } 
  else {
    super.paint(g);
  }
}

public void mousePressed() {
  //  captureTime = millis();
  //  captureSequence();
  if (mode == "GREETING") {
    fireTheCapture();
  }

  else if (mode == "CONFIRM") {
    mode = "PLOTTING";
    openShape();
    plotShape(shape);
  }
}

void openShape() {
  background(255);
  try {
    File shapeFile = new File("/Users/max/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.svg");
    shape = RG.loadShape(shapeFile.getPath());
    shape.transform(0, 0, width, height);
    //drawShape(loadedShape);
    //shapeFile.delete();
    println("Shape Loaded.");
  } 
  catch (Exception e) {
    println("Not ready to load the SVG yet." + e);
  }
}

public void fireTheCapture() {

  mode = "COUNTDOWN";
  countdownStart = millis();
  countdownLastTime = millis();
  ol.update();
  wl.setVisible(true);
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
void keyPressed() {
  if (key==27) {
    key = 0;
    reset();
    println("ESCAPE");
    ol.update();
  }
}

public void plotShape(RShape inshape) {
  // try {
  if (inshape != null) {
    RPoint[][] pointsArray = inshape.getPointsInPaths();

    for (RPoint[] points : pointsArray) {
      RPath path = new RPath(points);
      plotter.plotPath(path);
    }
  }
  // } 
  //  catch (Exception e) {
  //    println("Error plotting: " + e);
  //    reset();
  //  }
}

public void drawShape(RShape inShape) {

  // Likes being converted into points and back into a shape for some reason.
  // I blame the schools.

  RPoint[][] pointsArray = inShape.getPointsInPaths();
  RShape newShape = new RShape(pointsArray);
  shape = newShape;
  shape.draw();
}

