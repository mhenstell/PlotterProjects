package com.nycresistor.plotterprojects.sources;

import java.util.List;
import com.nycresistor.plotterprojects.sources.DataSource.dataType;

public class Message {

	private String target;
	private List<Message> message;
	private dataType type;
	private String from;
	private String fromNum;
	private String body;
	
	public String getTarget() { return target; }
	public List<Message> getMessage() { return message; }
	public dataType getType() { return type; }
	public String getFrom() { return from; }
	public String getFromNum() { return fromNum; }
	public String getBody() { return body; }
	
	public void setTarget(String target) { this.target = target; }
	public void setMessage(List<Message> message) { this.message = message; }
	public void setType(dataType type) { this.type = type; }
	public void setFrom(String from) { this.from = from; }
	public void setFromNum(String fromNum) { this.fromNum = fromNum; }
	public void setBody(String body) { this.body = body; }
	
	
	public void SMS(String fromNum, String body) {
		this.type = dataType.SMS;
		this.fromNum = fromNum;
		this.body = body;
		
	}
	

	
//	public Message(String fromName, String fromID, String fromNum, String messageBody) {
//		this.fromName = fromName;
//		this.fromID = fromID;
//		this.fromNum = fromNum;
//		this.messageBody = messageBody;
//	}
//	
//	public Message(String fromName, String fromID, String fromNum, String messageSubject, String messageBody) {
//		this.fromName = fromName;
//		this.fromID = fromID;
//		this.fromNum = fromNum;
//		this.messageSubject = messageSubject;
//		this.messageBody = messageBody;
//	}
	
}
