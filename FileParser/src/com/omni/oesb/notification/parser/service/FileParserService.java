package com.omni.oesb.notification.parser.service;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.notification.parser.adapters.FileParserAdapter;



public class FileParserService implements ServiceMaster{
	
	private final FileParserAdapter fileParserAdapter = FileParserAdapter.getFileParserAdapter();
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private Thread fileParserThread = null;
	
	public FileParserService(){
		fileLogger.writeLog("info", "Initializing FileParserService.....");
		/*fileLogger.writeLog("info", "Loading All Message Maps to Memeory......");
		
		fileLogger.writeLog("info", "Message Maps Loaded......");*/
	}
	
	public void startService(){
		fileParserThread = new Thread(fileParserAdapter);
		fileParserAdapter.setRunning(true);
		fileParserThread.start();
	}
	
	public void stopService(){
		fileParserAdapter.setRunning(false);
	}
	
	public boolean isServiceRunning(){
		return fileParserAdapter.isRunning();
	}
}
