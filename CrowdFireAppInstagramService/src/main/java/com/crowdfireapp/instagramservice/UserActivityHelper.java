package com.crowdfireapp.instagramservice;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.crowdfireapp.instagramservice.model.BestTime;
import com.crowdfireapp.instagramservice.model.ResponseObject;
import com.crowdfireapp.instagramservice.model.UserActivity;
import com.crowdfireapp.instagramservice.poll.PollManager;
import com.crowdfireapp.instagramservice.poll.followeractivity.FollowerActivityPollObject;
import com.crowdfireapp.instagramservice.poll.followeractivity.State;

public class UserActivityHelper {
	public static ResponseObject getBestTimeToPost(List<UserActivity> userList, String userId) {
		FollowerActivityPollObject followerActivityPO = (FollowerActivityPollObject) PollManager.getPollObject(userId);
		State currentState = followerActivityPO.getActivityState();
		
		int bestDay = currentState.getBestDay();
		int count = 0;
		for(UserActivity user : userList) {
			int day = user.getDay();
			List<Integer> week = currentState.getWeekMap();
			week.set(day, week.get(day)+1);
			if(count < week.get(day)) {
				count = week.get(day);
				bestDay = day;
			}
			
			int time = user.getTime();
			Map<Integer, List<Integer>> dayMap = currentState.getDayMap();
			List<Integer> timeMap = dayMap.get(day);
			timeMap.set(time, timeMap.get(time)+1);
		}
		currentState.setBestDay(bestDay);
		
		BestTime bestTime = analyseBestTime(currentState);
		ResponseObject ro = new ResponseObject();
		ro.setStatus(200);
		ro.setData(bestTime);
		
		followerActivityPO.updateState();
		ro.setPollStatus(followerActivityPO.getCurrentPollState().toString());
		
		return ro;
	}
	
	private static BestTime analyseBestTime(State currentState) {
		BestTime bestTime = new BestTime();
		
		// Storing best day
		String d = getDay(currentState.getBestDay());
		bestTime.setDay(d);
		
		// Storing best time of the day
		for(Entry<Integer, List<Integer>> entry : currentState.getDayMap().entrySet()) {
			int day = entry.getKey();
			List<Integer> time = entry.getValue();
			int count = 0;
			int t = Integer.MIN_VALUE;
			for(int i=0; i<23; i++) {
				if(count < time.get(i)) {
					count = time.get(i);
					t = i;
				}
			}
			bestTime.getWeek().put(getDay(day), String.valueOf(t));
		}
		System.out.println(bestTime);
		return bestTime;
	}
	
	private static String getDay(int n) {
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return days[n];
	}
}
