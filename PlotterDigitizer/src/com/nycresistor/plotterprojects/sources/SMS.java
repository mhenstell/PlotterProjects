package com.nycresistor.plotterprojects.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.google.gson.*;

public class SMS implements DataSource {
	
	DataSource.dataType type = dataType.SMS;
	String messageURL = "http://localhost:8081";
	
	String fromNum;
	String messageBody;
	
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
		
		
		// Because fuck JSON parsing in Java, that's why
		String[] args = inMessage.replace("{", "").replace("}", "").replace("\"", "").split(",");
		
		//This will break
		String target = args[0].split(":")[1];
		String fromNum = args[2].split(":")[1];
		String body = args[3].split(":")[1];
		
		Message message = new Message(fromNum, body);
		
		return message;
		
	}
	
	
	
}
