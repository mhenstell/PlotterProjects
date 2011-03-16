package com.nycresistor.plotterprojects;

import java.util.Queue;
import java.util.Vector;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import processing.core.PApplet;
import com.nycresistor.plotterprojects.sources.*;

public class PlotterText extends PApplet {
	

	
	public static void main(String[] args) {
		 PApplet.main(new String[] { com.nycresistor.plotterprojects.PlotterText.class.getName() });

	}
	
	public void setup() {
		
		
		DataSource source = new SMS();
		Message message = source.getMessage();
		
		println(message.getFromNum() + ": " + message.getBody());
		
		
	}
	
	public void draw() {

	}
	
	
	

}
