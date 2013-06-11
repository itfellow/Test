package com.omni.oesb.fileparser.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.common.AppConstants;


public class ParserUtil 
{
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private HashMap <String,String> headerMap = null;
	
	private HashMap<String,String> msgBodyMap = null;
	
	private int msgStrLen = 0;
	
	public HashMap<String,String> parseHeader(String msgHeader) 
	
	{
		msgStrLen =  msgHeader.length();
		headerMap=new HashMap <String,String>();
		
		if(msgHeader!=null && msgStrLen>0){
			
			String sndr_bnk_id = msgHeader.substring(0, 3);
			String msg_id = msgHeader.substring(3, 6);
			String IO_id = msgHeader.substring(6, 7);
			String msgType = msgHeader.substring(7, 10);
			String msgSubType = msgHeader.substring(10, 13);
			String senderIFSC = msgHeader.substring(13, 24);
			String receiverIFSC = msgHeader.substring(24, 35);
			String deliveryNotifyFlg = msgHeader.substring(35, 36);
			String openNotifyFlg = msgHeader.substring(36, 37);
			String nonDelWarnFlag = msgHeader.substring(37, 38);
			String obsolencePeriod = msgHeader.substring(38, 41);
			String mur = msgHeader.substring(41, 57);
			String possibleDupEmissionFlg = msgHeader.substring(57, 58);
			String serviceId = msgHeader.substring(58, 61);
			String originatingDT = msgHeader.substring(61, 69);
			originatingDT = date(originatingDT, '/');
			String originatingTime = msgHeader.substring(69, 73);
			String testTrainFlag = msgHeader.substring(73, 74);
			String seqNum = msgHeader.substring(74, 83);
			
			String UTR = null;
			String priorityFlag = null;
			String filler = null;
			
			if(msgStrLen==112){
				filler = msgHeader.substring(83, 93);
				
				if(msgSubType.indexOf("R") != -1)  {
				
					UTR = msgHeader.substring(92, 109);
					priorityFlag = msgHeader.substring(108, 111);
				
				}
			}
			else{
				filler = msgHeader.substring(83, 92);
				
				if(msgSubType.indexOf("R") != -1)  {
				
					UTR = msgHeader.substring(92, 108);
					priorityFlag = msgHeader.substring(108, 110);
				
				}
			}
			
			headerMap.put("SNDR_BNK_ID", sndr_bnk_id);
			headerMap.put("MSG_ID", msg_id);
			headerMap.put("IO_ID", IO_id);
			headerMap.put("MSG_TYPE", msgType);
			headerMap.put("MSG_SUBTYPE", msgSubType);
			headerMap.put("SNDR_IFSC", senderIFSC);
			headerMap.put("RCVR_IFSC", receiverIFSC);
			headerMap.put("DVRY_NOTIFY_FLG", deliveryNotifyFlg);
			headerMap.put("OPEN_NOTIFY_FLG", openNotifyFlg);
			headerMap.put("NON_DELV_FLG", nonDelWarnFlag);
			headerMap.put("OBSOL_PERIOD", obsolencePeriod);
			headerMap.put("MUR", mur);
			headerMap.put("POSBL_DUP_EMSN_FLAG", possibleDupEmissionFlg);
			headerMap.put("SERVICE_ID", serviceId);
			headerMap.put("ORGIN_DT", originatingDT);
			headerMap.put("ORGIN_TIME", originatingTime);
			headerMap.put("TEST_TRAIN_FLAG", testTrainFlag);
			headerMap.put("SEQ_NUM", seqNum);
			headerMap.put("FILLER", filler);
			headerMap.put("UTR", UTR);
			headerMap.put("PRIORITY_FLAG", priorityFlag);
			
			fileLogger.writeLog("info", "Message Header Data Fetched");
		}
		else{
			fileLogger.writeLog("info", "Message Header Data Fetch Failed");
		}
			
		  
		return headerMap;
	}

