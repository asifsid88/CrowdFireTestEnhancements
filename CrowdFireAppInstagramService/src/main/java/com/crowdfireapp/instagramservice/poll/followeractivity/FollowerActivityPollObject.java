package com.crowdfireapp.instagramservice.poll.followeractivity;

import java.util.List;

import com.crowdfireapp.instagramservice.poll.PollObject;
import com.crowdfireapp.instagramservice.poll.PollState;

public class FollowerActivityPollObject extends PollObject {

	/*
	 * This can be made a config-driven, to vary the amount of elements to process in a single call
	 */
	private static int segmentLength = 10; 
	
	private final List<String> followersList;
	private State activityState;
	
	public FollowerActivityPollObject(List<String> followersList) {
		super();
		this.followersList = followersList;
		this.activityState = new State();
	}
	
	public List<String> getFollowersList() {
		return this.followersList;
	}
	
	public State getActivityState() {
		return this.activityState;
	}
	
	public void setActivityState(State activityState) {
		this.activityState = activityState;
	}

	public List<String> getNextProcessingFollowerSegment() {
		if(pollState == PollState.COMPLETE) {
			return null;
		}
		
		int startIndex = currentProcessingElement;
		int endIndex;
		if(currentProcessingElement + segmentLength > followersList.size()) {
			endIndex = followersList.size();
		} else {
			endIndex = currentProcessingElement + segmentLength;
		}
		
		return this.followersList.subList(startIndex, endIndex);
	}
	
	@Override
	public void updateState() {
		currentProcessingElement += segmentLength + 1;
		if(currentProcessingElement >= followersList.size()) {
			pollState = PollState.COMPLETE;
		}
	}
}
