package com.omni.oesb.fileparser.transformer;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * This Class Provider Utilities Required for Transformer Classes
 * @author Shinoj Shayin
 *
 */
public final class TransformerUtil {
	/**
	 * Convert String Date Time to XMLGregorianCalendar Format
	 * @param date
	 * @param time
	 * @return {@link XMLGregorianCalendar}
	 */
	public static XMLGregorianCalendar convertToXMLGregorianDateTime(String date,String time,boolean isNormailize){
		
		XMLGregorianCalendar dateTime = null;
		
		if(date!=null && time!=null){
			date = date.replace("/", "");
			int day = Integer.parseInt(date.substring(6));
			int month = Integer.parseInt(date.substring(4, 6));
			int year = Integer.parseInt(date.substring(0, 4));
			
			try{
				
				//Set Creation Date here
				GregorianCalendar gregorianDateTime = new GregorianCalendar(year,month,day);		
				
				time = time.replace(":", "");
				
				String hrsStr = time.substring(0, 2);
				
				if(hrsStr.startsWith("0")){
					
					hrsStr = hrsStr.substring(1);
					
				}
				int hrs = Integer.parseInt(hrsStr);
				
				int min = Integer.parseInt(time.substring(2, 4));
				
				//Set Creation Time Starts here
				Calendar cal = Calendar.getInstance();
					// set Hours
					cal.set(Calendar.HOUR_OF_DAY,hrs);
					// set Minute
					cal.set(Calendar.MINUTE,min);
					// set Seconds
					cal.set(Calendar.SECOND,0);
					
				gregorianDateTime.setTime(cal.getTime());
				dateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDateTime);
				dateTime.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return dateTime;
	}
	
	/**
	 * Convert only String Date to XMLGregorianCalendar Formate
	 * @param Date
	 * @return {@link XMLGregorianCalendar}
	 */
	public static XMLGregorianCalendar convertToXMLGregorianDate(String Date){
		
		XMLGregorianCalendar dateTime = null;
		if(Date!=null){
			
			int day = Integer.parseInt(Date.substring(6));
			int month = Integer.parseInt(Date.substring(4, 6));
			int year = Integer.parseInt(Date.substring(0, 4));
			
			try{
				
				//Set Creation Date here
				GregorianCalendar gregorianDateTime = new GregorianCalendar(year,month,day);
				dateTime = DatatypeFactory.newInstance()
					    .newXMLGregorianCalendar(gregorianDateTime);
				dateTime.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setHour(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setMinute(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setSecond(DatatypeConstants.FIELD_UNDEFINED);
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return dateTime;
	}
}
