package plotterdigitizer;

import java.net.ConnectException;
import java.util.Arrays;

import plotterdigitizer.PlotterDriver.plotterMode;
import processing.core.PApplet;
import processing.core.PVector;
import processing.net.Client;
import geomerative.*;

public class PlotterDigitizer extends PApplet {


	PVector endPoint = new PVector(0,0);
	PVector startPoint = new PVector(0,0);
	
	PVector screenSize = new PVector(800, 600);

	

	RShape shape = new RShape();
	RShape currentShape = new RShape();
	
	PlotterDriver.plotterMode plotterMode = PlotterDriver.plotterMode.MOUSE_UP_PLOT;
	
	boolean liveMode = false;
	
	String plotterIP = "127.0.0.1";
	int plotterPort = 2000;
	
	PlotterDriver plotter = new MegaPlotterDriver();
	
	int currentPathNum= 0;
	int currentPoint = 0;
	
	RPath currentPath = new RPath();
	
	String saveFilePath = "save.svg";
	
	boolean invertX = false;
	boolean invertY = false;
	
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
		
		if (liveMode) {
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
	    
	    
	    if (liveMode) {
	    	plotter.livePlot(startPoint, endPoint, true);
	    } else {
	    	
	    }
	    
	    startPoint.set(endPoint.x, endPoint.y, endPoint.z);

	  

	   currentPoint++;
	  
	}
	
	public void mousePressed() {
		
		currentPath = new RPath(mouseX, mouseY);
		currentPath.addLineTo(mouseX, mouseY);

		
	}
	
	public void mouseReleased() {
		
		
			shape.addPath(currentPath);
			plotter.plotPath(currentPath);

		
	}
	
	public void keyPressed() {
		if (key == 's') {
			RG.saveShape(saveFilePath, shape);
		}
	}
	
	
	
	

}



  


