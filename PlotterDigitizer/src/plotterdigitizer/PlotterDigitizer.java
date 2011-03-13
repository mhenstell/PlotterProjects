package plotterdigitizer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.net.ConnectException;
import java.util.Arrays;
import processing.core.PApplet;
import processing.core.PVector;
import processing.net.Client;
import geomerative.*;
import javax.swing.JFileChooser;

public class PlotterDigitizer extends PApplet {
	
	/* Alter These Settings to Taste */
	
		PVector screenSize = new PVector(750, 1000); // 1px = 1mm of Paper
	
		PlotterDriver plotter = new MegaPlotterDriver();
		PlotterDriver.plotterMode plotterMode = PlotterDriver.plotterMode.MOUSE_UP_PLOT;
		
		String plotterIP = "127.0.0.1";
		int plotterPort = 2000;
		
		boolean invertX = false;
		boolean invertY = false;
		
	/* OK Stop Altering Shit Now */
	
	
	
	RShape shape = new RShape();
	RPath currentPath = new RPath();
	
	PVector endPoint = new PVector(0,0);
	PVector startPoint = new PVector(0,0);

	public void setup() {
		size((int)screenSize.x, (int)screenSize.y);

		background(255);

		plotter.setScreenSize(screenSize);
		plotter.setMode(plotterMode);
		plotter.setConnection(plotterIP, plotterPort);
		plotter.setInverts(invertX, invertY);
		plotter.connect();
		
		RG.init(this); 
			
	}

	public void draw() {

	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { plotterdigitizer.PlotterDigitizer.class.getName() });
	}
	
	public void mouseMoved() {
		
		if (plotterMode == plotterMode.LIVE_PLOT) {
			endPoint.set((float)mouseX, (float)mouseY, (float)0);
		    plotter.livePlot(startPoint, endPoint, false);	
		    startPoint.set(endPoint.x, endPoint.y, endPoint.z);
		}
		
		startPoint.set((float)mouseX, (float)mouseY, 0);
	}
	public void mouseDragged() {
	  
	    endPoint.set((float)mouseX, (float)mouseY, (float)0);

	    line(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	    
	    currentPath.addLineTo(mouseX, mouseY);
	    
	    if (plotterMode == plotterMode.LIVE_PLOT) {
	    	plotter.livePlot(startPoint, endPoint, true);
	    } else {
	    	
	    }
	    
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

}



  


