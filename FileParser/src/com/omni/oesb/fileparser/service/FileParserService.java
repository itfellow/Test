package com.omni.oesb.fileparser.service;

import java.util.Iterator;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.component.logging.FileLogger;
import com.omni.oesb.constants.ParserConstants;
import com.omni.oesb.fileparser.adapters.FileParserAdapter;


/**
 * This Class Controls the FileParserAdapter, i.e Starting,Stoping & Checking the Status of Adapter
 * @author Shinoj Shayin
 *
 */
public class FileParserService implements ServiceMaster{
	
	private final FileParserAdapter fileParserAdapter = FileParserAdapter.getFileParserAdapter();
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private Thread fileParserThread = null;
	
	
	public FileParserService(){
		
		DatabaseUtil dbUtil = new DatabaseUtil();
		
		List<Object> data = dbUtil.selectRecord("SELECT msg_code,code_value FROM NeftCodeMap");
		
		fileLogger.writeLog("info", "Initializing FileParserService.....");
		
		fileLogger.writeLog("info", "Loading All Message Maps From Database......");
		
		for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
			
			Object []value =  (Object[]) iterator.next();
			
			ParserConstants.NEFT_CODE_MAP.put(value[0].toString(), value[1].toString());
		
		}
		
		fileLogger.writeLog("info", "Neft Message Maps Loaded.");
	
	}
	
	
	/**
	 * This Method Starts FileParserAdapter<br>
	 * And Starts Picking Files From the Folder and Process It
	 */
	public void startService(){
		
		fileParserThread = new Thread(fileParserAdapter);
		
		fileParserAdapter.setRunning(true);
		
		fileParserThread.start();
		
	}
	
	
	
	/**
	 * This Method Stops the FileParserAdapter
	 */
	public void stopService(){
		
		fileParserAdapter.setRunning(false);
		
	}
	
	
	
	/**
	 * This Method checks the Status of FileParserAdpater
	 * @return {@link Boolean}
	 */
	public boolean isServiceRunning(){
		
		return fileParserAdapter.isRunning();
		
	}
	

}
