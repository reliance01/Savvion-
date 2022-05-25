package com.agent.utility;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AgentLogger {

	
	static Logger logger = null;
	
	public static Logger getAgentLogger(){
		Properties prop = new Properties();	
		try {
			prop.load(ClassLoader.getSystemResourceAsStream("AgentRetentionProcess.properties"));
			PropertyConfigurator.configure(prop);
			logger = Logger.getLogger("AgentRetention");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logger;
	}
	
}
