/**
  * @(#) LoggerConf.java 1.0 04-Feb-2013
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

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class is used to get the Logger Configuration
 *
 * @author Vikas Verma.
 * @version 1.0
 */

public class LoggerConf 
{
	
	String filePath = null;
	 	
	File confFile = null;
	 
	
	/**
     * This is default constructor
     *
     */
	
	
	public LoggerConf( )
	{
		//logger.writeLog("Info", "inside MailerConf.java class.....");
	}
	
	
	/**
     * Constructor takes String parameter as file path. This file path includes the file name as well
     * for example C:/conf/mailerconf.xml
     * 
     * @param  confFilePath 	String parameter as configuration file path along with the configuration file 
     * 							name.
     */
	
	
	public LoggerConf(String confFilePath)
	{
		
		filePath = parseFilePath(confFilePath);
 		
		confFile = new File(filePath);
		
	}
	
	
	/**
     * Constructor takes two String parameter as file path and file name separately. This file path does not includes 
     * the file name, for example C:/conf. File name provided as other parameter.
     * 
     * @param  confFileName    	String parameter as configuration file name provided separately.
     * @param  confFilePath    	String parameter as configuration file path alon with the configuration file 
     * 							name.
     */
	
	public LoggerConf(String confFileName, String confFilePath)
	{
		
		filePath = parseFilePath(confFilePath);
		
		filePath = filePath+"/"+confFileName;
		
		confFile = new File(confFilePath);
		
	}
	
	
	/**
     * This method checks for forward slash operator in the method and converts it in backward slash operator
     * for example C:\\conf will be converted to C:/conf.
     * 
     * @param  String   confFilePath   String parameter as configuration file path alon with the configuration file 
     * 									name.
     */
	
	public String parseFilePath(String confFilePath)
	{
		String parsedPath = confFilePath;
		
		int idx = confFilePath.indexOf("\\");
		
		if(idx>-1)
		{
						
			parsedPath = confFilePath.replaceAll("\\\\", "/");
				
		}
		
		return parsedPath;
	}
	
	 
	/**
     * Method internally call getMailerConfig method to parse xml file and returns Mail Configuration
     * 
     * @return 	return HashMap object of configuration details
     */
	
	
	public HashMap<String, String> getConfigData()
	{
		HashMap<String, String> map = null;
		
		
		if(confFile.exists())
		{
							
			map = getMailerConfig(confFile);
		
		}else
		{
			
			//logger.writeLog("severe", "Configuration XML file is null");
				
		}
				
		return map;
	}
	
  	
	
	/**
     * Method parse xml file and returns Logger Configuration
     * 
     * @param  confFile   Takes Configuration file Object as parameter
     * @return 			  return HashMap object of configuration details
     * 
     *     
     */
	
	public HashMap<String, String> getMailerConfig(File confFile)
	{
		
		HashMap<String, String> confData = new HashMap<String, String>();
		  
		
		try
		{
					 
			Document doc = null;
	        
	        DocumentBuilderFactory docbuilderfactory = DocumentBuilderFactory.newInstance();
	        
	        DocumentBuilder docbuilder = docbuilderfactory.newDocumentBuilder();
	        
	        doc = docbuilder.parse(confFile);
	        
	        Element rootElement = doc.getDocumentElement();
	        
	         	                
	        NodeList baseNodeList = rootElement.getElementsByTagName("log");
	        
	         
	        
	        Element baseElement = (Element)baseNodeList.item(0);
	        
	        // Get the log path from XML
	        
	        String logPath = null;
	        
	        NodeList logpath = baseElement.getElementsByTagName("logpath");
	       
	        Node logpathNode = logpath.item(0).getFirstChild();
	        
	        
	        if(logpathNode!=null)
	        {
	        	logPath = logpathNode.getNodeValue();
	        }
	        
	        
        	confData.put("logPath", logPath);
        	
        	
        	// Get the file name format from XML
        	
        	String fileName = null;
        	
        	NodeList filename = baseElement.getElementsByTagName("filename");
        	
        	Node filenameNode = filename.item(0).getFirstChild();
        	
        	if(filenameNode!=null)
 	        {
        		fileName = filenameNode.getNodeValue();
 	        	
 	        }
        	        	
         	
        	confData.put("fileName", fileName);
        	
        	
        	// Get the file size from XML
        	
        	String fileSize = null;
        	
        	NodeList filesize = baseElement.getElementsByTagName("filesize");
        	
        	Node filesizeNode = filesize.item(0).getFirstChild();
        	
        	if(filesizeNode!=null)
 	        {
        		fileSize = filesizeNode.getNodeValue();
 	        	
 	        }
        	        	
         	
        	confData.put("fileSize", fileSize);
        	

        	// Get the no of log files from XML
        	
        	String noOfFiles = null;
        	
        	NodeList nooffiles = baseElement.getElementsByTagName("nooffiles");
        	
        	Node nooffilesNode = nooffiles.item(0).getFirstChild();
        	
        	if(nooffilesNode!=null)
 	        {
        		noOfFiles = nooffilesNode.getNodeValue();
 	        	
 	        }
        	        	
         	
        	confData.put("noOfFiles", noOfFiles);
        	
        	
        	// Get the append parameter from XML
        	
        	String append = null;
        	
        	NodeList appends = baseElement.getElementsByTagName("append");
        	
        	Node appendNode = appends.item(0).getFirstChild();
        	
        	if(appendNode!=null)
 	        {
        		append = appendNode.getNodeValue();
 	        	
 	        }
        	        	
         	
        	confData.put("append", append);
        	
         
           
 	     }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 
			// logger.logExceptionStackTrace(e);
		 }
		
		
		//logger.writeLog("Info", "Exiting MailerConf.java class.....");
		
		return confData;
		
	}
 	

	public static void main(String []args)
	{
		
		System.out.println(new LoggerConf("/ne/conf/loggerconf.xml").getConfigData());
		
	}
	
	
}
