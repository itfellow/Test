package com.omni.oesb.main;

import com.omni.oesb.notification.parser.service.FileParserService;
import com.omni.oesb.notification.parser.service.ServiceMaster;

public class OesbMain {
	public static void main(String ar[]){
		ServiceMaster fileParserService = new FileParserService();
		
		fileParserService.startService();
		
	}
}
