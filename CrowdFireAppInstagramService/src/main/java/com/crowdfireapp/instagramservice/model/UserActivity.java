package com.crowdfireapp.instagramservice.model;


public class UserActivity {
	private String username;
	private int time;	// 24 hr format ==> rounded to floor value
	private int day;	// 0-6 ==> Sun-Saturday

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	@Override
	public String toString() {
		return "userId:"+this.username+" posted on "+this.day+" by "+this.time+" hrs";
	}
}
