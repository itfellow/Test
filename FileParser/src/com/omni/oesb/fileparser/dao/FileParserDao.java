package com.omni.oesb.fileparser.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.omni.component.hibernate.DataBaseUtility;
import com.omni.component.hibernate.HibernateUtil;
import com.omni.component.logging.FileLogger;
import com.omni.oesb.constants.ParserConstants;

public final class FileParserDao extends DataBaseUtility {
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	/**
	 * check whether msg is outgoing,incoming or ack typ<br>
	 * example pass N06 string it will return Out<br>
	 * (Possible return values check DB table Message_type_mst table)<br>
	 * 
	 * @param msgSubTyp
	 * @return {@link String} 
	 */
	
	public String checkMsgTyp(String msgSubTyp,String IO_identifier){
		String msgTyp = null;
		
		if(msgSubTyp != null && IO_identifier != null){
			try{
				msgTyp = (String) selectRecord("SELECT msg_typ_flag FROM MessageTypeMst WHERE series = '"+msgSubTyp+"' and io_identifier ='"+IO_identifier+"'").get(0);
			}
			catch(Exception e){
				e.printStackTrace();
				fileLogger.writeLog("sevre", "Error Fetching Type Of Message to Parse From Database class:NotificationDao, Method:checkMsgTyp");
			}
		}
		
		return msgTyp;
	}
	
	/**
	 * Perform Batch Insert Opertion and return parser status after the operation
	 * @param tablepojo
	 * @return {@link String}
	 */
	public String insertOesbTransactions(final Object[] tablepojo) {
		
		String parserStatus = ParserConstants.MSG_PARSE_ERROR;
		
		Session sessionForInsertPojo = null;
		Transaction trForInsertPojo = null;
		
		try {
			sessionForInsertPojo = HibernateUtil.getSessionFactory().openSession();
			trForInsertPojo = sessionForInsertPojo.beginTransaction();
			
			for (Object object : tablepojo) {
				sessionForInsertPojo.save(object);
				System.out.println(object.toString() + " saved");
			}
			
			trForInsertPojo.commit();
			parserStatus = ParserConstants.MSG_PARSE_SUCCESS;
			fileLogger.writeLog("info", "Batch Insert Completed Successfully");
			
		}catch(ConstraintViolationException e){
			parserStatus = ParserConstants.MSG_PARSE_ERROR;
			e.printStackTrace();
			if(trForInsertPojo!=null){
				trForInsertPojo.rollback();
			}
			System.out.println("DB Insert Failed: Constrain violation");
			fileLogger.writeLog("severe", e.getMessage());
			fileLogger.writeLog("info", "Duplicate Message Detected, Batch Insert Cannot be performed");
		}
		catch (HibernateException e) {
			parserStatus = ParserConstants.MSG_PARSE_ERROR;
			e.printStackTrace();
			if(trForInsertPojo!=null){
				trForInsertPojo.rollback();
			}
			System.out.println("DB Insert Failed");
			fileLogger.writeLog("severe", e.getMessage());
			fileLogger.writeLog("severe", "DB Insert Failed Because of Following Error");
		

		}
		finally {
			
			if(sessionForInsertPojo!=null && sessionForInsertPojo.isOpen()){
				sessionForInsertPojo.close();
			}
		}
		
		return parserStatus;
	}
	
	/**
	 * Perform Batch Update Operation and return parserStatus
	 * @param tablepojo
	 * @return {@link String}
	 */
	public String updateOesbRecord(final Object[] tablepojo) {
		
		String parserStatus = ParserConstants.MSG_PARSE_ERROR;
		
		Session sessionForUpdatePojo = null;
		Transaction trForUpdatePojo = null;
		
		try {
			
			sessionForUpdatePojo = HibernateUtil.getSessionFactory().openSession();
			trForUpdatePojo = sessionForUpdatePojo.beginTransaction();
			
			for (Object object : tablepojo) {
				sessionForUpdatePojo.update(object);
				System.out.println(object + " updated");
			}
			
			trForUpdatePojo.commit();
			parserStatus = ParserConstants.MSG_PARSE_SUCCESS;
			fileLogger.writeLog("info", "Batch Update Completed Successfully");
		} catch (HibernateException e) {
			parserStatus = ParserConstants.MSG_PARSE_ERROR;
			e.printStackTrace();
			
			if (trForUpdatePojo != null){
				trForUpdatePojo.rollback();
			}
			
			System.out.println("Batch Update Failed");
			fileLogger.writeLog("severe", e.getMessage());
			fileLogger.writeLog("severe", "DB Update Failed Because of Following Error");
			
		} finally {
			if (sessionForUpdatePojo!=null && sessionForUpdatePojo.isOpen()){
				sessionForUpdatePojo.close();
			}
		}
		return parserStatus;
	}
}
