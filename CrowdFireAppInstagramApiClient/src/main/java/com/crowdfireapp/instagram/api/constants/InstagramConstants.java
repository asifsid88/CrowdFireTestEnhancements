package com.crowdfireapp.instagram.api.constants;

public final class InstagramConstants {
	/*
	 * TODO: Move the credentials and urls to properties file. So in future if clientId change, 
	 * then it will be easier to do amendments
	 */
	public static final String CLIENT_ID = "d2c6a05068c247bb800b9da968eebf35";
	public static final String CLIENT_SECRET = "dd8cf98ced0848a789fa9142bb23e3a3";
	public static final String SCOPE_RELATION = "relationships";
	public static final String REDIRECT_URI = "http://localhost:8080/CrowdFireAppInstagramService/crowdfire/user/accesstoken";
	public static final String REDIRECT_URI_POST_AUTH = "http://localhost:8080/CrowdFireAppInstagramService/crowdfire/user/accesstoken";
	public static final String AUTHORIZATION_CODE = "authorization_code";
}
