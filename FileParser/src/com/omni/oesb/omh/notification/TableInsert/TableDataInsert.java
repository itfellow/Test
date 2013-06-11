package com.omni.oesb.omh.notification.TableInsert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.constants.AppConstants;
import com.omni.oesb.notification.TableInsert.vo.CommonAckStatusVo;
import com.omni.oesb.notification.TableInsert.vo.CommonTransactionDtlsVo;
import com.omni.oesb.omh.notification.data.MessageHeader;
import com.omni.oesb.omh.notification.data.N06Data;
import com.omni.oesb.omh.notification.data.N09Data;
import com.omni.oesb.omh.notification.data.N10Data;
import com.omni.oesb.omh.notification.data.R09Data;
import com.omni.oesb.omh.notification.data.R41Data;
import com.omni.oesb.omh.notification.data.R42Data;
import com.omni.oesb.omh.notification.data.TransactionDtls;
import com.omni.util.common.CurrentDate;

public class TableDataInsert extends TableData {
	  
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	/**
	 * Insert Of CSV FileData is Initiated From this Method.<br>
	 * Note: CSV File Will Only be considered for Insert Of ACK type NEFT Message
	 */
	public  String insertcsvdata(ArrayList<String> CSV_Data[] ){

		String parseStatus=AppConstants.MSG_PARSE_ERROR;
		
		try{
			
			String transId = CSV_Data[1].get(0);
			String credtitTime = CSV_Data[1].get(1);
			
			if(nfDao.checkId(N06Data.class, transId)){
				if(!nfDao.checkId(N10Data.class, transId)){
					N10Data n10 = new N10Data();
					
					credtitTime = credtitTime.replace(".", ":");
					
					n10.setAmt_credited_date(new CurrentDate().toString());
					n10.setAmt_credited_time(credtitTime);
					n10.setTransaction_refId(transId);
					Object []tablePojo = new Object[1];
					tablePojo[0] = n10;
					parseStatus = nfDao.insertOesbTransactions(tablePojo);
					
					if(parseStatus.equals(AppConstants.MSG_PARSE_SUCCESS)){
						TransactionDtls transDtls = (TransactionDtls) nfDao.getObject(TransactionDtls.class,transId);
						transDtls.setTransaction_status("2");
						transDtls.setMessage_type("N10");
						transDtls.setTransCompleteDate(new CurrentDate());
						transDtls.setTransCompTime(credtitTime);
						tablePojo[0] = transDtls;
						parseStatus = nfDao.updateOesbRecord(tablePojo);
					}
					else{
						fileLogger.writeLog("warning", "CSV-NEFT ACK:Update Failed");
					}
				}
				else{
					System.out.println("CSV-NEFT ACK:AckMsg Already Recieved for this TransRefId");
					System.out.println("cannot accept another ack with Duplicate TransRefId:"+transId);
					
					fileLogger.writeLog("warning", "CSV-NEFT ACK:AckMsg Already Recieved for this TransRefId");
					fileLogger.writeLog("warning", "cannot accept another ack with Duplicate TransRefId:"+transId);
					
				}
			}
			else{
				System.out.println("N10: AckMsg Received is not Valid, TransRefId :"+transId);
				System.out.println("Reason: Details of Ack Msg Not Found in N06 Table");
				
				fileLogger.writeLog("warning", "N10: AckMsg Received is not Valid, TransRefId :"+transId);
				fileLogger.writeLog("warning", "Reason: Details of Ack Msg Not Found in N06 Table");
			
			}
			

			
			
			
			
		}
		catch(Exception e){
			
		}
		
		
		
		return parseStatus;
		
	}
	
	
	/**
	 * Insert Of TXT FileData is Initiated From this Method
	 */
	public String insertTxtData(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap) throws Exception{
		

		String msgSubTyp = headerMap.get("MSG_SUBTYPE");
		
		fileLogger.writeLog("info", "Insert Operation For "+msgSubTyp+" Started");
		
		String parseStatus = AppConstants.MSG_PARSE_ERROR;
		
		try{
			if(headerMap != null && !headerMap.isEmpty() && msgBodyMap != null && !msgBodyMap.isEmpty()){
				
				String IO_identifier = headerMap.get("IO_ID");
				String msgTyp = nfDao.checkMsgTyp(msgSubTyp,IO_identifier);
				
				if(msgTyp != null){
					if(msgTyp.equalsIgnoreCase("Out")){
						parseStatus = insertForOutGoingMsg(msgSubTyp,headerMap, msgBodyMap);
					}
					else if(msgTyp.equalsIgnoreCase("Ack_Msg")){
						if(msgSubTyp.charAt(0)=='R' || msgSubTyp.charAt(1)=='r'){
							parseStatus = insertRtgsAckTypMsg( headerMap, msgBodyMap);
						}
						else if(msgSubTyp.charAt(0)=='N' || msgSubTyp.charAt(1)=='n'){
							parseStatus = insertNeftAckTypMsg(headerMap, msgBodyMap);
						}
					}
					else if(msgTyp.equalsIgnoreCase("In")){
						parseStatus = AppConstants.MSG_PARSE_IGNORED;
						
						System.out.println("System has Detected Incoming Message, Cannot Insert to DB");
						System.out.println("Moving file to ignore...");
						
						fileLogger.writeLog("warning","System has Detected Incoming Message Insert Operation Cancelled, Contact technical team");
						
					}
					
				}
				else{
					
					fileLogger.writeLog("severe","Msg Typ returned NULL from Databse");
					fileLogger.writeLog("severe","Insert Operation Failed Msg Typ not recognised, Check Database");
				}
				
			}
			else{
				System.out.println("Cannot insertData NULL value in method parameter method:insertData");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Hibernate Object initialization error");
			
			fileLogger.writeLog("severe",e.getMessage());
			fileLogger.writeLog("severe", "Error Occurred in main insert Method: insertData() of txt File");
			
		}
		return parseStatus;
	}
	
	
	
	
	
	
	@SuppressWarnings("unused")
	private String insertNeftAckTypMsg(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
		String parseStatus = AppConstants.MSG_PARSE_ERROR;
		
		Object[]tablePojo = null;
		String transId = null;
		short updateFlag = 1;
		
		if(headerMap != null && !headerMap.isEmpty() && msgBodyMap != null && !msgBodyMap.isEmpty()){
			String msgSubTyp = headerMap.get("MSG_SUBTYPE");
			transId = msgBodyMap.get("TRANS_REF_ID");
			
			if(msgSubTyp.equalsIgnoreCase("N10")){
				if(nfDao.checkId(N06Data.class, transId)){
					if(!nfDao.checkId(N10Data.class, transId)){
						Long headerId = null;
						try{
							headerId = (Long) nfDao.selectRecord("SELECT header_id FROM MessageHeader  " +
																	"WHERE transaction_refno = '"+transId+"'").get(0);
						}
						catch(IndexOutOfBoundsException e){
							fileLogger.writeLog("warning", "N10: MsgHeaderId Not Found");
						}
						tablePojo = setN10(headerId,msgBodyMap);
					}
					else{
						updateFlag=0;
						System.out.println("N10:AckMsg Already Recieved for this TransRefId");
						System.out.println("cannot accept another ack with Duplicate TransRefId:"+transId);
						
						fileLogger.writeLog("warning", "N10:AckMsg Already Recieved for this TransRefId");
						fileLogger.writeLog("warning", "cannot accept another ack with Duplicate TransRefId:"+transId);
						
					}
				}
				else{
					updateFlag=0;
				
					System.out.println("N10: AckMsg Received is not Valid, TransRefId :"+transId);
					System.out.println("Reason: Details of Ack Msg Not Found in N06 Table");
						
					fileLogger.writeLog("warning", "N10: AckMsg Received is not Valid, TransRefId :"+transId);
					fileLogger.writeLog("warning", "Reason: Details of Ack Msg Not Found in N06 Table");
					
				}
			}
			else if(msgSubTyp.equalsIgnoreCase("N09")){
				if(nfDao.checkId(N06Data.class, transId)){
					if(!nfDao.checkId(N09Data.class, transId)){
						Long headerId = null;
						try{
							headerId = (Long) nfDao.selectRecord("SELECT header_id FROM MessageHeader  " +
																	"WHERE transaction_refno = '"+transId+"'").get(0);
						}
						catch(IndexOutOfBoundsException e){
							System.out.println();
							fileLogger.writeLog("warning","N09: MsgHeaderId Not Found");
						}
						tablePojo = setN09(headerId,msgBodyMap);
					}
					else{
						updateFlag=0;
						System.out.println("N09:AckMsg Already Recieved for this TransRefId");
						System.out.println("cannot accept another ack with Duplicate TransRefId:"+transId);
						
						fileLogger.writeLog("warning", "N09:AckMsg Already Recieved for this TransRefId");
						fileLogger.writeLog("warning", "cannot accept another ack with Duplicate TransRefId:"+transId);
					}
				}
				else{
					updateFlag=0;
					System.out.println("N09: AckMsg Received is not Valid, TransRefId :"+transId);
					System.out.println("Reason: Details of Ack Msg Not Found in N06 Table");
					
					fileLogger.writeLog("warning", "N09: AckMsg Received is not Valid, TransRefId :"+transId);
					fileLogger.writeLog("warning", "Reason: Details of Ack Msg Not Found in N06 Table");
				}
			}
			
			if(updateFlag==1){
				
				Object headerTablePojo = setMessageHeader(headerMap, transId);
				Object[] tableToInsert = new Object[2];
				tableToInsert[0] = headerTablePojo;
				tableToInsert[1] = tablePojo[0];
				
				try{
					if(tableToInsert[0]!=null && tableToInsert[1]!=null && transId != null && transId.trim().length()>0){
						
						updateFlag = 0;
							
						TransactionDtls transDtls = (TransactionDtls) nfDao.getObject(TransactionDtls.class,transId);
						CommonAckStatusVo commonAck = (CommonAckStatusVo) tablePojo[1];
							
						if(commonAck.getStatus().equalsIgnoreCase("Y")){
							updateFlag = 1;	
							transDtls.setMessage_type("N10");
							transDtls.setTransaction_status("2");
							
						}
						else if(commonAck.getStatus().equalsIgnoreCase("N")){
							updateFlag = 1;
							transDtls.setMessage_type("N09");
							transDtls.setTransaction_status("3");
						}
						
						if(updateFlag == 1){
							transDtls.setTransCompleteDate(new CurrentDate());
							String time = commonAck.getTime();
							transDtls.setTransCompTime(time);
							
							parseStatus=nfDao.insertOesbTransactions(tableToInsert);
							Object[] tableToUpdate = new Object[1];				// even though its update of single table batch update is
																				// written here beCAUSE in future(after Liquidity Manager) there may 
																				// to perform bacth update here 
							tableToUpdate[0] = transDtls;
						
							parseStatus=nfDao.updateOesbRecord(tableToUpdate);
						}
						else{
							System.out.println(msgSubTyp+": Error Updating Data for TransRefId: "+transId);
							System.out.println("Reason: ACK Indicator Not Proper");
							
							fileLogger.writeLog("severe", msgSubTyp+": Error Updating Data for TransRefId: "+transId);
							fileLogger.writeLog("severe", "Reason: ACK Indicator Not Proper");
						}
						
					}
					else{
						System.out.println("Table to Insert Found NULL");
						
						fileLogger.writeLog("severe", "Table Data For Batch Insert Found Null");
						fileLogger.writeLog("severe", "Class: TableDataInsert method: insertNeftAckTypMsg");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					
					fileLogger.writeLog("severe", e.getMessage());
					fileLogger.writeLog("severe", "Exception Ocurrend in class: TableDataInsert Method: insertNeftAckTypMsg");
				}
			}
			
		}
		else{
			System.out.println("HeaderMap or MsgBody Recieved is Empty");
		}
		return parseStatus;
	}
	
	
	
	
	
	private String insertRtgsAckTypMsg(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
		
		String parseStatus = AppConstants.MSG_PARSE_ERROR;
		
		Object[]tablePojo = new Object[2];				// array r09 and common data table
		String UTR = null;
		short updateFlag = 1;
		
		if(headerMap != null && !headerMap.isEmpty() && msgBodyMap != null && !msgBodyMap.isEmpty()){
			
			String msgSubTyp = headerMap.get("MSG_SUBTYPE");
			UTR = headerMap.get("UTR");
			if(msgSubTyp.equalsIgnoreCase("R09")){
				if(nfDao.checkId(R41Data.class, UTR)){
					if(!nfDao.checkId(R09Data.class, UTR)){
						
						Long headerId = null;
						try{
							headerId = (Long) nfDao.selectRecord("SELECT header_id FROM MessageHeader  " +
																	"WHERE unique_transaction_reference = '"+UTR+"'").get(0);
						}
						catch(IndexOutOfBoundsException e){
							fileLogger.writeLog("warning", "R09: MsgHeaderId Not Found");
						}
						
						tablePojo = setR09(headerId,UTR, msgBodyMap);
					}
					else{
						updateFlag=0;
						System.out.println("R09:AckMsg Already Recieved for this UTR No.");
						System.out.println("cannot accept another ack with Same UTR No, UTR No:"+UTR);
						
						fileLogger.writeLog("warning", "R09:AckMsg Already Recieved for this UTR No.");
						fileLogger.writeLog("warning", "cannot accept another ack with Same UTR No, UTR No:"+UTR);
					}
				}
				else{
					updateFlag=0;
					System.out.println("AckMsg Received is not Valid, UTR No:"+UTR);
					System.out.println("Reason: Details of Ack Msg Not Found in R41 Table");
				
					fileLogger.writeLog("warning", "AckMsg Received is not Valid, UTR No:"+UTR);
					fileLogger.writeLog("warning", "Reason: Details of Ack Msg Not Found in R41 Table");
				}
			}
			
			if(updateFlag==1){
				Object headerTablePojo = setMessageHeader(headerMap, UTR);
				Object[] tableToInsert = new Object[2];
				tableToInsert[0] = headerTablePojo;
				tableToInsert[1] = tablePojo[0];
				try{
					if(tableToInsert[0]!=null && tableToInsert[1]!=null && UTR != null && UTR.trim().length()>0){
						
						updateFlag = 0;
							
						TransactionDtls transDtls = (TransactionDtls) nfDao.getObject(TransactionDtls.class,UTR);
						CommonAckStatusVo commonAck = (CommonAckStatusVo) tablePojo[1];
						
						
						if(commonAck.getStatus().equalsIgnoreCase("Y")){
							updateFlag = 1;	
							transDtls.setMessage_type("R09-ACK");	//hard coded need to change
							transDtls.setTransaction_status("2");
							
						}
						else if(commonAck.getStatus().equalsIgnoreCase("N")){
							updateFlag = 1;
							transDtls.setMessage_type("R09-NACK"); //hard coded need to change
							transDtls.setTransaction_status("3");
						}
						
						if(updateFlag == 1){
							transDtls.setTransCompleteDate(new CurrentDate());
							String time = commonAck.getTime();
							transDtls.setTransCompTime(time);
							
							parseStatus=nfDao.insertOesbTransactions(tableToInsert);
							Object[] tableToUpdate = new Object[1];				// even though its update of single table batch update is
																				// written here beCAUSE in future(after Liquidity Manager) there may 
																				// to perform bacth update here 
							tableToUpdate[0] = transDtls;
						
							parseStatus = nfDao.updateOesbRecord(tableToUpdate);
						}
						else{
							System.out.println(msgSubTyp+": Error Updating Data for UTR No: "+UTR);
							System.out.println("Reason: ACK Indicator Not Proper");
						
							fileLogger.writeLog("warning", msgSubTyp+": Error Updating Data for UTR No: "+UTR);
							fileLogger.writeLog("warning", "Reason: ACK Indicator Not Proper");
						}
						
					}
					else{
						System.out.println("Table to Insert Found NULL");
						
						fileLogger.writeLog("severe", "Table Data For Batch Insert Found Null");
						fileLogger.writeLog("severe", "Class: TableDataInsert method: insertRtgsAckTypMsg");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					fileLogger.writeLog("severe", e.getMessage());
					fileLogger.writeLog("severe", "Error While Insert/update or Batch Insert/update Operation insertRtgsAckTypMsg");
				}
			}
		}
			
		
		return parseStatus;
	}
	
	
	
	
	
	@SuppressWarnings("unused")
	private String insertForOutGoingMsg(String msgTyp,HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
		
		String parseStatus = AppConstants.MSG_PARSE_ERROR;
		
		if(headerMap != null && !headerMap.isEmpty() && msgBodyMap != null && !msgBodyMap.isEmpty()){
			
			Object tablePojo[] = null;				// array Message(N06,R41) Table and commonTransactionDtls object
			Object messageTablePojo = null;
			Object headerTablePojo = null;
			Object transactTablePojo = null;
			Object msgNotifTablePojo = null;
			int insert_flag = 1;
			
			String transId = msgBodyMap.get("TRANS_REF_ID");
			
			if(msgTyp.equalsIgnoreCase("n06") && transId!=null){
				if(!nfDao.checkId(N06Data.class, transId)){
					
						tablePojo = setN06(headerMap,msgBodyMap);
					
				}
				else{
					insert_flag = 0;
					
					if(transId!=null){
						fileLogger.writeLog("warning", "Duplicate N06 Message Detected, N06 MSG with transRefId: "+transId+" Already Present in table");
					}
					else{
						fileLogger.writeLog("warning", "Cannot Insert N06 TransId Missing");
					}
				}
				
			}
			else if(msgTyp.equalsIgnoreCase("r41")){
				if(!nfDao.checkId(R41Data.class, headerMap.get("UTR"))){
					tablePojo = setR41(headerMap,msgBodyMap);	
					
				}
				else{
					insert_flag = 0;
					fileLogger.writeLog("warning", "Duplicate R41 Message Detected, R41 MSG with UTR No.: "+headerMap.get("UTR")+" Already Present in table");
				}
				
			}
			else if(msgTyp.equalsIgnoreCase("r42")){
				if(!nfDao.checkId(R42Data.class, headerMap.get("UTR"))){
					tablePojo = setR42(headerMap,msgBodyMap);	
					
				}
				else{
					insert_flag = 0;
					fileLogger.writeLog("warning", "Duplicate R42 Message Detected, R42 MSG with UTR No.: "+headerMap.get("UTR")+" Already Present in table");
				}
				
			}
			
			
			if(tablePojo != null && tablePojo.length == 2 && insert_flag == 1){
				messageTablePojo =  tablePojo[0];
				CommonTransactionDtlsVo common = (CommonTransactionDtlsVo) tablePojo[1];
				transactTablePojo = setTransactionDtls(headerMap, common);
				headerTablePojo = setMessageHeader(headerMap,common.getTransId());
				msgNotifTablePojo = setMessageNotificationDtls(common.getTransId(),msgTyp);
				
				Object[] tableToInsert = new Object[4];
				tableToInsert[0] = headerTablePojo;
				tableToInsert[1] = messageTablePojo;
				tableToInsert[2] = transactTablePojo;
				tableToInsert[3] = msgNotifTablePojo;
				try{
					if(tableToInsert[0]!=null && tableToInsert[1]!=null && tableToInsert[2]!=null && tableToInsert[3]!=null){
						parseStatus=nfDao.insertOesbTransactions(tableToInsert);
					}
					else{
						System.out.println("Table to Insert Found NULL");
						
						fileLogger.writeLog("severe", "Table Data For Batch Insert Found Null");
						fileLogger.writeLog("severe", "Class: TableDataInsert method: insertForOutGoingMsg");
					}
					
				}
				catch(Exception e){
					e.printStackTrace();
					fileLogger.writeLog("severe", e.getMessage());
					fileLogger.writeLog("severe", "Error While Insert or Batch Insert Operation Method:insertForOutGoingMsg");
				}
				
			}
		}

		return parseStatus;
	}
}
