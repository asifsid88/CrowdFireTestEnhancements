package com.crowdfireapp.utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class JSONUtils {
	public static String toJSON(Object object) {
		if(ObjectUtils.isNull(object)) {
			return "";
		}
		
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	
	public static Map<String, Object> fromJSONToObject(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNullOrEmpty(json)) {
			Gson gson = new Gson();
			map = gson.fromJson(json, Map.class);
		}
		
		return map;
	}
	
	public static List<Map<String, Object>> fromJSONToArray(String json) {
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		if(!StringUtils.isNullOrEmpty(json)) {
			Gson gson = new Gson();
			list = gson.fromJson(json, List.class);
		}
		
		return list;
	}
}
