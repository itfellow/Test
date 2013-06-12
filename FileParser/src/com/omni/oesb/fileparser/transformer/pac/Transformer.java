package com.omni.oesb.fileparser.transformer.pac;

import java.util.HashMap;

public interface Transformer {

	void convertToNGRTGS(HashMap<String, String> headerMap, HashMap<String, String>  msgBodyMap);
	
}