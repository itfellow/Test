package com.omni.oesb.fileparser.transformer.adapter;

import java.util.ResourceBundle;

import com.omni.component.logging.FileLogger;
import com.omni.util.common.PropAccess;

public class TxtToXmlTransformationAdater implements Runnable {

	private final  FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private static TxtToXmlTransformationAdater txtToXmlAdapter;
	
	private  boolean isRunning		 	= false;
	
	private ResourceBundle bundle 		= PropAccess.getResourceBundle();
		
	private final int delayPeriod 		= Integer.parseInt(bundle.getString("delay").trim());
	
	
	public void run() {
		
		while(isRunning){
			try {
				
				
				
				
				Thread.sleep(delayPeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public final static TxtToXmlTransformationAdater getTxtToXmlTransformationAdapter() {

		System.out.println("Initializing .txt to .xml Transformation Adapter............");

		if (txtToXmlAdapter == null)
			txtToXmlAdapter = new TxtToXmlTransformationAdater();

		return TxtToXmlTransformationAdater.txtToXmlAdapter;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
