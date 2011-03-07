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

	Client myClient;

	RShape shape = new RShape();
	RShape currentShape = new RShape();
	
	
	boolean liveMode = true;
	
	String plotterIP = "127.0.0.1";
	int plotterPort = 2000;
	
	PlotterDriver plotter = new MegaPlotterDriver(screenSize, plotterMode.MOUSE_UP_PLOT);
	
	int currentPathNum= 0;
	int currentPoint = 0;
	
	RPath currentPath = new RPath();
	
	public void setup() {
		size((int)screenSize.x, (int)screenSize.y);
		
		myClient = new Client(this, plotterIP, plotterPort);
		if (myClient.available() == 0) {
			System.out.println("Could not connect to RepG on " + plotterIP + ":" + plotterPort);
//			System.exit(0);
		}
		
		background(255);
		
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
		

		
	}
	
	public void mouseReleased() {
		
		
		shape.addPath(currentPath);
		
		plotter.newPath(currentPath);
		
		
		
	}
	
	
	
	public void keyPressed() {
		if (key == 'q') {

			
		}
	}

}



  


