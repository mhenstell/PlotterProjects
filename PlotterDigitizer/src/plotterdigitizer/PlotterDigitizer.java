package plotterdigitizer;

import java.net.ConnectException;
import java.util.Arrays;

import plotterdigitizer.PlotterDriver.plotterMode;
import processing.core.PApplet;
import processing.core.PVector;
import processing.net.Client;
import geomerative.*;
import controlP5.*;

public class PlotterDigitizer extends PApplet {


	PVector endPoint = new PVector(0,0);
	PVector startPoint = new PVector(0,0);
	
	PVector screenSize = new PVector(750, 1000);

	

	RShape shape = new RShape();
	RShape currentShape = new RShape();
	
	PlotterDriver.plotterMode plotterMode = PlotterDriver.plotterMode.MOUSE_UP_PLOT;
	
	boolean liveMode = false;
	
	String plotterIP = "192.168.1.110";
	int plotterPort = 2000;
	
	PlotterDriver plotter = new MegaPlotterDriver();
	
	int currentPathNum= 0;
	int currentPoint = 0;
	
	RPath currentPath = new RPath();
	
	String saveFilePath = "save.svg";
	
	boolean invertX = false;
	boolean invertY = false;
	
	
	String fontPath = "Courier New.ttf";
	int fontSize = 50;
	int fontAlign = RFont.LEFT;
	
	ControlP5 controlP5;
	
	Textfield myTextfield;
	String textValue = "";
	
	ControlWindow controlWindow;
	
	DropdownList dropList;
	
	Textlabel modeLabel;
	
	public void setup() {
		size((int)screenSize.x, (int)screenSize.y);

		
		
		
		
		controlP5 = new ControlP5(this);
		myTextfield = controlP5.addTextfield("Enter Text",120,10,200,20);
		myTextfield.setFocus(true);
		myTextfield.setAutoClear(false);
		myTextfield.keepFocus(true);

	
		plotter.setScreenSize(screenSize);
		plotter.setMode(plotterMode);
		plotter.setConnection(plotterIP, plotterPort);
		plotter.setInverts(invertX, invertY);
		plotter.connect();
		
		RG.init(this);
				  
		 dropList = controlP5.addDropdownList("Mode",10,20,100,120);
		 customize(dropList);
		 
		 //controlP5.setControlFont(new ControlFont(createFont("Georgia",20), 20));

		  modeLabel = controlP5.addTextlabel("label","A SINGLE TESTLABEL.",20,134);
		  modeLabel.setColorValue(0xffcccccc);
	}

	void customize(DropdownList ddl) {
		  ddl.setBackgroundColor(color(190));
		  ddl.setItemHeight(20);
		  ddl.setBarHeight(15);
		  ddl.captionLabel().set("pulldown");
		  ddl.captionLabel().style().marginTop = 3;
		  ddl.captionLabel().style().marginLeft = 3;
		  ddl.valueLabel().style().marginTop = 3;
		  
		  for (plotterMode mode : plotterMode.values()) {
			  ddl.addItem(mode.name(), mode.ordinal());
		  }
		  
		  ddl.setColorBackground(color(60));
		  ddl.setColorActive(color(255,128));
		}
	
	public void draw() {
		
		//background(255);
		fill(200);
		rect(0,0,width,100);
		
		modeLabel.draw(this); 
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
	
	public void drawShape(RShape shape) {
		
			
			RPoint[][] pointsArray = shape.getPointsInPaths();
			
			for (RPoint[] points : pointsArray) {
				plotter.plotPath(new RPath(points));
			}
		
	}
	
	
//	public void keyPressed() {
//		if (key == 's') {
//			RG.saveShape(saveFilePath, shape);
//		} else if (key == 'l') {
//			RShape loadedShape = RG.loadShape(saveFilePath);
//			drawShape(loadedShape);
//		} else if (key == 'f') {
//			RFont fontTest = new RFont(fontPath, fontSize, fontAlign);
//			RShape fontShape = fontTest.toShape("All your base are belong to us.");
//			fontShape.transform(0, 0, screenSize.x, fontSize, true);
//			fontShape.draw();
//			drawShape(fontShape);
//		}
//	}
	
	public void keyPressed() {
		if (key == ENTER) {
			println("Text: " + myTextfield.getText());
			myTextfield.clear();
		}
	}
	
	







	
	
}



  


