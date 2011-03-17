package com.nycresistor.plotterprojects;

import geomerative.*;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Vector;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import processing.core.PApplet;
import processing.core.PVector;

import com.nycresistor.plotterprojects.sources.*;
import com.nycresistor.plotterprojects.drivers.*;


public class PlotterText extends PApplet {
	
	PVector screenSize = new PVector(750, 1000); // 1px = 1mm of Paper
	
	PlotterDriver plotter = new MegaPlotterDriver();
	PlotterDriver.plotterMode plotterMode = PlotterDriver.plotterMode.MOUSE_UP_PLOT;
	
	String plotterIP = "127.0.0.1";
	int plotterPort = 2000;
	
	String fontPath = "Arial Rounded Bold.ttf";

	DataSource source = new SMS();
	RFont font = null;
	RShape totalArea = null;
	RPolygon bounds = null;
	
	RPoint cursorLocation = new RPoint(0, 20);
	RShape previousWord = null;
	float wordSpacing, lineSpacing;
	
	public static void main(String[] args) {
		 PApplet.main(new String[] { com.nycresistor.plotterprojects.PlotterText.class.getName() });

	}
	
	public void setup() {
		size((int)screenSize.x, (int)screenSize.y);

		background(255);
		
		RG.init(this);
		
		font = new RFont(fontPath, 20, RFont.LEFT);
		RPoint tl = new RPoint(0,0);
		RPoint tr = new RPoint(screenSize.x, 0);
		RPoint bl = new RPoint(0, screenSize.y);
		RPoint br = new RPoint(screenSize.x, screenSize.y);
		RPoint[] points = {tl, tr, br, bl};
		bounds = new RPolygon(points);
		
		wordSpacing = font.size/6;
		lineSpacing = font.size/2;
		
	}
	
	private RPoint wordLocation(RShape word) {
		
		RPoint cursor = new RPoint(cursorLocation);
		
		float wordWidth = word.getWidth();
		RPoint wordCenter = word.getCenter();
		
		RPoint wordEndPoint = new RPoint(wordCenter.x + (wordWidth/2) + wordSpacing, cursorLocation.y);
		float wordEndX = wordEndPoint.x;
		cursorLocation.add(new RPoint(wordEndX, 0));
		
		
		return cursor;
	}
	
	public void draw() {
		
//		Message message = source.getMessage();
//		String messageString = message.getBody();
		
		String messageString = "The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog";
		String[] words = messageString.split(" ");
		ArrayList<RShape> wordShapes = new ArrayList<RShape>();
		
		for (String word : words) {
			RShape letter = font.toShape(word);
			wordShapes.add(letter);
		}
		
		for (RShape word : wordShapes) {
			print(words[wordShapes.indexOf(word)] + ": ");
			RPoint translatePoint = wordLocation(word);
			print("Translate Point: " + translatePoint.x + "," + translatePoint.y);
			word.translate(translatePoint);		
			
			println(" Bounds: (" + (int)word.getTopLeft().x + "," + (int)word.getTopLeft().y + ") (" + (int)word.getBottomRight().x + "," + (int)word.getBottomRight().y + ")");
			word.draw();
		}
		
		
		
		nextLine();
		
//		
//		RPoint tl = new RPoint(0,0);
//		RPoint tr = new RPoint(screenSize.x, 0);
//		RPoint bl = new RPoint(0, 20);
//		RPoint br = new RPoint(screenSize.x, 20);
//		RPoint[] rectPoints = {tl, tr, br, bl};
		//RPolygon textLine = new RPolygon(rectPoints);
		
		//RPoint startingPoint = getStartingPoint();
		
		
		//RPoint[][] pointsArray = messageShape.getPointsInPaths();
				
	
		
		delay(2000);
	}
	
	private void nextLine() {
		RPoint newLineStart = new RPoint(0, cursorLocation.y + lineSpacing);
		cursorLocation = newLineStart;
	}
	
	private RPoint getStartingPoint() {
		
		RPoint startingPoint = new RPoint(0,0);
		
		
		return startingPoint;
	}
	
	

}
