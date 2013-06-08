package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.List;

import com.omni.component.hibernate.DataBaseUtility;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private static DataBaseUtility dbUtil = new DataBaseUtility();
	
	private static final HashMap<String, String> xmlPacMap = new HashMap<String, String>();
	
	public TransformerHandler(){
		
		List<Object> data = dbUtil.selectRecord("SELECT pac_name FROM XmlTransformerMap");
		
	}
	
	public void handler(){
		
		try {
			
			Transformer tranformer= (Transformer) Class.forName("com.omni.oesb.fileparser.transformer.pac.TransformerPac813").newInstance();
			
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

		new TransformerHandler();
		
	}
	
}
