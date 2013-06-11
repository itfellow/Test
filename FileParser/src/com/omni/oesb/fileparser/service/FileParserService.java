package com.omni.oesb.fileparser.service;

import java.util.Iterator;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.component.logging.FileLogger;
import com.omni.oesb.constants.AppConstants;
import com.omni.oesb.fileparser.adapters.FileParserAdapter;


/**
 * 
 * This Class Controls the FileParserAdapter, i.e Starting,Stoping & Checking the Status of Adapter
 * @author Shinoj Shayin
 *
 */
public class FileParserService implements ServiceMaster{
	
	private final FileParserAdapter fileParserAdapter = FileParserAdapter.getFileParserAdapter();
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private Thread fileParserThread = null;
	
	
	public FileParserService(){
		
		try{
			DatabaseUtil dbUtil = new DatabaseUtil();
			
			List<Object> data = dbUtil.selectRecord("SELECT msg_code,code_value FROM NeftCodeMap");
			
			fileLogger.writeLog("info", "Initializing FileParserService.....");
			
			fileLogger.writeLog("info", "Loading All Message Maps From Database......");
			
			Object []value;
			
			for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
				
				value = null;
				
				value =  (Object[]) iterator.next();
				
				// Loads  NeftCodeMap Table in Database to static HashMap
				AppConstants.NEFT_CODE_MAP.put(value[0].toString(), value[1].toString());
			
			}
			
			fileLogger.writeLog("info", "Neft Message Maps Loaded.");
			
			data = null;
			
			data = dbUtil.selectRecord("SELECT msg_code,code_value FROM RtgsCodeMap");
			
			int codeArrayLen;
			
			for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
				
				codeArrayLen = 0;
				
				value = null;
				
				value =  (Object[]) iterator.next();
				
				String codeStr = value[0].toString();
				
				String codeVal = value[1].toString();
				
				String []codeArray  = codeStr.split(":");
				
				codeArrayLen = codeArray.length;
				
				for (int i = 0; i < codeArrayLen; i++) {
					
					// Loads  RtgsCodeMap Table in Database to static HashMap
					AppConstants.RTGS_CODE_MAP.put(codeArray[i], codeVal);
					
				}
			
			}
			
			fileLogger.writeLog("info", "Rtgs Message Maps Loaded.");
		}
		catch(Exception e){
			
			e.printStackTrace();
			
			fileLogger.writeLog("severe", e.getMessage());
			
			fileLogger.writeLog("severe", "Error While Initializing FileParserService");
			
			fileLogger.writeLog("info", "Application Stopped!!");
			
			System.exit(0);
			
		}
		
	
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
	
	public static void main(String[] args) {
		
		DatabaseUtil dbUtil = new DatabaseUtil();
		
		List<Object> data = dbUtil.selectRecord("SELECT msg_code,code_value FROM RtgsCodeMap");
		
		int codeArrayLen;
		
		
		for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
			codeArrayLen = 0;
			Object []value =  (Object[]) iterator.next();
			String codeStr = value[0].toString();
			String codeVal = value[1].toString();
			
			String []codeArray  = codeStr.split(":");
			codeArrayLen = codeArray.length;
			for (int i = 0; i < codeArrayLen; i++) {
				System.out.println(codeArray[i]+"="+codeVal);
			}
		
		}
	}
}
