package com.crowdfireapp.instagramservice.apis.social;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.crowdfireapp.instagram.api.InstagramApi;
import com.crowdfireapp.instagramservice.model.UserActivity;
import com.crowdfireapp.instagramservice.model.helper.JSONToModelTransformer;
import com.crowdfireapp.instagramservice.poll.PollManager;
import com.crowdfireapp.instagramservice.poll.PollObject;
import com.crowdfireapp.instagramservice.poll.followeractivity.FollowerActivityPollObject;
import com.crowdfireapp.utility.JSONUtils;
import com.crowdfireapp.utility.StringUtils;

public class InstagramApiClient implements SocialConnectApi {
	public boolean hasAccessToken(String userName) {
		return InstagramApi.hasAccessToken(userName);
	}
	
	public String getUserId(String userName) {
		return InstagramApi.getUserId(userName);
	}
	
	public String getUserIdFromUserInformation(String userName) {
		String jsonResponse = InstagramApi.getUserInformation(userName);
		Map<String, Object> map = JSONUtils.fromJSONToObject(jsonResponse);
		// TODO: Get the userId
		
		String userId = "";
		
		return userId;
	}
	
	public String authorizeUser(String userName) {
		return InstagramApi.authorizeUser(userName);
	}
	
	public String obtainAccessToken(String code) {
		return InstagramApi.obtainAccessToken(code);
	}
	
	public List<UserActivity> getFollowersActivity(String userId) {
		List<String> followersList = getFollowersList(userId);
		if(followersList == null || followersList.size() == 0) {
			return (List<UserActivity>) Collections.EMPTY_LIST;
		}
		
		return getActivityForFollowersList(followersList);		
	}
	
	private List<String> getFollowersList(String userId) {
		List<String> followersList = null;
		if(!PollManager.isActivePollObject(userId)) {
			System.out.println("Calling FollewedBy");
			String response = InstagramApi.getUsersFollowedBy(userId);
			if(StringUtils.isNullOrEmpty(response)) {
				return (List<String>) Collections.EMPTY_LIST;
			}
			
			System.out.println("Followers list obtained, transforming into objects");
			followersList = JSONToModelTransformer.getUsers(response);
			System.out.println("List of followers for userId="+userId);
			System.out.println(followersList);
			
			PollObject po = new FollowerActivityPollObject(followersList);
			PollManager.addPollObject(userId, po);
			System.out.println("Added the object for polling");
		}
		
		System.out.println("Polling Object for UserId = "+userId);
		FollowerActivityPollObject followerActivityPO = (FollowerActivityPollObject) PollManager.getPollObject(userId);
		followersList = followerActivityPO.getNextProcessingFollowerSegment();
		System.out.println("Processing request for followers (sublist) => " + followersList);
		
		return followersList;
	}
	
	private List<UserActivity> getActivityForFollowersList(List<String> followersList) {
		System.out.println("Getting Activities for each followers");
		List<UserActivity> list = new LinkedList<UserActivity>();
		for(String followerName : followersList) {
			System.out.println("Fetching last activity for follower="+followerName);
			String response = InstagramApi.getLastActivityByUser(followerName);
			if(StringUtils.isNullOrEmpty(response)) {
				System.out.println("Couldn't find details of follower => " + followerName);
				continue;
			}
			UserActivity followerActivity = JSONToModelTransformer.createActivity(response);
			if( followerActivity != null) {
				System.out.println("Last Time Posted => "+followerActivity);
				list.add(followerActivity);
			} else {
				System.out.println("Issue while getting follower activity for userId="+followerName);
			}
		}
		
		return list;
	}
}
