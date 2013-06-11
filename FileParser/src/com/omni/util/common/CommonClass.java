package com.omni.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.omni.oesb.constants.AppConstants;

public final class CommonClass
{
  /*
   * Convert string "20121012"  (yyyyMMdd) to Data format
   */
  public static Date convertSQLDateFormat(String str) {
	  Date sqlDate = null;
	  SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy/MM/dd");
	  try {
		  if(str.length()== 8){
			  String day = str.substring(6);
			  String month = str.substring(4, 6);
			  String year = str.substring(0, 4);
			  
			  String date = year +"/"+month+"/"+day;
			 
			  
				sqlDate = sqlFormat.parse(date);
			
		  }
		  else if(str.length()== 10){
			  if(str.charAt(4)=='/' && str.charAt(7)=='/'){
				  sqlDate = sqlFormat.parse(str);
			  }
			  
		  }
		  else{
			  System.out.println("cannot convert date");
		  }
	  } catch (ParseException e) {
			e.printStackTrace();
		}
	  return sqlDate;
  }
  
  public static String getSQLDateFormat(String str) {
	  String date = null;
	 
		  if(str.length()== 8){
			  String day = str.substring(6);
			  String month = str.substring(4, 6);
			  String year = str.substring(0, 4);
			  
			  date = year +"/"+month+"/"+day;
			
		  }
		  else if(str.length()== 10){
			  if(str.charAt(4)!='/' && str.charAt(7)!='/'){
				  return null;
			  }
			  
		  }
		  else{
			  System.out.println("cannot convert date");
		  }
	
	  return date;
  }
  
  /**
   * get current time in string format HH:mm:ss
   * @return {@link String}
   */
  public static String getCurrentTimeStr(){
	  String time = null;
	  Date date = new Date();
	  DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	  time = timeFormat.format(date);
	  return time;
  }

  
/*  public static String getCurrentDateStr(){
	  String time = null;
	  Date date = new Date();
	  DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd");
	  time = timeFormat.format(date);
	  return time;
  }*/
  

  public static String formatTimeString(String Time){
	  if(Time!=null && Time.length()==6){
		  String hrs = Time.substring(0, 2);
		  String min = Time.substring(2, 4);
		  String sec = Time.substring(4, 6);
		  Time = hrs+":"+min+":"+sec;
	  }
	  else{
		  Time= getCurrentTimeStr();
	  }
	  
	  return Time;
  }

}