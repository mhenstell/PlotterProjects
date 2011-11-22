import geomerative.*;
import org.apache.batik.svggen.font.table.*;
import org.apache.batik.svggen.font.*;
import javax.swing.JFileChooser;
import java.awt.event.KeyEvent;

RShape shape;

void setup() {
  shape = new RShape();
  RG.init(this);
}

void draw() {
  
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

    }
  }
void openShape() {
  background(255);
  try {
    File shapeFile = new File("/Users/max/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.svg");
   // File logoFile = new File("/Users/max/Dropbox/Projects/PlotterProjects/PlotterWebcam/NYCR_logo.svg");
    shape = RG.loadShape(shapeFile.getPath());
    //logo = RG.loadShape(logoFile.getPath());
    //logo.transform(width+500, height+400, 100, 100);
    shape.transform(0, 0, width, height);
    drawShape(shape);
    //shapeFile.delete();
    println("Shape Loaded.");
  } 
  catch (Exception e) {
    println("Not ready to load the SVG yet." + e);
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
  public void drawShape(RShape inShape) {

    // Likes being converted into points and back into a shape for some reason.
    // I blame the schools.

    RPoint[][] pointsArray = inShape.getPointsInPaths();
    RShape newShape = new RShape(pointsArray);
    shape = newShape;
    shape.draw();
  }
