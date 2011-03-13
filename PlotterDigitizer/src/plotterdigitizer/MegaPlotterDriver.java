package plotterdigitizer;

import geomerative.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import processing.core.PApplet;
import processing.core.PVector;
import processing.net.Client;
import java.util.ArrayList;

public class MegaPlotterDriver implements PlotterDriver {

	PVector plotterMin = new PVector(0,0); 		// Minimum plotter points - (0,0) should be bottom left
	PVector plotterMax = new PVector(1200,0);   // Maximum plotter points - Top right. 1200 (mm) Max X for the Red Sail Plotter
	
	PVector screenSize = new PVector();
	float screenRatio;
	
	private plotterMode mode;
	
	private boolean penDown = true;
	private boolean penUpFirst = true;
	
	int currentSample = 1;
	int sampleNum = 3;
	
	Socket plotterSocket = null;
	PrintWriter out;

	String plotterIP;
	int plotterPort;

	private boolean invertX;
	private boolean invertY;
	
	public MegaPlotterDriver() {

	}
	public void connect() {
		
		if (plotterSocket == null) {
			System.out.println("Connecting to " + plotterIP + ":" + plotterPort);
			
			try {
				plotterSocket = new Socket(plotterIP, plotterPort);
				out = new PrintWriter(plotterSocket.getOutputStream(), true);
				System.out.println(plotterSocket.isConnected());
				
				out.println("LE");		// Go Go Gadget Lights!
				out.println("HOME");	// Go Go Gadget Homing!
				
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void setScreenSize(PVector screenSize) {
		this.screenSize = screenSize;
		screenRatio = screenSize.x / screenSize.y;
		
		if (plotterMax.y == 0) {
			plotterMax.y = plotterMax.x / screenRatio;
		}
	}
	public void setMode(PlotterDriver.plotterMode mode) {
		this.mode = mode;
	}
	public void setConnection(String ip, int port) {
		this.plotterIP = ip;
		this.plotterPort = port;
	}
	public void setInverts(boolean invertX, boolean invertY) {
		this.invertX = invertX;
		this.invertY = invertY;
	}
	
	public boolean penUp() {
		
		if (penDown) {
			penDown = false;
		}
		
		if (penUpFirst) {
			penUpFirst = false;
			return true;
		}
		
		return false;
	}
	
	public void livePlot(PVector startPoint, PVector endPoint, boolean penDown) {
		
		/* Does this work? I've had this disabled for the big plotter */
		/* This comment is even less useful than the last one 		  */
		
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

		currentSample++;
		return false;
	}
	
	public void plotPath(RPath path) {
		if (this.mode == mode.MOUSE_UP_PLOT) {
			RPoint[] points = path.getPoints();
			points = scalePoints(points);
			
			ArrayList<String> cmd = new ArrayList<String>();
			cmd.add("PA " + PApplet.floor(points[0].x) + " " + PApplet.floor(points[0].y));
			cmd.add("PD");
			
			for (RPoint point : points) {
				cmd.add("PA " + PApplet.floor(point.x) + " " + PApplet.floor(point.y));
			}
			
			cmd.add("PU");
			
			System.out.println("MEGA: " + cmd.toString());
			sendCommands(cmd);
		}
	}
	public void plotShape(RShape shape) {
		// Stub stub stub stub stub stub WTF stub
		
	}
	
	private void sendCommands(ArrayList<String> commands) {		
		if ((plotterSocket != null) && (plotterSocket.isConnected())) {
			for (String command : commands) {
				out.println(command);
			}
		}
	}
	
	private RPoint[] scalePoints(RPoint[] points) {
		
		for (RPoint point : points) {
			
			if (!invertX) {
				point.x = PApplet.floor(PApplet.map(point.x, 0, screenSize.x, plotterMin.x, PApplet.min(screenSize.x, plotterMax.x)));
			} else {
				point.x = PApplet.floor(PApplet.map(point.x, 0, screenSize.x, PApplet.min(screenSize.x, plotterMax.x), plotterMin.x));
			}
			
			if (!invertY) {
				point.y = PApplet.floor(PApplet.map(point.y, 0, screenSize.y, plotterMin.y, plotterMax.y));
			} else {
				point.y = PApplet.floor(PApplet.map(point.y, 0, screenSize.y, plotterMax.y, plotterMin.y));
			}
		}
		
		return points;
	}

}
