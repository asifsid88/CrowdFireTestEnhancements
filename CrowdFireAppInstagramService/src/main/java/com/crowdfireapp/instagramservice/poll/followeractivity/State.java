package com.crowdfireapp.instagramservice.poll.followeractivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
	private List<Integer> weekMap;
	private int bestDay;
	private Map<Integer, List<Integer>> dayMap;

	public State() {
		initialize();
	}
	
	private void initialize() {
		weekMap = new ArrayList<Integer>();
		for(int i=0; i<7; i++)
			weekMap.add(0);
		
		bestDay = -1;
		
		dayMap = new HashMap<Integer, List<Integer>>();
		for(int i=0; i<7; i++) {
			List<Integer> hourMap = new ArrayList<Integer>();
			for(int j=0; j<24; j++)
				hourMap.add(0);
			dayMap.put(i, hourMap);	
		}
	}
	
	public List<Integer> getWeekMap() {
		return weekMap;
	}
	public void setWeekMap(List<Integer> weekMap) {
		this.weekMap = weekMap;
	}
	public int getBestDay() {
		return bestDay;
	}
	public void setBestDay(int bestDay) {
		this.bestDay = bestDay;
	}
	public Map<Integer, List<Integer>> getDayMap() {
		return dayMap;
	}
	public void setDayMap(Map<Integer, List<Integer>> dayMap) {
		this.dayMap = dayMap;
	}
}
