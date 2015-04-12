package com.crowdfireapp.instagram.api.constants;

public final class RESTApi {
	/* ** Relationships APIs ** */
	
	// TODO: Followed-by is not returning proper values --> so did it using "following"
	// public static final String USERS_FOLLOWED_BY = "https://api.instagram.com/v1/users/{user-id}/followed-by";
	public static final String USERS_FOLLOWED_BY = "https://api.instagram.com/v1/users/{user-id}/follows";
	
	
	/* ** Users APIs ** */
	public static final String USER_INFORMATION = "https://api.instagram.com/v1/users/{user-id}/";
	public static final String USER_LAST_ACTIVITY = "https://api.instagram.com/v1/users/{user-id}/media/recent/";

	
	/* ** Authorization APIs ** */
	public static final String OAUTH_USER = "https://api.instagram.com/oauth/authorize/";
	public static final String ACCESS_TOKEN = "https://api.instagram.com/oauth/access_token";
}
