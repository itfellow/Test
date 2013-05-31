package com.omni.oesb.constants;

import java.util.HashMap;

public interface ParserPatterns {

	/*     Message Parse Patterns        */
	// public static String MSG_BLOCK_PATTERN = "\\{A:[a-zA-Z0-9]+\\}\\{4::[[0-9]{4}:[/.@,a-zA-Z0-9\\s]]+-\\}\\{UMAC:[A-Za-z0-9-\\s.]*|\\{A:[a-zA-Z0-9]+\\}\\{4::[[0-9]{4}:[/.@,a-zA-Z0-9\\s]]+-\\}";

	String MSG_BLOCK_PATTERN = "\\{A:[a-zA-Z0-9]+\\}\\{4:*:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}\\{UMAC:[A-Za-z0-9-\\s\\n.]*|\\{A:[a-zA-Z0-9]+\\}\\{4:\\n:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}";
	
	String MSG_BLOCK_PATTERN_1 = "\\{A:[a-zA-Z0-9]+\\}\\{4:*:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}\\";
	
	String MSG_BLOCK_HEADER_PATTERN = "\\{A:[a-zA-Z0-9]+\\}";

	String MSG_BLOCK_BODY_PATTERN = "\\{4:\\n:[[0-9]{4}:[/.@,'a-zA-Z0-9\\s\\n]]+-\\}";

	String MSG_UMAC_BLOCK_PATTERN = "\\{UMAC:[-/.@,'a-zA-Z0-9\\n]+(\\})?(?!\\{)";

	String MSG_BLOCK_DATA_PATTERN = ":[0-9]{4}:['\\/.@,a-zA-Z0-9\\s\\n]+";

	String MSG_PARSE_ERROR = "MSG_PARSE_ERROR";

	String MSG_PARSE_IGNORED = "MSG_PARSE_IGNORED";

	String MSG_PARSE_SUCCESS = "MSG_PARSE_SUCCESS";

	String ACC_NUM_PATTERN = "/[0-9]+\\n";

	String NAME_PATTERN = "[.,a-zA-Z\\s]+\\n";

	String ADDR_PATTERN = "[/.'@,a-zA-Z0-9\\s\\n]+\\n";
	
	
	HashMap<String, String> MESSAGE_FORMAT_MAP = new HashMap<String, String>();
	
	
}
