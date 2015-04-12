package com.crowdfireapp.instagramservice.poll;

import java.util.HashMap;
import java.util.Map;

public class PollManager {
	
	private static Map<String, PollObject> pollCache = new HashMap<String, PollObject>();
	
	public static boolean isPollObjectPresent(String identifier) {
		return pollCache.containsKey(identifier);
	}
	
	public static void removePollObject(String identifier) {
		pollCache.remove(identifier);
	}
	
	public static void addPollObject(String identifier, PollObject pollObject) {
		if(pollCache.containsKey(identifier)) {
			PollObject that = pollCache.get(identifier);
			if(that.getCurrentPollState() == PollState.COMPLETE) {
				pollCache.put(identifier, pollObject);
			}
		} else {
			pollCache.put(identifier, pollObject);	
		}
	}
	
	public static PollObject getPollObject(String identifier) {
		return pollCache.get(identifier);
	}

	public static PollState getObjectPollState(String identifier) {
		if(!pollCache.containsKey(identifier)) {
			return null;
		}
		
		return pollCache.get(identifier).getCurrentPollState();
	}
	
	public static boolean isActivePollObject(String identifier) {
		if(!pollCache.containsKey(identifier)) {
			return false;
		}
		
		return getObjectPollState(identifier) != PollState.COMPLETE;
	}
}
