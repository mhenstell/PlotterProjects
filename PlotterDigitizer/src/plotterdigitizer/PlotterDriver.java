package plotterdigitizer;

import geomerative.RPath;
import geomerative.RShape;

import java.util.ArrayList;

import processing.core.PVector;

public interface PlotterDriver {
	enum plotterMode {LIVE_PLOT, MOUSE_UP_PLOT, TRIGGER_PLOT};
	
	PVector plotterMin = new PVector();
	PVector plotterMax = new PVector();
	
	ArrayList<PVector> points = new ArrayList<PVector>();
	
	boolean penDown = true;
	boolean penUpFirst = true;
	
	public void livePlot(PVector start, PVector end, boolean penDown);
	public boolean penUp();
	public void plotShape(RShape shape);
	public void newPath(RPath currentPath);
}
