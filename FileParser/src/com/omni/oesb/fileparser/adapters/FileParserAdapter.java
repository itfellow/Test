package com.omni.oesb.fileparser.adapters;

import java.io.File;
import java.util.ResourceBundle;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.fileparser.Util.FileReaderUtil;
import com.omni.util.common.PropAccess;


public class FileParserAdapter implements Runnable{

	private final  FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private ResourceBundle bundle 		= PropAccess.getResourceBundle();
	
	private static FileParserAdapter fileParserAdapter;
	
	private  boolean isRunning		 	= false;

	private final String sourceFilePath = bundle.getString("path").trim();
	
	private final int delayPeriod 		= Integer.parseInt(bundle.getString("delay").trim());
	
	
	/* 
	 * File Transfer Operations
	 */
	public void run() {
		
		fileLogger.writeLog("info", "FileParseAdapter Started.");
		
		FileReaderUtil fileReaderUtil = new FileReaderUtil();
		
		File srcFile 		= null;
		
		while(isRunning) {
			
			try {
				
				srcFile 	= new File(sourceFilePath);
			
				if (srcFile.isDirectory()) {
					
					String filePath = srcFile.getPath();
					
					System.out.println(filePath + " ======> " + srcFile.getName());
					
					long srcFileCount =  srcFile.listFiles().length;
					System.out.println("-----------------------"+ srcFileCount);
					
					if(srcFileCount > 0){
						fileReaderUtil.processFile(srcFile);
					}
					
				}
				else{
					
					System.out.println("Error Directory not found, path: "+srcFile.getAbsolutePath());
				}
				
				Thread.sleep(delayPeriod);
			}
			catch(Exception e) {
				
				e.printStackTrace();
				fileLogger.writeLog("warning", e.getMessage());
				fileLogger.writeLog("warning", "Inside the Catch of FileParserAdaper Run Method:");
				
			}
		}
		
		fileLogger.writeLog("info", "FileParseAdapter Stopped.");
	}
	
	public final static FileParserAdapter getFileParserAdapter() {

		System.out.println("Initializing Ne DashBoard Adapter............");

		if (fileParserAdapter == null)
			fileParserAdapter = new FileParserAdapter();

		return FileParserAdapter.fileParserAdapter;
	}
	
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}
