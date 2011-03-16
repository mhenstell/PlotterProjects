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

public class PlotterTwitter extends PApplet {
	
	Twitter myTwitter = new TwitterFactory().getInstance();
	
	Query query = new Query("#THEFEAR");
	
	Vector<Tweet> tweetQueue = new Vector();
	
	public static void main(String[] args) {
		 PApplet.main(new String[] { com.nycresistor.plotterprojects.PlotterTwitter.class.getName() });

	}
	
	public void setup() {
		
		
		
		
		
	}
	
	public void draw() {
		
		
		Tweet tweet = getNewTweet(query);
		
		if (tweet != null) {
			println(tweet.getText());
		}
		
		
		
		delay(1000);
	}
	
	public Tweet getNewTweet(Query query) {
		
		Tweet returnTweet = null;
		
		try {
			QueryResult qr = myTwitter.search(query);
			
			for (Tweet tweet : qr.getTweets()) {
				
				
				if (tweetQueue.lastIndexOf(tweet) == -1) {
					
					
					tweetQueue.add(tweet);
					return tweet;
				
				}
			
			}
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
