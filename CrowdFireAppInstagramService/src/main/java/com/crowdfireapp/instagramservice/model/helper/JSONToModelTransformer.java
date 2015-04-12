package com.crowdfireapp.instagramservice.model.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.crowdfireapp.instagramservice.model.UserActivity;
import com.crowdfireapp.utility.JSONUtils;

public class JSONToModelTransformer {
	public static List<String> getUsers(String json) {
		List<String> userList = new LinkedList<String>();
		Map<String, Object> map = JSONUtils.fromJSONToObject(json);
		List<Object> dataArray = (List<Object>) map.get("data");
		for(Object data : dataArray) {
			Map<String, String> followersInfo = (Map<String, String>) data; 
			userList.add((String) followersInfo.get("id"));
		}
		
		return userList;
	}
	
	public static UserActivity createActivity(String json) {
		UserActivity u = null;
		Map<String, Object> map = JSONUtils.fromJSONToObject(json);
		
		if(map.get("data") == null) return u;
		
		List<Object> dataArray = (List<Object>) map.get("data");
		for(Object data : dataArray) {
			Map<String, Object> activityInfo = (Map<String, Object>) data;
			String createdTime = (String) activityInfo.get("created_time");
			Map<String, String> userObject = (Map<String, String>) activityInfo.get("user");
			String username = (String) userObject.get("id");
			
			Date date = convertUnixTimeStampToDate(Long.valueOf(createdTime));
			u = new UserActivity();
			u.setUsername(username);
			u.setTime(date.getHours());
			u.setDay(date.getDay());
		}
		
		return u;
	}
	
	public static Date convertUnixTimeStampToDate(long unixSeconds) {
		Date date = new Date(unixSeconds*1000L); 
		return date;
	}
}
