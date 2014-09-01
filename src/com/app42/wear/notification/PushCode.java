package com.app42.wear.notification;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Vishnu Garg
 * @Shephertz
 * 
 */
public enum PushCode {
	Basic(0), BigText(1),Image(2),MultiPage(3);
	private int code;
	private PushCode(final int code) { 
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	private static final Map<Integer, PushCode> codeMap = new HashMap<Integer, PushCode>();
    static {
        // populating the map
        for (PushCode adCode : values()) {
        	codeMap.put(adCode.getCode(), adCode);
        }
    }
    public static PushCode getByCode(int value) {
        return codeMap.get(value);
    }
}
