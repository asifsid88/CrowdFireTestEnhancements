package com.crowdfireapp.instagram.api;

import java.util.HashMap;
import java.util.Map;

/**
 * This is just imitation to any persistent storage. Currently I'm not using any Database or file system to maintain the state
 * (especially, accessToken)
 * 
 * @author Asif
 *
 */
class Cache {
	public static Map<String, String> accessToken = new HashMap<String, String>();
	public static Map<String, String> userIds = new HashMap<String, String>();
}
