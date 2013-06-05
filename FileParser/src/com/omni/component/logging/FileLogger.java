/**
  * @(#) FileLogger.java 1.0 04-Feb-2013
  *
  * Copyright (c) 1996-2013 Omnitech Infosolutions Ltd.
  * Omnitech House. Plot No. A-13, Cross Road No. 5
  * MIDC, Marol, Andheri (E) Mumbai 400093
  * All rights reserved.
  *
  * This software is the confidential and proprietary information of 
  * Omnitech Infosolutions Ltd. ("Confidential Information").  You shall not
  * disclose such Confidential Information and shall use it only in
  * accordance with the terms of the license agreement you entered into
  * with Omnitech.
  * 
  */


package com.omni.component.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.omni.util.common.PropAccess;


/**
 * This Class is used to Write developer logs in the file specified
 *
 * @author Vikas Verma
 * @version 1.0
 */

public class FileLogger
{
	
	// File Logger object
	
	private static  FileLogger fileLogger = null;
	
	private static Logger logger = null;
	
	/**
	 * This class constructor initializes log file path and
	 * the log file format
	 * 
	 * @param className - class name of the class which uses the logger
	 * 
	 * */
	
	private FileLogger(String className) throws Exception
	{
	 	 
		
		logger = Logger.getLogger(className);
		
		logger.setLevel(Level.ALL);
		
		
		// Create the name of Log File
		
		
		Calendar calendar = new GregorianCalendar();
		
		String currentDate = ""+calendar.get(Calendar.DAY_OF_MONTH);
		
		String currentMonth = ""+calendar.get(Calendar.MONTH);
		
		String currentYear = ""+calendar.get(Calendar.YEAR);
		
		
		int month = Integer.parseInt(currentMonth.trim()) + 1;
		
		
		// Get the folder path of the Log file
		
		
		ResourceBundle bundle = PropAccess.getResourceBundle();
		
		
		String loggerConfPath = bundle.getString("Fileloggerconf").trim();
		
		
		HashMap<?, ?> loggerConfData = new LoggerConf(loggerConfPath).getConfigData();
		
		
		String logPath = (String)loggerConfData.get("logPath");
		
		String fileName = (String)loggerConfData.get("fileName");
		
		String fileSize = (String)loggerConfData.get("fileSize");
		
		String noOfFiles = (String)loggerConfData.get("noOfFiles");
		
		String append = (String)loggerConfData.get("append");
		
		
		if(logPath==null)
		{
			
			throw new LoggerException("Log file path is null");
			
		}
		
		if(fileName==null)
		{
			
			throw new LoggerException("Log file name pattern is null");
			
		}
		
		
		if(fileSize==null)
		{
			
			throw new LoggerException("Log file size is null");
			
		}
		
		
		if(append==null)
		{
			
			throw new LoggerException("append parameter is null");
			
		}
		
		
		// Set the Pattern of log file
		
		
		String dateStamp = currentDate.trim()+"_"+month+"_"+currentYear.trim();
		
		
		String filePattern = fileName.replace("currentdate$", dateStamp);
		
		
		filePattern = logPath+"/"+filePattern;
		
		
		// Set append parameter
		
		
		boolean isAppend = false;
		
		
		if(append.equalsIgnoreCase("yes"))
		{
			
			isAppend = true;
			
		}
		
		// Set fileSizelimit
		
		
		int fileSizelimit = Integer.parseInt(fileSize);
		
		
		// Set noOfFilesLimit
		
		
		int noOfFilesLimit = Integer.parseInt(noOfFiles);
		
  	
		
		// Create file handler to create logs
		
		
		
		FileHandler logFileHandler = new FileHandler(filePattern,fileSizelimit,noOfFilesLimit,isAppend);
		
		 
		
		// Format the logs
		
				
		Formatter formatter = new FileLogFormatter();
		
		logFileHandler.setFormatter(formatter);
		
		logger.addHandler(logFileHandler);
		
	}
	
	
	/**
     * Method gets FileLogger class object
     * @return             className - class name of the class which uses the logger
     * @param FileLogger   returns FileLogger class object
     * 
     */  
	public static FileLogger getFileLogger(String className) 
	{
		
		try
		{

			if(fileLogger == null)
			{
				fileLogger = new FileLogger(className);
				
			}
		
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
		
		return fileLogger;
		
	}
	
	
	/**
     * Method used to write log to file
     * 
     * @param String   specifies log level: info - for information, severe - errors or exception, warning - for warning
     * @param String   msg - log message that will be written to the log file
     * 
     */  
	public void writeLog(String logLevel,String msg)
	{
		
		try
		{
			
			if(logLevel.equalsIgnoreCase("info"))
			{
				logger.info(msg);
			}
			else if(logLevel.equalsIgnoreCase("severe"))
			{
				logger.severe(msg);
			}
			else if(logLevel.equalsIgnoreCase("warning"))
			{
				logger.warning(msg);
			}
			
		}
		catch(Exception e)
		{
			
			logger.severe(e.getMessage());
			e.printStackTrace();
			
		}
		
		
	}
	
	
	/**
     * Method used to exception stacktrace
     * 
     * @param Exception   stacktrace of the Exception object will written to log file
     * 
     */ 
	
	public void logExceptionStackTrace(Exception e)
	{
		
		StringWriter sw = null;
		PrintWriter pw = null;
		
		try
		{
			
			sw = new StringWriter();
			pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			logger.severe(sw.toString());
			
		}
		catch(Exception ex)
		{
			
			logger.severe(e.getMessage());
			e.printStackTrace();
			
		}
		finally
		{
			
			try
			{
			
				if(pw != null)
				{
					pw.flush();
					pw.close();
				}
				
				if(sw != null)
				{
					sw.flush();
					sw.close();
				}
			
			}
			catch(Exception ex)
			{
				
				logger.severe(e.getMessage());
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}
