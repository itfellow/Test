package com.omni.oesb.fileparser;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omni.oesb.common.AppConstants;

public class MessageDataFilter {
	
	private  HashMap<String, String> filteredData = null;
	
	private  Set<String> keys = null;
	
	synchronized public  HashMap<String,String> filterMsgBodyData(String msgTyp,HashMap<String,String> msgBody){
		
		filteredData = new HashMap<String, String>();
		
		if(msgTyp.charAt(0) =='N' || msgTyp.charAt(0) == 'n'){

			keys = msgBody.keySet();
			
			for(String code: keys){
				 
				 String code_name = AppConstants.NEFT_CODE_MAP.get(code);
				 
				 String code_value = msgBody.get(code);
				 
				 System.out.println("Value of "+code+"("+code_name+") is: "+code_value);
				 
				 if(code_name!=null){
					 
					 filteredData.put(code_name, code_value);
					 
				 }
				 else{
					 
					 System.out.println("code: "+code+" Not Found in NeftData Map, Contact technical Support");
					 
				 }
				
				 
			 }
			 
			 filteredData = filterNeftData(filteredData);
		}
		else if(msgTyp.charAt(0) == 'R' || msgTyp.charAt(0) == 'r'){
			
			keys = msgBody.keySet();
			
			for(String code: keys){
				 
				 String code_name = AppConstants.RTGS_CODE_MAP.get(code);
				 
				 String code_value = msgBody.get(code);
				 
				 System.out.println("Value of "+code+"("+code_name+") is: "+code_value);
				 
				 if(code_name!=null){
					 
					 filteredData.put(code_name, code_value);
					 
				 }
				 else{
					 
					 System.out.println("code: "+code+" Not Found in NeftData Map, Contact technical Support");
					 
				 }
			}
			
			filteredData = filterRtgsData(filteredData);
			
		}
		return filteredData;
	}
	
	private HashMap<String,String> filterNeftData(HashMap<String, String> dataFilter){
		
		//Formating amt. code:4038
		String currencyAmtStr = dataFilter.get("AMT");
		
		if(currencyAmtStr!=null){
			String []currencyAmt = parseAmount(currencyAmtStr);
			dataFilter.remove("AMT");
			dataFilter.put("CURRENCY", currencyAmt[0]);
			dataFilter.put("AMT", currencyAmt[1]);
		}
		
		//Formating amt. code:4115
		String totAmtRejected = dataFilter.get("TOT_AMT_REJECTED");
				
		if(totAmtRejected!=null){
			String []currencyAmt = parseAmount(totAmtRejected);
			dataFilter.remove("TOT_AMT_REJECTED");
			dataFilter.put("TOT_AMT_REJECTED", currencyAmt[1]);
		}
		
		//Formatting Sum of amt. code:4063
		String sum_amt= dataFilter.get("SUM_AMT");
		
		if(sum_amt!=null){
			String []currencyAmt = parseAmount(sum_amt);
			dataFilter.remove("SUM_AMT");
			dataFilter.put("SUM_AMT", currencyAmt[1]);
		}
		
		//Formatting amt credited, date, time. code:3501
		String AmtCredited_Date_Time = dataFilter.get("AMT_CREDIT_TIME");
		
		if(AmtCredited_Date_Time!=null){
			
			String date = AmtCredited_Date_Time.substring(0,8);
			String time = AmtCredited_Date_Time.substring(7,14);
			
			dataFilter.remove("AMT_CREDIT_TIME");
			
			if(date!=null)
				dataFilter.put("VALUE_DATE", date);
			
			if(time!=null)
				dataFilter.put("TIME", time);
		}
		
		
		return dataFilter;
	}
	
