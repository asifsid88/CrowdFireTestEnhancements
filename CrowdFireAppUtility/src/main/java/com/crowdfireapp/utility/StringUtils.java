package com.crowdfireapp.utility;

public class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		if(str == null) return true;
		if(str.trim().length() == 0) return true;
		
		return false;
	}
	
	public static String replace(String str, String key, String value) {
		StringBuilder result = new StringBuilder();
		result.append(str);
		int start = result.indexOf(key);
		int end = start + key.length();
		result.replace(start, end, value);
		
		return result.toString();
	}
}
