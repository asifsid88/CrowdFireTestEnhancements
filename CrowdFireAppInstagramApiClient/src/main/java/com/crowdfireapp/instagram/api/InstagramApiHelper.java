package com.crowdfireapp.instagram.api;

import java.util.Map;
import java.util.Map.Entry;

import com.crowdfireapp.utility.ObjectUtils;
import com.crowdfireapp.utility.StringUtils;

class InstagramApiHelper {
	public static String addParamToUrl(String url, String key, String value) {
		if(StringUtils.isNullOrEmpty(key) || StringUtils.isNullOrEmpty(value)) return url;
		
		StringBuilder sb = new StringBuilder();
		sb.append(url)
			.append("?")
			.append(key)
			.append("=")
			.append(value);
		
		return sb.toString();
	}
	
	public static String addParamsToUrl(String url, Map<String, String> params) {
		if(ObjectUtils.isNull(params) || params.size() == 0) return url;
		
		int size = params.size() - 1;
		StringBuilder sb = new StringBuilder();
		sb.append(url)
			.append("?");
		
		for(Entry<String, String> param : params.entrySet()) {
			sb.append(param.getKey())
				.append("=")
				.append(param.getValue());
			
			if(size > 0) {
				sb.append("&");
				size--;
			}
		}
		
		return sb.toString();
	}
	
	public static String getAccessToken(String userName) {
		if(StringUtils.isNullOrEmpty(userName)) {
			return null;
		}
		
		return Cache.accessToken.get(userName);
	}
}
