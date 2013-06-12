package com.omni.oesb.fileparser.main;

import com.omni.oesb.common.ServiceMaster;
import com.omni.oesb.fileparser.adapter.service.FileParserService;

public class OesbMain {
	public static void main(String ar[]){
		
		ServiceMaster fileParserService = new FileParserService();
		
		fileParserService.startService();
		
	}
}
