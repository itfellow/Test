package com.omni.oesb.fileparser.transformer.adapter.service;

import com.omni.component.logging.FileLogger;
import com.omni.oesb.common.AppConstants;
import com.omni.oesb.fileparser.transformer.adapter.TxtToXmlTransformationAdater;

public class TxtToXmlTransformationService {

	private final TxtToXmlTransformationAdater txtToXmlAdapter = TxtToXmlTransformationAdater.getTxtToXmlTransformationAdapter();
	
	private final FileLogger fileLogger  = FileLogger.getFileLogger(this.getClass().getName());
	
	private Thread txtToXmlThread = null;
	
	/**
	 * This Method Starts FileParserAdapter<br>
	 * And Starts Picking Files From the Folder and Process It
	 */
	public void startService(){
		
		txtToXmlThread = new Thread(txtToXmlAdapter);
		
		AppConstants.isTranform = true;
		
		txtToXmlAdapter.setRunning(true);
		
		txtToXmlThread.start();
		
	}
	
	
	
	/**
	 * This Method Stops the FileParserAdapter
	 */
	public void stopService(){
		
		AppConstants.isTranform = false;
		
		txtToXmlAdapter.setRunning(false);
		
	}
	
	
	
	/**
	 * This Method checks the Status of FileParserAdpater
	 * @return {@link Boolean}
	 */
	public boolean isServiceRunning(){
		
		return txtToXmlAdapter.isRunning();
		
	}
	
}
