package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.component.logging.FileLogger;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private final static DatabaseUtil dbUtil = new DatabaseUtil();
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	public void tranformData(HashMap<String, String> headerMap, HashMap<String, String>  msgBodyMap){
		
		try {
			
			String msgTyp = headerMap.get("MSG_SUBTYPE");
			
			if(msgTyp!=null){
				
				String[] pacDtls=fetchPacName(msgTyp);
				
				if(pacDtls!=null && pacDtls.length == 3 ){
					
					Transformer tranformer= (Transformer) Class.forName(pacDtls[1]).newInstance();
					
					tranformer.convertToNGRTGS(pacDtls[0], pacDtls[2], headerMap, msgBodyMap);
						
					
				}
				else{
					fileLogger.writeLog("warning", "pac Not Found for Transformation");
					throw new Exception("pacName cannot be Null");
				}
				
			}
			else{
				fileLogger.writeLog("warning", "Cannot Transform Message Type: "+msgTyp);
				throw new Exception("msgTypId Cannot be NULL");
			}
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
			fileLogger.writeLog("severe", e.getMessage());
			fileLogger.writeLog("severe", "Error Occured in Tranformation Operation.");
			fileLogger.writeLog("severe", "Application Shutdown");
			System.exit(0);
			
		}
		
	}
	
	private String[] fetchPacName(String msgSubTyp){
		
		if(msgSubTyp!=null){
			
			List<Object> pacName = dbUtil.selectRecord(	"SELECT xf.pac_name, xf.tranformer_class_name, xf.business_service_rule FROM XmlTransformerMap xf, MessageTypeMst msg " +
														"WHERE msg.xml_id = xf.id AND msg.msg_typ_flag = 'Out' AND msg.series = '"+msgSubTyp+"'");
			
			if(pacName != null && !pacName.isEmpty()){
				
				String[] pacDtls = new String[3];
				for (Iterator<Object> iterator = pacName.iterator(); iterator.hasNext();) {
					
					Object []object =  (Object[]) iterator.next();
					
					pacDtls[0] = object[0].toString();
					pacDtls[1] = object[1].toString();
					pacDtls[2] = object[2].toString();
					
				}
				return pacDtls;
				
			}
			
		}
		
		return null;
	}

}
