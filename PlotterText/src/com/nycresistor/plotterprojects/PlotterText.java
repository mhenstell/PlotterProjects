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
import processing.serial.Serial;

import com.nycresistor.plotterprojects.sources.*;
import com.nycresistor.plotterprojects.sources.DataSource.dataType;
import com.nycresistor.plotterprojects.drivers.*;


public class PlotterText extends PApplet {
	
	PVector screenSize = new PVector(750, 1000); // 1px = 1mm of Paper
	
	PlotterDriver plotter = new MegaPlotterDriver();
	PlotterDriver.plotterMode plotterMode = PlotterDriver.plotterMode.MOUSE_UP_PLOT;
	
	DataSource.dataType dataSource = DataSource.dataType.SMS;
	
	String plotterIP = "127.0.0.1";
	int plotterPort = 2000;
	
	String fontPath = "Arial Rounded Bold.ttf";

	DataSource source = new SMS();
	RFont font = null;
	RFont subtextFont = null;
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
		subtextFont = new RFont(fontPath, 18, RFont.LEFT);
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
		
///		Message message = source.getMessage();
//	/	String messageString = message.getBody();
		
		String messageString = "The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog";
		
		Message message = new Message();
		message.SMS("3103090428", messageString);
		
		printMessage(message);

		lineBreak();
		
		delay(1000);
		

	}
	
	public void printMessage(Message message) {
		String messageBody = message.getBody();
		String[] words = messageBody.split(" ");
		
		// Print main message
		ArrayList<RShape> wordShapes = new ArrayList<RShape>();
		for (String word : words) {
			RShape letter = font.toShape(word);
			wordShapes.add(letter);
		}
		
		printWordShapes(wordShapes);
		
		// Print subtext
		String subtext = getSubtext(message);
		RShape subtextShape = subtextFont.toShape(subtext);
		printSubtext(subtextShape);
		
	}
	
	
	private String getSubtext(Message message) {
		
		StringBuilder subtext = new StringBuilder();
		
		if (dataSource == dataType.SMS) {
			subtext.append("From: " + formatPhoneNumber(message.getFromNum()));
			
			
		}
		
		return subtext.toString();
	}
	
	private String formatPhoneNumber(String fromNum) {
		//todo
		String formattedNumber = fromNum;
		
		return formattedNumber;
	}
	
	private void printSubtext(RShape subtext) {
		lineBreak();
		subtext.translate(new RPoint(screenSize.x - subtext.getWidth() - wordSpacing, (currentLine * lineSpacing) - (lineSpacing / 2)));
		subtext.draw();
		println(subtext.getTopLeft().x + "," + subtext.getTopLeft().y);
	}
	
	private void printWordShape(RShape word) {
		
		if ((currentLine * lineSpacing) > screenSize.y) {
			println("OVER Y");
			clearScreen();
		}
		
		//print(words[wordShapes.indexOf(word)] + ": ");
		float newCursor = cursor + word.getWidth() + wordSpacing;
		
		RShape newShape = new RShape(word);
		
		if (newCursor > screenSize.x) {
			cursor = word.getWidth() + wordSpacing;
			lineBreak();					
		} else {
			cursor+= word.getWidth() + wordSpacing;
		}
	
		word.translate(new RPoint(cursor - word.getWidth() - wordSpacing, currentLine * lineSpacing));
		
		

		print(" Width: " + word.getWidth());
		print(" CL: " + currentLine + " Cur: " + (int)cursor);
		println(" Translated: " + word.getX() + "," + word.getY());
		word.draw();
		
	}
	private void printWordShapes(ArrayList<RShape> wordShapes) {
		for (RShape word : wordShapes) {
			
			printWordShape(word);
			
		}
	}
	public void clearScreen() {
		background(255);
		currentLine = 1;
		plotter.home();
	}
	
	public void lineBreak() {
		currentLine++;
		cursor = 0;
	}

}
