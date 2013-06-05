/**
  * @(#) FileLogFormatter.java 1.0 04-Feb-2013
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
 * FileLogFormatter formats the LogRecord as follows:
 * date   level   localized message with parameters 
 */

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileLogFormatter extends Formatter
{
	
	/**
	 * This class constructor calls the Formatter class constructor
	 * 
	 * 
	 * */
	
	public FileLogFormatter()
	{
		super();
	}
	
	
	/**
     * Method provides the log file format
     * @return String       returns log file format
     * @param  LogRecord    gets a log record for setting the log file format
     * 
     */  
	
	@Override
	public String format(LogRecord record) 
	{
		
		// Create a StringBuffer to contain the formatted record
		// start with the date.
		
		StringBuffer sb = new StringBuffer();
		
		// Get the date from the LogRecord and add it to the buffer
		
		Date date = new Date(record.getMillis());
		sb.append(date.toString());
		sb.append(" ");
		
		// Get the level name and add it to the buffer
		sb.append(record.getLevel().getName());
		sb.append(" ");
		 
		// Get the formatted message (includes localization 
		// and substitution of paramters) and add it to the buffer
		
		sb.append(formatMessage(record));
		sb.append(System.getProperty("line.separator"));
		

		return sb.toString();
	}

}
