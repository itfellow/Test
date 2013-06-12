package com.omni.oesb.fileparser.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.DigitalSignature.SignData;
import com.omni.oesb.common.AppConstants;
import com.omni.oesb.fileparser.MessageParser;
import com.omni.util.common.PropAccess;

public class FileReaderUtil 
{
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
		
	private static final ResourceBundle bundle = PropAccess.getResourceBundle();
	
	private static final MessageParser msgParser = new MessageParser();
	
	private static  final String destinationFilePath	= bundle.getString("destPath").trim();
	
	private static  final String ignoredFilePath 		= bundle.getString("ignoredPath").trim();
	
	private static final String corruptFilePath 		= bundle.getString("corruptPath").trim();
	
	/*
	 * This method handle the file processing operations like reading the file from source folder, parsing it
	 * and allocating the file to other folder like destination,ignored,corrupt.
	 */
	synchronized public void processFile(File srcFile){

			File allFiles[] = srcFile.listFiles();
			
			int fileCount = allFiles.length;
			
			fileLogger.writeLog("info", "Number of Files Picked : "+fileCount);
			
			if(allFiles != null && fileCount!=0){
				File sourceFile = null;
				
				for(int i=0; i<fileCount; i++){
					try{
						
					sourceFile = allFiles[i];
					
					if(sourceFile.isFile()) {
						
						String fileName = sourceFile.getName();
						
						fileLogger.writeLog("info", "Reading File Name : "+fileName);
						
						int fileLength = sourceFile.getName().length();
						
						String fileData = readFileData(sourceFile);
						
						String fileParseStatus = AppConstants.MSG_PARSE_ERROR;
						
						if(fileData!=null && !(fileData.trim().length() == 0)){
							if(fileName.substring(fileLength-4, fileLength).equalsIgnoreCase(".txt")){
								
								String result[] = new String[2];
								String umach_flag;
								
								// Main Method that parse Text Files
								result = msgParser.parseTxtFile(fileData);
								
								fileParseStatus = result[0];
								umach_flag =  result[1];
								
								if(fileParseStatus.equals(AppConstants.MSG_PARSE_SUCCESS)){
									if(umach_flag!=null){
										if(umach_flag.equals("1"))
											WriteUMACData(sourceFile);			//  this method append UMAC/Digital Signature 
																			// 	at the end of the file
									}
									else{
										
										System.out.println("Umac flag found null");
										
									}
										
								}
								
								System.out.println(sourceFile.getName()+ " ========> "+fileParseStatus);
								
						}
						else if(fileName.substring(fileLength-4, fileLength).equalsIgnoreCase(".csv"))	{
									
								// Main Method that parse CSV Files
								fileParseStatus = msgParser.parseCsvMessage(fileData);
									
									
									
						}
						else if(fileName.substring(fileLength-4, fileLength).equalsIgnoreCase(".xml"))	{
							
							
								/*	
								 * 	Nilesh code for xml parsing starts From here
								 * 	fileParseStatus = msgParser.parseXMLMessage(fileData);
								 * 
								 */
								
								
								
						}
						else{
								
								fileParseStatus = AppConstants.MSG_PARSE_IGNORED;
									
								System.out.println("file ignored because unrecognised file format, FileName : "+fileName);
									
								fileLogger.writeLog("warning", "file ignored because unrecognised file format, FileName : "+fileName);
							}
						}
						else{
							fileParseStatus = AppConstants.MSG_PARSE_IGNORED;
							
							System.out.println("file ignored because File Contain No Data, FileName : "+fileName);
							
							fileLogger.writeLog("warning", "file ignored because File Contain No Data : "+fileName);
						}
						
						if(fileParseStatus!=null)
							FileMover(sourceFile,fileParseStatus,fileName);
						
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					fileLogger.writeLog("severe",e.getMessage());
					fileLogger.writeLog("severe","Some Error Occured Class: FileReaderUtil Method: processFile");
				}
					
					
			}
		}
		else{
			System.out.println("No file to process");
		}
	} 
	
	
	private void FileMover(File sourceFile,String fileParseStatus,String fileName){
		try{

			if(fileParseStatus.equals(AppConstants.MSG_PARSE_ERROR)){
				File crrFile = new File(corruptFilePath);
				File corruptFile = new File(crrFile.getAbsolutePath()+"\\"+sourceFile.getName());
				moveFile(sourceFile, corruptFile);
				fileLogger.writeLog("info", "File Moved to Corrupt Folder, FileName : "+fileName );
				
			} 
			else 
			if(fileParseStatus.equals(AppConstants.MSG_PARSE_IGNORED)) {
				
				File ignoredFile = new File(ignoredFilePath);
				File ignoreFile = new File(ignoredFile.getAbsolutePath()+"\\"+sourceFile.getName());
				moveFile(sourceFile, ignoreFile);
				fileLogger.writeLog("info", "File Moved to Ignore Folder, FileName : "+fileName );
				
			} 
			else 
			if(fileParseStatus.equals(AppConstants.MSG_PARSE_SUCCESS)) {
				File destFile = new File(destinationFilePath);
				File processedFile = new File(destFile.getAbsolutePath()+"\\"+sourceFile.getName());
				moveFile(sourceFile, processedFile);
				fileLogger.writeLog("info", "File Moved to Processed Folder, FileName : "+fileName );
				
			}
		}
		catch(Exception e){
			
			e.printStackTrace();
			
			fileLogger.writeLog("severe",e.getMessage());
			fileLogger.writeLog("severe","Exception Occured in FileMover Method");
		}
	}
	

	
	public String readFileData(File src)  
	{
		StringBuffer fileData = new StringBuffer();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			
			fileReader = new FileReader(src);
			bufferedReader = new BufferedReader(fileReader);
			
			String thisLine = null;
			while((thisLine = bufferedReader.readLine()) != null) {
				
				fileData.append(thisLine);
				fileData.append("\n");
					
				}
			
		
		} 
		catch(Exception e) 
		{
			 e.printStackTrace();			 
		} 
		finally 
		{
			try
			{
			if(fileReader != null)
				fileReader.close();
			
			if(bufferedReader != null)
				bufferedReader.close();
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
		}
		
		return fileData.toString();
	}
	
	public void writeData(File dest,StringBuffer data,boolean isAppend){
		
		try{
			FileWriter fileWritter = new FileWriter(dest,isAppend);
	        
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    bufferWritter.write(data.toString());
    	    
    	    if(bufferWritter !=null){
    	    	bufferWritter.flush();
    	    	bufferWritter.close();   
    	    }
    	    
    	    if(fileWritter !=null){
    	    	
    	    	fileWritter.close();
    	    }
		}
		catch(IOException e){
			e.printStackTrace();
			fileLogger.writeLog("severe", e.getMessage());
    		fileLogger.writeLog("severe", "Error When Appending to "+dest.getAbsolutePath());
		}
		
	}
	
	private void WriteUMACData(File src){
			
    		StringBuffer data = new StringBuffer("{UMAC:");
    		
    		data.append(SignData.execute());
    		
    		data.append("}");
    		
    		//true = append file
    		
    		
    		writeData(src,data,true);
    		
	        System.out.println("Digital Signature Appended");
	        fileLogger.writeLog("info", "UMAC With Digital Signature Appended at EOF");
  
	}
	
	public void moveFile(File sourceFile, File dest) throws Exception {
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			
			if(!dest.exists()) {
				
				dest.createNewFile();
				
			}
			
			inputStream = new FileInputStream(sourceFile);			
			outputStream = new FileOutputStream(dest);
			
			byte[] buf = new byte[(int) sourceFile.length()];
			int len;
			
			while ((len = inputStream.read(buf)) > 0) {
				
				outputStream.write(buf, 0, len);
				
			}
			
		} catch(Exception e) {
			 
			 e.printStackTrace();
			 fileLogger.writeLog("severe", e.getMessage());
			 
		} finally {
			
			if(inputStream != null)
				inputStream.close();
			
			if(outputStream != null) {
				
				outputStream.flush();
				outputStream.close();
			
			}
			
		}
		
		sourceFile.delete();
		
	}
	
	public int getFileCount(File file) throws Exception  {  
		
        int count;
        
		try {
			
			if (!file.isDirectory())  {  
			    return 1;  
			}  
			
			count = 0;  
			File[] files = file.listFiles();  
			if (files != null)   {  
				
			    for (File f: files)  {  
			    	
			        count += getFileCount(f);
			        System.out.println("Count of Files "+count);
			        
			    }  
			    
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Exception thrown by getFileCount : getFileCount"+e);
			
		} 
        
        return count;  
    }  

}
