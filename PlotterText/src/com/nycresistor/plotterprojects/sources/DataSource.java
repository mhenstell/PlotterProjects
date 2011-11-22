package com.nycresistor.plotterprojects.sources;

import java.io.IOException;

public interface DataSource {
	
	public enum dataType { SMS, TWITTER };
	
	
	public Message getMessage();
	
	
}
