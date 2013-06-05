/**
  * @(#) PropAccess.java 1.0 04-Feb-2013
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

package com.omni.util.common;

import java.util.Properties;
import java.util.ResourceBundle;


/**
 * This Class is used to access property file 
 * by using ResourceBundle.
 *
 * @author Vikas.
 * @version 1.0
 */

public class PropAccess {
	
		/**
		 * Resource Bundle variable.
		 */
		
	    private static ResourceBundle bundle = null;
	    private Properties pro = null;
        
        
	    /**
		 * This method returns values from specified key in Properties file.
		 *
		 * @param 	key parameter against which detail has to be return
		 * @return		returns detail for parameter 'key'
		 * 
		 */
        public String getVal(String key) {
        
            String val = "";
            
            if(pro!=null) {
                
                 val = pro.getProperty(key);
            }
            
          return val;  
        }
        
        
        /**
		 * This method sets details against key specified
		 *
		 * @param String		key against which details needs to be modified
		 * @param String		details that needs to be modified
		 * 
		 */  
	    public void setVal(String key, String value) {
	    	
	        if(pro!=null) {
	        	
	            pro.setProperty(key, value);
	            	            
	        }
	        		        
	    }
        
        
	
		
		/**
		 * This method creates ResourceBundle Object.
		 *
		 * @param 		propFileName name of the properties file
		 * @author		Vikas verma   11/09/2007
		 */
		
		public static ResourceBundle getResourceBundle() {
			
			bundle = ResourceBundle.getBundle("parser");
	    	return bundle;
		}	
	
		
		/**
		 * This method returns values from Properties file.
		 *
		 * @param 		key parameter against which detail has to be return
		 * @return		returns detail for parameter 'key'
		 * @author      Vikas verma   11/09/2007
		 */
		 
		public static String getDetail(String key)	{
			
			String temp = null;
			
			if (bundle != null)	{
				
				 temp  = bundle.getString(key);
			}
			
			return temp.trim();
		}	

}