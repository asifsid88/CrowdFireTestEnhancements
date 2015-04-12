package com.crowdfireapp.utility;

public class CrowdFireAppUtils {
	public static String formatExceptionString(String message, Exception exception) {
		StringBuilder msg = new StringBuilder();
		msg.append(message)
			.append("\n")
			.append("Exception: ")
			.append(exception)
			.append("\n");
		
		return msg.toString();
	}
}
