package com.nycresistor.plotterprojects.drivers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nycresistor.plotterprojects.drivers.PlotterDriver.plotterMode;

import geomerative.RPath;
import geomerative.RShape;
import processing.core.PVector;
import processing.serial.*;


public class RolandDriver implements PlotterDriver {

	PVector plotterMin = new PVector(0,0); 		// Minimum plotter points - (0,0) should be bottom left
	PVector plotterMax = new PVector(1200,0);   // Maximum plotter points - Top right. 1200 (mm) Max X for the Red Sail Plotter
	
	PVector screenSize = new PVector();
	float screenRatio;
	
	private plotterMode mode;
	
	private boolean penDown = true;
	private boolean penUpFirst = true;
	
	int currentSample = 1;
	int sampleNum = 3;
	
	private boolean invertX;
	private boolean invertY;
	
	Serial plotter;
	
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

	@Override
	public void livePlot(PVector start, PVector end, boolean penDown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void plotShape(RShape shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void plotPath(RPath currentPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConnection(String plotterIP, int plotterPort) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connect() {
		plotter = new Serial(, Serial.list()[0], 9600);
		
	}

	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}

}