	private HashMap<String,String> filterRtgsData(HashMap<String, String> dataFilter){
		
		/////Format date,currency,amt. code:4488///////
		
		String dateAndCurrency = dataFilter.get("VALUE_DATE_CURR_AMT");
		
		if(dateAndCurrency != null){
			//spliting date , currency typ, amt for insert in Database
			dataFilter.remove("VALUE_DATE_CURR_AMT");
			
			String date = dateAndCurrency.substring(0,8);
			
			String currenyStr = dateAndCurrency.substring(8);
			
			String currencyAmt [] = parseAmount(currenyStr);
			
			//currency type to insert in DB
			dataFilter.put("CURRENCY", currencyAmt[0]);
			
			//Amount to insert in DB
			dataFilter.put("AMT", currencyAmt[1]);
			
			//Date to insert in DB
			dataFilter.put("VALUE_DATE", date);
		}
		
		/////Formating order customer. code:5500//////
		
		String order_CustDtls[] = parserOrderCustomerForR41(dataFilter.get("ORDRNG_CUST"));
		
		if(order_CustDtls!=null && order_CustDtls.length>0){
			//Split acount no , acount name, adrs from order_customer
			
			//customer account no
			dataFilter.put("CUST_ACNT_NO", order_CustDtls[0].replace("/", "").trim());
			//customer account name
			dataFilter.put("CUST_ACNT_NAME", order_CustDtls[1]);
			//customer adrs
			dataFilter.put("CUST_ADRS", order_CustDtls[2].trim()+" "+order_CustDtls[3].trim());
		}
		
		////Formating Benf customer. code:5561/////
		
		String benf_CustDtls[] = parseBenfCustomerForR41(dataFilter.get("BENF_CUST"));
		
		if(benf_CustDtls!=null){
			
			if(benf_CustDtls.length > 4){
				
				dataFilter.put("BENF_ACNT_NO", benf_CustDtls[0]);
				
				dataFilter.put("BENF_ACNT_NAME", benf_CustDtls[1]);
				
				StringBuffer benfAdrsBuffer = new StringBuffer();
				
				for(int i=2; i < benf_CustDtls.length ; i++){
					benfAdrsBuffer.append(benf_CustDtls[i]+" ");
				}
				
				String benfAdrs = benfAdrsBuffer.toString();
				
				if(benfAdrs!=null && benfAdrs.length() > 0){
					dataFilter.put("BENF_ADRS", benfAdrs);
				}
			}
			else if(benf_CustDtls.length > 2){
				
				dataFilter.put("BENF_ACNT_NO", benf_CustDtls[0]);
				
				String benfAdrs = benf_CustDtls[1];
				
				if(benfAdrs!=null && benfAdrs.length() > 0){
					dataFilter.put("BENF_ADRS", benfAdrs);
				}
			}
		}
		
		
		return dataFilter;
	}
	
	
	
	
	
	/***************Filter Utilities*********************************/
	/*
	 * this method is exclusivley for r41
	 * 5561
	 */
	public String[] parseBenfCustomerForR41(String data){
		
		String benf_CustDtls[]= null;
		if(data!=null){
			benf_CustDtls=data.split("\\n");
			
			if(benf_CustDtls[0].charAt(0) == '/')
				benf_CustDtls[0] = benf_CustDtls[0].replace("/", "");

		}

		return benf_CustDtls;
	}
	
	/*
	 * this method is exclusivley for r41
	 * 5500
	 */
	private String[] parserOrderCustomerForR41(String data){
		//System.out.println("dataValue: "+dataValue);
		//int nameIndex = dataValue.indexOf("\n");
		String order_Customer[] = null;
		if(data!=null){
			order_Customer=data.split("\\n");		
		}
		
		return order_Customer;
	}
	
	private String[] parseAmount(String amt){
		
		String[] amtSplit = null;
		
		if(amt !=null && amt.length() > 0){
			try {
				amtSplit = parseCurrAmtString(amt);
			} catch (Exception e) {

				amtSplit = null;
				
				e.printStackTrace();
			}
			
		}

		return amtSplit;
	}
	
	private  String[] parseCurrAmtString(String datCurVal) throws Exception  {

        String a[];
        
		try {
			
			a = new String[2];
			String cur="[a-zA-z]+";
			String val="[1-9]+";
			             
			//System.out.println("datCurVal =======> "+datCurVal);
			
			Pattern p_cur=Pattern.compile(cur);
			Matcher m_cur=p_cur.matcher(datCurVal);   

			Pattern p_val=Pattern.compile(val);
			Matcher m_val=p_val.matcher(datCurVal);   

			m_cur.find();
			m_val.find();
			/*m_val.start();
			m_val.end();              
			m_val.find();*/
			
			a[0]=datCurVal.substring(m_cur.start(),m_cur.end());
			a[1]=datCurVal.substring(m_val.start());
			
//			a[1] = a[1].replaceAll("", "");
			a[1] = a[1].replaceAll(",", ".");
			//System.out.println("a[0] ===> "+a[0]);
			//System.out.println("a[1] ===> "+a[1]);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Exception thrown by datCurValParser : ParserHelper"+e);
			
		}

        return a;

    }
}