	public HashMap<String,String> parserBody(String msgBody)  
	{
		
		
		ArrayList<String>[] msgFormatDataList = new ArrayList[2];
		msgFormatDataList[0] = new ArrayList<String>();
		msgFormatDataList[1] = new ArrayList<String>();
		
		
		String codePatternStr = ":[0-9]{4}:"; 
		

		Pattern codeMapPattern = Pattern.compile(AppConstants.MSG_BLOCK_DATA_PATTERN);
		Matcher codeMapMatcher = codeMapPattern.matcher(msgBody);
			        
		Pattern codePattern = Pattern.compile(codePatternStr);
		Matcher codeMatcher = codePattern.matcher(msgBody);
			    
		while (codeMapMatcher.find()) 
	    {
			String key=null;
			
			String value=null;
			
	        value=msgBody.substring(codeMapMatcher.start()+6, codeMapMatcher.end());
	        
	        codeMatcher.find();
	        
	        key=msgBody.substring(codeMatcher.start(), codeMatcher.end());
	        
	        key = key.replaceAll(":", "");
	        
	        /*   String code_value[]=value.split("\\n");
	        	
	       value =	"";
	        	
	        for(String code:code_value)
	        {
	        	value=value+code;
	        }*/
	       
	        
	        key = key.trim();
	        value=value.trim();
	        
	       
	        
	        msgFormatDataList[0].add(key);
	        msgFormatDataList[1].add(value);
	    
	    }  
	         
	    msgBodyMap = setArrayListToHashMap(msgFormatDataList);
	         
	   fileLogger.writeLog("info", "Message Body Data Fetched");
	         
	   return msgBodyMap;
	   
	} 
		 
        
    
    
	
	
	
	
	
	

	
	public ArrayList<HashMap<String,String>> getMessageContent(String message) 
	{
		ArrayList<HashMap<String,String>> messagesList = new ArrayList<HashMap<String,String>>();
		
		String isUMACRequired = "1";
		
		if(message != null && message.trim().length() > 0){
			
			Pattern msgBlockPattern = Pattern.compile(AppConstants.MSG_BLOCK_PATTERN);
			
			Matcher msgBlkPatternMatcher = msgBlockPattern.matcher(message);
			
			while(msgBlkPatternMatcher.find()) 
			{
				fileLogger.writeLog("info", "Message Recognised");
				
				String msgBlock = message.substring(0,message.length());
				
				HashMap<String,String> msgMap = new HashMap<String,String>();
				
				Pattern msgHeaderPattern = Pattern.compile(AppConstants.MSG_BLOCK_HEADER_PATTERN);
				
				Matcher msgHeaderPatternMatcher = msgHeaderPattern.matcher(msgBlock);
				
				String msgHeader = null;	
				
				while(msgHeaderPatternMatcher.find()) {
					
					// Header of the Message
					msgHeader = msgBlock.substring(msgHeaderPatternMatcher.start()+3,msgHeaderPatternMatcher.end());
					
					fileLogger.writeLog("info", "Message Header Pattern Identified");
				}
				
				Pattern msgBodyPattern = Pattern.compile(AppConstants.MSG_BLOCK_BODY_PATTERN);
				Matcher msgBodyPatternMatcher = msgBodyPattern.matcher(msgBlock);
				String msgBody = null;
				while(msgBodyPatternMatcher.find()) {
					// Body of the Message
					msgBody = msgBlock.substring(msgBodyPatternMatcher.start(),msgBodyPatternMatcher.end());
				
					fileLogger.writeLog("info", "Message Body Pattern Identified");
				}
				
				Pattern umacPattern = Pattern.compile(AppConstants.MSG_UMAC_BLOCK_PATTERN);
				Matcher umacPatternMatcher = umacPattern.matcher(msgBlock);
				String UMAC = null;
				while(umacPatternMatcher.find()) {
					// UMAC Part of Message (UMAC contain Unique Digital Signature of the Message)
					isUMACRequired = "0";
					
					fileLogger.writeLog("info", "Message UMAC Pattern Identified");
				}
			   
				msgBody=msgBody.replace("'", "\\'");
				
				msgMap.put("MSG_HEADER", msgHeader);
				msgMap.put("MSG_BODY", msgBody);
				msgMap.put("UMAC", isUMACRequired);
				
				messagesList.add(msgMap);
				
			}
		}
		else{
			
			fileLogger.writeLog("warning", "Error: Message String Passed to getMessageContent Method (Message Pasring) is NULL");
			System.out.println("Message string passed is Null, Class:ParserUtil Method:getMessageList");
		}
			
		
		
		return messagesList;
	}
	
	public  String[] parseDateCurrAmtString(String datCurVal, char separator) throws Exception  {

        String a[];
        
		try {
			
			a = new String[3];
			String date="[0-9]+";   
			String cur="[a-zA-z]+";
			String val="[0-9,]+|[0-9.]+";
			
			Pattern p_date=Pattern.compile(date);
			Matcher m_date=p_date.matcher(datCurVal);                
			
			Pattern p_cur=Pattern.compile(cur);
			Matcher m_cur=p_cur.matcher(datCurVal);   

			Pattern p_val=Pattern.compile(val);
			Matcher m_val=p_val.matcher(datCurVal);   

			m_date.find();
			m_cur.find();
			m_val.find();
			m_val.start();
			m_val.end();              
			m_val.find();
			
			a[0]=date(datCurVal.substring(m_date.start(),m_date.end()),separator);
			a[1]=datCurVal.substring(m_cur.start(),m_cur.end());
			a[2]=datCurVal.substring(m_val.start(),m_val.end());
			
			a[2] = a[2].replaceAll(",", ".");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Exception thrown by datCurValParser : ParserHelper"+e);
			
		}

        return a;

    }
	
	
	
