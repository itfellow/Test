package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.omni.component.hibernate.DatabaseUtil;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private static DatabaseUtil dbUtil = new DatabaseUtil();
	
	private static final HashMap<String, String> xmlPacMap = new HashMap<String, String>();
	
	static{
		
		List<Object> data = dbUtil.selectRecord("SELECT pac_name,tranformer_class_name FROM XmlTransformerMap");

		for (Iterator<Object> iterator = data.iterator(); iterator.hasNext();) {
		
			Object[] str =  (Object[]) iterator.next();
			
			xmlPacMap.put(str[0].toString(), str[1].toString());
			
		}
	
		System.out.println("xml pac Map Fetched");
		
	}
	
	public void handler(String pacName){
		
		try {
			
			Transformer tranformer= (Transformer) Class.forName(xmlPacMap.get(pacName)).newInstance();
			
			tranformer.convertToNGRTGS("ds");
		
		} catch (InstantiationException e) {

			e.printStackTrace();
		
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		
		}
		
	}
	
	public static void main(String []ar){


	
	}
	
}
