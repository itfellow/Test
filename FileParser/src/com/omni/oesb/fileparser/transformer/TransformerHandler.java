package com.omni.oesb.fileparser.transformer;

import java.util.HashMap;
import java.util.List;

import com.omni.component.hibernate.DataBaseUtility;
import com.omni.oesb.fileparser.transformer.pac.Transformer;

public class TransformerHandler {
	
	private static DataBaseUtility dbUtil = new DataBaseUtility();
	
	private static final HashMap<String, String> xmlPacMap = new HashMap<String, String>();
	
	public TransformerHandler(){
		
		List<Object> data = dbUtil.selectRecord("SELECT ");
		
	}
	
	public void handler(){
		
		try {
			
			Transformer obj= (Transformer) Class.forName("com.omni.oesb.fileparser.transformer.pac.TransformerPac813").newInstance();
			obj.convertToNGRTGS("ds");
		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String []ar){
		
		new TransformerHandler();
		
	}
	
}
