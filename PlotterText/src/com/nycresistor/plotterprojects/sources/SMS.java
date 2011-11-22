package com.nycresistor.plotterprojects.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;

import org.json.*;

public class SMS implements DataSource {
	
	DataSource.dataType type = dataType.SMS;
	String messageURL = "http://localhost:8081";
	
//	String fromNum;
//	String messageBody;
//	
	public SMS() {
		
	}
	
	public Message getMessage() {
		URL url;
		StringBuilder inMessage = new StringBuilder();
		
		try {
			url = new URL(messageURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			
			
			while ((inputLine = in.readLine()) != null) {
				inMessage.append(inputLine).append('\n');
			}
			in.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Message message = parseMessage(inMessage.toString());
		return message;
		
		
	}
	
	private Message parseMessage(String inMessage) {

		System.out.println(inMessage);
		
		JSONObject jo = null;
		try {
			jo = new JSONObject(inMessage);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String target = jo.getString("target");
		JSONObject message = jo.getJSONObject("message");
		
		String type = message.getString("type");
		String fromNum = message.getString("from");
		String body = message.getString("body");
		
		Message msg = new Message();
		msg.SMS(fromNum, body);
		
		return msg;

		
	}
	
	
	
}
