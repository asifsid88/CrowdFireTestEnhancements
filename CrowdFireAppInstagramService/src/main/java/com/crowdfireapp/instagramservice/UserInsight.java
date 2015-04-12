package com.crowdfireapp.instagramservice;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.crowdfireapp.instagramservice.apis.social.InstagramApiClient;
import com.crowdfireapp.instagramservice.enums.SocialConnectType;
import com.crowdfireapp.instagramservice.factory.apis.SocialConnectApiFactory;
import com.crowdfireapp.instagramservice.helpers.ResponseBuilder;
import com.crowdfireapp.instagramservice.model.ResponseObject;
import com.crowdfireapp.instagramservice.model.UserActivity;
import com.crowdfireapp.utility.CrowdFireAppUtils;
import com.crowdfireapp.utility.StringUtils;


@Path("crowdfire")
public class UserInsight {
	private SocialConnectApiFactory apiFactory;
	
	{
		apiFactory = SocialConnectApiFactory.getInstance();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/timetopost")
	public Response getBestTimeToPost(@QueryParam("username") String userName) {
		if(StringUtils.isNullOrEmpty(userName)) {
			return ResponseBuilder.buildResponse(Status.BAD_REQUEST, new String("User Name is required !!!"));
		}
		
		try {
			InstagramApiClient instagramApi = (InstagramApiClient) apiFactory.getApi(SocialConnectType.INSTAGRAM);
			String userId = getUserIdFromUserName(instagramApi, userName);			
			boolean hasAccessToken = instagramApi.hasAccessToken(userId);
			if(!hasAccessToken) {
				System.out.println("User: "+userName+" does not have access token");
				String response = instagramApi.authorizeUser(userName);
				System.out.println("Redirecting to ==> " + response);
				return Response.temporaryRedirect(new URI(response))
								.header("Access-Control-Allow-Origin", "*")
		        				.header("Access-Control-Allow-Methods", "GET, POST, PUT, UPDATE, OPTIONS")
		        				.header("Access-Control-Allow-Headers", "x-requested-with,Content-Type")
								.build();
			} else {
				System.out.println("User: "+userName+" have access token");
				ResponseObject bestTimeDetails = processFurther(instagramApi, userId);
				return ResponseBuilder.buildResponse(Status.ACCEPTED, bestTimeDetails);
			}
		} catch(Exception e) {
			System.out.println(CrowdFireAppUtils.formatExceptionString("Exception while getting InstagramApiClient", e));
			return null; // TODO: Handle exception gracefully
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/accesstoken")
	public Response obtainAccessToken(@QueryParam("code") String code, 
										@QueryParam("error") String error, @QueryParam("error_reason") String errorReason,
										@QueryParam("error_description") String errorDescription) {
		StringBuilder response = new StringBuilder();
		if(StringUtils.isNullOrEmpty(code)) {
			if(StringUtils.isNullOrEmpty(error)) {
				response.append("Error Connecting to Instagram");
				return ResponseBuilder.buildResponse(Status.BAD_REQUEST, response.toString());	
			} else {
				response.append("User has denied to accept the invitation. Error Description: ").append(errorDescription);
				return ResponseBuilder.buildResponse(Status.OK, response.toString());
			}
		}
		System.out.println("Received the code from instagram. Now exchanging to get Access Token");
		InstagramApiClient instagramApi = (InstagramApiClient) apiFactory.getApi(SocialConnectType.INSTAGRAM);
		String userId = instagramApi.obtainAccessToken(code);
		
		ResponseObject bestTimeDetails = new ResponseObject();
		if(!StringUtils.isNullOrEmpty(userId)) {
			System.out.println("Access token obtained successfully");
			bestTimeDetails = processFurther(instagramApi, userId);
		} else {
			// Show user the same login page, saying .. error occured while connecting to instagram
			System.out.println("Some issue faced while getting access token");
			bestTimeDetails.setStatus(500);
		}
		
		return ResponseBuilder.buildResponse(Status.ACCEPTED, bestTimeDetails);
	}
	
	private ResponseObject processFurther(InstagramApiClient instagramApi, String userId) {
		System.out.println("Getting the followers and its activity");
		List<UserActivity> followersActivityList = instagramApi.getFollowersActivity(userId);
		
		System.out.println("Got the list of followersActivity");
		System.out.println(followersActivityList);
		System.out.println("Calculating best time for post");
		
		return UserActivityHelper.getBestTimeToPost(followersActivityList, userId);
	}
	
	private String getUserIdFromUserName(InstagramApiClient instagramApi, String userName) {
		String userId = instagramApi.getUserId(userName);
		System.out.println("Trying to get the userId from Cache");
		if(StringUtils.isNullOrEmpty(userId)) {
			System.out.println("User Id is deleted from the CACHE, make UserInformation call to get it");
			//userId = instagramApi.getUserIdFromUserInformation(userName);
		}
		
		return userId;
	}
}
