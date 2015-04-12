package com.crowdfireapp.instagramservice.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BestTime {
	private String day;
	private Map<String, String> week = new HashMap<String, String>();

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Map<String, String> getWeek() {
		return week;
	}
	public void setWeek(Map<String, String> week) {
		this.week = week;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=====================================================\n");
		sb.append("Best Day to Post: " + this.day).append("\n\n");
		for(Entry<String, String> entry : week.entrySet()) {
			sb.append("Best Time to Post on " + entry.getKey()+" is " + entry.getValue() + ":00 hours").append("\n");
		}
		sb.append("=====================================================");
		
		return sb.toString();
	}
}
