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
	RPoint lastWordPoint = new RPoint(0, 20);
	float wordSpacing, lineSpacing;
	float cursor;
	
	int currentLine = 1;
	ArrayList<RShape> lines = new ArrayList<RShape>();
	
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
		
		int lineLength = 0;
		RShape currentLineShape = new RShape();
		
		printWords(wordShapes); 
		
		cursor = 0;
		currentLine++;

		delay(1000);
	}
	
	public void clearScreen() {
		background(255);
		currentLine = 1;
		plotter.home();
	}
	
	private void printWords(ArrayList<RShape> wordShapes) {
		for (RShape word : wordShapes) {
			
			if ((currentLine * lineSpacing) > screenSize.y) {
				println("OVER Y");
				clearScreen();
			}
			
			//print(words[wordShapes.indexOf(word)] + ": ");
			float newCursor = cursor + word.getWidth() + wordSpacing;
			
			RShape newShape = new RShape(word);
			
			if (newCursor > screenSize.x) {
				cursor = word.getWidth() + wordSpacing;
				currentLine++;					
			} else {
				cursor+= word.getWidth() + wordSpacing;
			}
		
			word.translate(new RPoint(cursor - word.getWidth() - wordSpacing, currentLine * lineSpacing));
			
			

			print(" Width: " + word.getWidth());
			print(" CL: " + currentLine + " Cur: " + (int)cursor);
			println(" Translated: " + word.getX() + "," + word.getY());
			word.draw();
		}
	}

}
