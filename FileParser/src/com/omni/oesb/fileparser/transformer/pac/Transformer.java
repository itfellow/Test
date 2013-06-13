package com.omni.oesb.fileparser.transformer.pac;

import java.util.HashMap;

public interface Transformer {

	void convertToNGRTGS(	String pacName,
							String businessRule,
							HashMap<String, String> headerMap, 
							HashMap<String, String>  msgBodyMap);
	
}