	private String date(String date,char separator)  {

        return date.substring(0,4)+separator+date.substring(4,6)+separator+date.substring(6);

    }
	
/*	public String generateMsgHeaderId() throws Exception 
	{
		
		String msgHeaderId = null;
		
		try {
			
			String getMsgHeaderIdQry = "SELECT MAX(HEADER_ID)+1 FROM msg_header";
			
			DataAccessObject dao = new DataAccessObject();
			ArrayList[] dataList = dao.queryExecute("getMsgHeaderIdQry ====> ", getMsgHeaderIdQry);
			
			msgHeaderId = (String)dataList[0].get(0);
			
			if(msgHeaderId.trim().length() == 0)
				msgHeaderId = "1";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Exception thrown by generateMsgHeaderId : ParserHelper"+e);
			
		}
		
		return msgHeaderId;
	}*/
	
	private HashMap<String,String> setArrayListToHashMap(ArrayList<String>[] dataList) 
	{
		
		HashMap<String,String> dataMap = new HashMap<String,String>();
		
		
		
			int dataListCount = dataList[0].size();
			//System.out.println("dataListCount ====> "+dataListCount);
			
			ArrayList<String>[] repeatedStrList = new ArrayList[3];
			repeatedStrList[0] = new ArrayList<String>();
			repeatedStrList[1] = new ArrayList<String>();
			repeatedStrList[2] = new ArrayList<String>();
			 
			for(int i=0; i<dataListCount; i++)
             {
			
				int itemRepeatCnt = Collections.frequency(dataList[0],dataList[0].get(i));
				//System.out.println(dataList[0].get(i)+" itemRepeatCnt :> "+itemRepeatCnt);
				
				if(itemRepeatCnt > 1) 
				{
					
					int index = repeatedStrList[0].indexOf(dataList[0].get(i));
					//System.out.println(" index :> "+index);
					
					if(index != -1) 
					{
						
						
						if(repeatedStrList[0].size() == 0) 
						{
							
							repeatedStrList[0].add(dataList[0].get(i));
							repeatedStrList[1].add((""+i).trim());
							
						} 
						else
						if(repeatedStrList[0].size() > 0) 
						{
						
							String str =  repeatedStrList[1].get(index);
							str = str+":"+i;
							//System.out.println(" str :> "+str);
							repeatedStrList[1].set(index, str);
						
						}
						
					} 
					else 
					{
						
						repeatedStrList[0].add(dataList[0].get(i));
						repeatedStrList[1].add((""+i).trim());
						
					}
											
				}
				
			}
			
			int dataCount = repeatedStrList[0].size();
			for(int i=0; i<dataCount; i++) {
				
				String indexes =  repeatedStrList[1].get(i);
				String[] indexList = indexes.split(":");
				int indexListCnt = indexList.length;
				
				for(int k=1; k<indexListCnt; k++)
				{
				
				   	
					dataList[0].set(Integer.parseInt(indexList[k]),repeatedStrList[0].get(i)+"_"+k);
					
			
				}
				
			}
			
			for(int i=0; i<dataListCount; i++) {
				
				//System.out.println(dataList[0].get(i)+"-----> "+dataList[1].get(i));
				dataMap.put(dataList[0].get(i), dataList[1].get(i));
				
			}
		
		
		
		
		return dataMap;
	}	
	

	



	
	
	/*
	 * this method is exclusivley for r41
	 * 5500
	 * 
	 * Nilesh code commented becoz generating new order_customer account number by removing sbi word at the beginng of order accunt no
	 */
/*	public String[] parserOrderCustomerForR41(String data){
		//System.out.println("dataValue: "+dataValue);
		//int nameIndex = dataValue.indexOf("\n");
		
		String Order_Customer[]=data.split("\\n");
		String ACN_NUM="";
		loop:for(int k=Order_Customer[0].length()-1; k>=0; k--)
			{
				   if( Order_Customer[0].charAt(k) >= 48 && Order_Customer[0].charAt(k)<=57)
				   {
					   
					   ACN_NUM=Order_Customer[0].charAt(k)+ACN_NUM;  		   
					   
				   }
				   else
				   {
					   break loop;
				   }
				
			}
			
			System.out.println("Account number is "+ACN_NUM);
		
		
		return Order_Customer;
	}*/
	
	/*
	 * 5561
	 * nilesh old code commented
	 */
/*	public String[] parseBenfCustomerForR41(String data){
		
		String benf_CustDtls[]=data.split("\\n");
		String BEN_ACN_NUM="";
		loop:for(int k=benf_CustDtls[0].length()-1; k>=0; k--)
			{
				   if( benf_CustDtls[0].charAt(k) >= 48 && benf_CustDtls[0].charAt(k)<=57)
				   {
					   
					  BEN_ACN_NUM=benf_CustDtls[0].charAt(k)+BEN_ACN_NUM;  		   
					   
				   }
				   else
				   {
					   break loop;
				   }
				
			}
	}*/
}
