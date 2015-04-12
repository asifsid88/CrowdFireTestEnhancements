package com.crowdfireapp.instagramservice.factory.apis;

import com.crowdfireapp.instagramservice.apis.social.InstagramApiClient;
import com.crowdfireapp.instagramservice.apis.social.SocialConnectApi;
import com.crowdfireapp.instagramservice.enums.SocialConnectType;

public class SocialConnectApiFactory {
	private static SocialConnectApiFactory apiFactory;
	private SocialConnectApi api;
	
	private SocialConnectApiFactory() {}

	public static SocialConnectApiFactory getInstance() {
		if(apiFactory == null) {
			apiFactory = new SocialConnectApiFactory();
		}
		
		return apiFactory;
	}
	
	/*
	 * Get the API for different SocialConnect
	 */
	public SocialConnectApi getApi(SocialConnectType type) {
		if(SocialConnectType.INSTAGRAM == type) {
			api = new InstagramApiClient();
		} else if(SocialConnectType.FACEBOOK == type) {
			/*
			 * Similarly, if we want to integrate FACEBOOK, we can do so, like this we can have 
			 * any social connect apis present in this factory
			 */
			api = null;
		}	
		
		return api;
	}
}