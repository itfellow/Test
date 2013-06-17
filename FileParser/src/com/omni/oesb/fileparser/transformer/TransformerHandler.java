package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private final static DatabaseUtil dbUtil = new DatabaseUtil();
	
	
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
					throw new Exception("pacName cannot be Null");
				}
				
			}
			else{
				throw new Exception("msgTypId Cannot be NULL");
			}
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
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
	
	
	
	public static void main(String[] args) {
		HashMap<String , String> map = new HashMap<String, String>();
		map.put("ORGIN_DT","2013/05/08".replace("/", ""));
		map.put("SNDR_IFSC","VSBL0000012");
		map.put("UTR", "VSBLH13128000024");
//		new TransformerHandler().generateNgrtgsTransId('R', map);
	}

}
