package com.omni.oesb.common;

import java.util.HashMap;

public final class AppConstants {

	// Message Parse Patterns //

	public static final String MSG_BLOCK_PATTERN = "\\{A:[a-zA-Z0-9]+\\}\\{4:*:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}\\{UMAC:[A-Za-z0-9-\\s\\n.]*|\\{A:[a-zA-Z0-9]+\\}\\{4:\\n:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}";
	
	public static final String MSG_BLOCK_PATTERN_1 = "\\{A:[a-zA-Z0-9]+\\}\\{4:*:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}\\";
	
	public static final String MSG_BLOCK_HEADER_PATTERN = "\\{A:[a-zA-Z0-9]+\\}";

	public static final String MSG_BLOCK_BODY_PATTERN = "\\{4:\\n:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}";

	public static final String MSG_UMAC_BLOCK_PATTERN = "\\{UMAC:[-/.@,'a-zA-Z0-9\\n]+(\\})?(?!\\{)";

	public static final String MSG_BLOCK_DATA_PATTERN = ":[0-9]{4}:['\\/.@,a-zA-Z0-9\\s\\n]+";

	public static final String ACC_NUM_PATTERN = "/[0-9]+\\n";
	
	public static final String NAME_PATTERN = "[.,a-zA-Z\\s]+\\n";

	public static final String ADDR_PATTERN = "[/.'@,a-zA-Z0-9\\s\\n]+\\n";
	
	
	//	Parser Status Constants  //
	
	public static final String MSG_PARSE_ERROR = "MSG_PARSE_ERROR";

	public static final String MSG_PARSE_IGNORED = "MSG_PARSE_IGNORED";

	public static final String MSG_PARSE_SUCCESS = "MSG_PARSE_SUCCESS";
	
	//	Message Maps	//
	
	public static final HashMap<String, String> NEFT_CODE_MAP = new HashMap<String, String>();
	
	public static final HashMap<String, String> RTGS_CODE_MAP = new HashMap<String, String>();
	
	// Transformation Flag
	
	public static boolean isTranform = true;
	
}
