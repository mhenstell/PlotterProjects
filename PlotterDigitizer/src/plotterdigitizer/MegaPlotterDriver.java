package plotterdigitizer;

import geomerative.RPath;
import geomerative.RPoint;
import geomerative.RShape;

import java.net.ConnectException;
import processing.core.PApplet;
import processing.core.PVector;
import processing.net.Client;
import java.util.ArrayList;

public class MegaPlotterDriver implements PlotterDriver {

	private plotterMode mode;
	
	PVector plotterMin = new PVector(0,0);
	PVector plotterMax = new PVector(600,600);
	
	PVector screenSize = new PVector();
	
	ArrayList<PVector> points = new ArrayList<PVector>();
	
	private boolean penDown = true;
	private boolean penUpFirst = true;
	
	int currentSample = 1;
	int sampleNum = 3;
	
	
	public MegaPlotterDriver(PVector screenSize) {
		this.screenSize = screenSize;
		this.mode = mode.LIVE_PLOT;
	}
	
	public MegaPlotterDriver(PVector screenSize, plotterMode mode) {
		this.screenSize = screenSize;
		this.mode = mode;
	}
	
	public boolean penUp() {
		
		if (penDown) {
			penDown = false;
		}
		
		if (penUpFirst) {
			penUpFirst = false;
			
			System.out.println();
			
			return true;
		}
		
		return false;
		
	}
	
	public void livePlot(PVector startPoint, PVector endPoint, boolean penDown) {
		
		
		if (sampled()) {
			
			StringBuilder cmd = new StringBuilder();
			
			cmd.append((penDown) ? "PD\n" : "PU\n");
			
			int startX = PApplet.floor(PApplet.map(startPoint.x, 0, screenSize.x, plotterMin.x, plotterMax.x));
			int startY = PApplet.floor(PApplet.map(startPoint.y, 0, screenSize.y, plotterMin.y, plotterMax.y));
			PVector startPointMapped = new PVector(startX, startY);
			
			int endX   = PApplet.floor(PApplet.map(endPoint.x, 0, screenSize.x, plotterMin.x, plotterMax.x));
			int endY   = PApplet.floor(PApplet.map(endPoint.y, 0, screenSize.y, plotterMin.y, plotterMax.y));
			PVector endPointMapped = new PVector(endX, endY);
			
			cmd.append(PApplet.floor(endPointMapped.x) + " " + PApplet.floor(endPointMapped.y) + "\n");
			
			System.out.print("MEGA: " + cmd);
			//System.out.println("Mega: (" + startPointMapped.x + "," + startPointMapped.y + ") -> (" + endPointMapped.x + "," + endPointMapped.y + ")");
		}
		
		
	}
	
	

	public boolean sampled() {
		
		if (currentSample == sampleNum) {
			currentSample = 1;
			return true;
		}
		
		
		
		//System.out.println(currentSample);
		currentSample++;
		return false;
		
	}
	
	public void newPath(RPath path) {
		if (this.mode == mode.MOUSE_UP_PLOT) {
			RPoint[] points = path.getPoints();
			
			StringBuilder cmd = new StringBuilder("PD\n");
			
			for (RPoint point : points) {
				cmd.append("PA " + point.x + " " + point.y + "\n");
			}
			
			cmd.append("PU\n");
			
			System.out.print("MEGA: " + cmd.toString());
			
		}
	}

	@Override
	public void plotShape(RShape shape) {
		// TODO Auto-generated method stub
		
	}
	
}
