package com.nycresistor.plotterprojects.sources;

import java.util.List;

public class Message {

	private String target;
	private List<Message> message;
	private String type;
	private String from;
	private String fromNum;
	private String body;
	
	public String getTarget() { return target; }
	public List<Message> getMessage() { return message; }
	public String getType() { return type; }
	public String getFrom() { return from; }
	public String getFromNum() { return fromNum; }
	public String getBody() { return body; }
	
	public void setTarget(String target) { this.target = target; }
	public void setMessage(List<Message> message) { this.message = message; }
	public void setType(String type) { this.type = type; }
	public void setFrom(String from) { this.from = from; }
	public void setFromNum(String fromNum) { this.fromNum = fromNum; }
	public void setBody(String body) { this.body = body; }
	
	
	public Message(String fromNum, String messageBody) {
		this.fromNum = fromNum;
		this.body = messageBody;
	}
	
	public Message(String fromName, String fromNum, String messageBody) {
		this.from = fromName;
		this.fromNum = fromNum;
		this.body = messageBody;
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
