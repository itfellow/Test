package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private static DatabaseUtil dbUtil = new DatabaseUtil();
	
	private static final HashMap<String, String> xmlPacMap = new HashMap<String, String>();
	
/*	static{
		
		List<Object> data = dbUtil.selectRecord("SELECT pac_name,tranformer_class_name FROM XmlTransformerMap");

		for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
		
			Object[] str =  (Object[]) iterator.next();
			
			xmlPacMap.put(str[0].toString(), str[1].toString());
			
		}
	
		System.out.println("xml pac Map Fetched");
		
	}*/
	
	@SuppressWarnings("unused")
	public void tranformData(HashMap<String, String> headerMap, HashMap<String, String>  msgBodyMap){
		
		try {
			
			String msgTyp = headerMap.get("MSG_SUBTYPE");
			
			
			if(msgTyp!=null){
				
				String pacName=fetchPacName(msgTyp);
				
				if(pacName!=null){
					
					Transformer tranformer= (Transformer) Class.forName(xmlPacMap.get(pacName)).newInstance();
					
					tranformer.convertToNGRTGS(headerMap,msgBodyMap);
					
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
	
	private String fetchPacName(String msgSubTyp){
		
		if(msgSubTyp!=null){
			
			List<Object> pacName = dbUtil.selectRecord(	"SELECT xf.pac_name FROM XmlTransformerMap xf, MessageTypeMst msg " +
														"WHERE msg.xml_id = xf.id AND msg.msg_typ_flag = 'Out' AND msg.series = '"+msgSubTyp+"'");
			
			if(pacName != null && !pacName.isEmpty()){
				return pacName.get(0).toString();
			}
			
		}
		
		return null;
	}
	
	
	public static void main(String []ar){
		
		System.out.println(new TransformerHandler().fetchPacName("r40"));;
	
	}
	
}
