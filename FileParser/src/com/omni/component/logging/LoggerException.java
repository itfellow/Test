/**
  * @(#) LoggerException.java 1.0 04-Feb-2013
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


/**
 * This class used for Handle Exception in the logger it extends Exception Class
 * 
 * @author Vikas Verma.
 * 
 * @version 1.0
 * 
 */


public class LoggerException extends Exception 
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message = "";
	

	/**
	 * Default Constructor of the class
	 * 
	 */ 
	
	public LoggerException() 
	{
		
		
	}
	
	
	/**
	 * Parametrized constructor set the passed message as String
	 * 
	 * @param message Error message as string object
	 * 
	 */
	
	
	public LoggerException(String message) 
	{
		
		super(message);
		
		this.message = message;
		
	}
	
	
	/**
	 * Getter method used to return the error message.
	 * 
	 * @return  Error message as string object
	 * 
	 */ 
	
	
	public String getProblem() 
	{
		
		return message;
			
	}
	

}
