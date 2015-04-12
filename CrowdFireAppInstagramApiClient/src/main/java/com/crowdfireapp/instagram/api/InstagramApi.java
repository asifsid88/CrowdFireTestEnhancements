package com.crowdfireapp.instagram.api;

import java.util.HashMap;
import java.util.Map;

import com.crowdfireapp.instagram.api.constants.ApiPlaceholders;
import com.crowdfireapp.instagram.api.constants.ApiQueryParams;
import com.crowdfireapp.instagram.api.constants.InstagramConstants;
import com.crowdfireapp.instagram.api.constants.RESTApi;
import com.crowdfireapp.utility.JSONUtils;
import com.crowdfireapp.utility.StringUtils;

public class InstagramApi {
	public static String getUsersFollowedBy(String userName) {
		String jsonResponse = "";
		if(StringUtils.isNullOrEmpty(userName)) {
			return jsonResponse;
		}
		
		String accessToken = Cache.accessToken.get(userName);
		if(StringUtils.isNullOrEmpty(accessToken)) {
			return jsonResponse;
		}
		
		String restUrl = StringUtils.replace(RESTApi.USERS_FOLLOWED_BY, ApiPlaceholders.USER_ID, userName);
		restUrl = InstagramApiHelper.addParamToUrl(restUrl, ApiQueryParams.ACCESS_TOKEN, accessToken);
		jsonResponse = InstagramClient.getInstance().doGet(restUrl);
		
		return jsonResponse;
	}
	
	public static String getLastActivityByUser(String userName) {
		String jsonResponse = "";
		if(StringUtils.isNullOrEmpty(userName)) {
			return jsonResponse;
		}
		
		String restUrl = StringUtils.replace(RESTApi.USER_LAST_ACTIVITY, ApiPlaceholders.USER_ID, userName);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ApiQueryParams.CLIENT_ID, InstagramConstants.CLIENT_ID);
		params.put(ApiQueryParams.COUNT, "1");	// TODO: Currently, this count=1, we can have custom count to do analysis
		restUrl = InstagramApiHelper.addParamsToUrl(restUrl, params);		
		jsonResponse = InstagramClient.getInstance().doGet(restUrl);
		
		return jsonResponse;
	}

	public static String getUserInformation(String userName) {
		String jsonResponse = "";
		if(StringUtils.isNullOrEmpty(userName)) {
			return jsonResponse;
		}
		
		String accessToken = Cache.accessToken.get(userName);
		if(StringUtils.isNullOrEmpty(accessToken)) {
			return jsonResponse;
		}
		
		String restUrl = StringUtils.replace(RESTApi.USER_INFORMATION, ApiPlaceholders.USER_ID, userName);
		restUrl = InstagramApiHelper.addParamToUrl(restUrl, ApiQueryParams.ACCESS_TOKEN, accessToken);
		jsonResponse = InstagramClient.getInstance().doGet(restUrl);
		
		return jsonResponse;
	}
	
	public static String authorizeUser(String userName) {
		if(StringUtils.isNullOrEmpty(userName)) {
			return "";
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ApiQueryParams.CLIENT_ID, InstagramConstants.CLIENT_ID);
		params.put(ApiQueryParams.REDIRECT_URI, InstagramConstants.REDIRECT_URI);
		params.put(ApiQueryParams.RESPONSE_TYPE, ApiQueryParams.CODE);
		params.put(ApiQueryParams.SCOPE, InstagramConstants.SCOPE_RELATION);

		return InstagramApiHelper.addParamsToUrl(RESTApi.OAUTH_USER, params);
	}
	
	public static String obtainAccessToken(String code) {
		if(StringUtils.isNullOrEmpty(code)) {
			return "";
		}
		
		Map<String, String> data = new HashMap<String, String>();
		data.put(ApiQueryParams.CLIENT_ID, InstagramConstants.CLIENT_ID);
		data.put(ApiQueryParams.CLIENT_SECRET, InstagramConstants.CLIENT_SECRET);
		data.put(ApiQueryParams.GRANT_TYPE, InstagramConstants.AUTHORIZATION_CODE);
		data.put(ApiQueryParams.REDIRECT_URI, InstagramConstants.REDIRECT_URI_POST_AUTH);
		data.put(ApiQueryParams.CODE, code);
		System.out.println("Posting the code to get the access token for the user");
		String jsonResponse = InstagramClient.getInstance().doPost(RESTApi.ACCESS_TOKEN, data);
		
		if(!StringUtils.isNullOrEmpty(jsonResponse)) {
			Map<String, Object> map = JSONUtils.fromJSONToObject(jsonResponse);
			String token = (String) map.get("access_token");
			Map<String, Object> userMap = (Map<String, Object>) map.get("user");
			String userId = (String) userMap.get("id");
			String userName = (String) userMap.get("username");
			
			System.out.println("Caching the access token for user: "+userName+" at server");
			Cache.accessToken.put(userId, token);
			System.out.println("Caching the userId="+userId);
			Cache.userIds.put(userName, userId);
			return userId;
		} else {
			// TODO : Error while getting the access token, handle it gracefully
			// Return proper error code to the callee to be handled
			return "";
		}
	}
	
	public static boolean hasAccessToken(String userId) {
		if(StringUtils.isNullOrEmpty(userId)) {
			return false;
		}
		
		return Cache.accessToken.containsKey(userId);
	}

	public static String getUserId(String userId) {
		if(StringUtils.isNullOrEmpty(userId)) {
			return "";
		}

		return Cache.userIds.get(userId);
	}
}
