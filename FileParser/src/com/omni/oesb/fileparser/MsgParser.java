package com.omni.oesb.fileparser;

import java.util.ArrayList;
import java.util.HashMap;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.constants.ParserPatterns;
import com.omni.oesb.notification.Util.ParserUtil;
import com.omni.oesb.omh.notification.TableInsert.TableDataInsert;


public class MsgParser{
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private ParserUtil parserUtil = new ParserUtil();

	private final TableDataInsert dataInsert = new TableDataInsert();
	
	public String[] parseTxtFile(String message) 
	{
		String []result = new String[2]; 		// this array hold parserStatus 
												// and variable to identify is UMAC present in the msg or not
		
		String parseStatus = ParserPatterns.MSG_PARSE_ERROR;
		
		result[0] = parseStatus;
		
		if(message!=null && message.length()>0){
			
			try
			{
				
				ArrayList<HashMap<String,String>> messageContent = parserUtil.getMessageContent(message);
				
				int messageListSize = messageContent.size();
				System.out.println("message List Size ====> "+messageListSize);
				
				for(int i=messageListSize; i>0; i--) 
				{
			
					try
					{

						HashMap<String,String> msgMap = messageContent.get(i - messageListSize);
						
						String msgHeader = msgMap.get("MSG_HEADER");
						
						String msgBody = msgMap.get("MSG_BODY");
						
					
						
						HashMap<String,String> headerMap = parserUtil.parseHeader(msgHeader);
						HashMap<String,String> msgBodyMap = parserUtil.parserBody(msgBody);			
						
						parseStatus = dataInsert.insertTxtData(headerMap,msgBodyMap);
						
						result[0] =  parseStatus;
								
						if(headerMap.get("IO_ID").equalsIgnoreCase("O") && parseStatus.equals(ParserPatterns.MSG_PARSE_SUCCESS)){
							
							result[1] = msgMap.get("UMAC");		// return string value 0 or 1,
																// 1  means UMAC is Present in File and 0, vice-versa
						}
						else{
							result[1] = "0";					// UMAC Signature not required for incoming Message, thats why flag set to "0"
						}
					}
					catch(Exception e){
						parseStatus = ParserPatterns.MSG_PARSE_ERROR;
						e.printStackTrace();
						System.out.println("Message Parsing Error");
						System.out.println("Some Data May be missing in file");
						
						fileLogger.writeLog("severe", e.getMessage());
						fileLogger.writeLog("severe", "Message Parsing Failed Message Pattern Matching Error");
					}
				}
			
			}
			catch(Exception e)
			{
				parseStatus = ParserPatterns.MSG_PARSE_ERROR;
				e.printStackTrace();
				
				fileLogger.writeLog("severe", e.getMessage());
				fileLogger.writeLog("severe", "Error Occured MsgParser Class: parseMessage Method");
			}
		
		}
		else{
			
			fileLogger.writeLog("warning", "Message not found to parse class:MsgParser Method:parseMessage");
			System.out.println("Message not found to parse class:MsgParser Method:parseMessage");
		
		}
		
		
		
		return result;
	}
	
	
	public String parseCsvMessage(String fileData)
	{
		String fileParseStatus=ParserPatterns.MSG_PARSE_ERROR;
		
		try{
			String columnName_values[]=fileData.split("\n"); 
			
			int valueSize = columnName_values.length;
			
			int index=0;		
			
			if(valueSize < 2)
			{
			 
				System.out.println("There is no proper data in file so file is ignored");	
				return ParserPatterns.MSG_PARSE_IGNORED;	
			
			}
			
		   
			ArrayList <String> csv[] = new ArrayList[valueSize];
		   
		   for(int i=0; i < valueSize; i++)
			    csv[i]=new ArrayList<String>();			// Created ArrayList Inside and ArrayList
		   												// to store Dynamic Data
		  
			for(String a:columnName_values)
			{
			    String data[]=a.split(",");
			    
			    for(String b:data)
			    {
		              csv[index].add(b);
		              
			    	System.out.println(b);
			    }
					
				index=index+1;
			
			}
			
			fileParseStatus=dataInsert.insertcsvdata(csv);
			
		}
		catch(Exception e){
			fileParseStatus=ParserPatterns.MSG_PARSE_ERROR;
			
			e.printStackTrace();
			fileLogger.writeLog("severe", e.getMessage());
			fileLogger.writeLog("severe", "Error Occurred class: MsgParser Method: parseCsvMessage");
		}
		
		
		return fileParseStatus;	
   	}
	
	

	
}
	
