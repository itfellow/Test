package com.omni.oesb.fileparser.main;

import com.omni.oesb.fileparser.service.FileParserService;
import com.omni.oesb.fileparser.service.ServiceMaster;

public class OesbMain {
	public static void main(String ar[]){
		ServiceMaster fileParserService = new FileParserService();
		
		fileParserService.startService();
		
	}
}
