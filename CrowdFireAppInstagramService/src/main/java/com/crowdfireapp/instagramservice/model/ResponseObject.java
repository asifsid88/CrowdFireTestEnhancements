package com.crowdfireapp.instagramservice.model;

public class ResponseObject {
	private int status;
	private String pollStatus;
	private Object data;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getPollStatus() {
		return pollStatus;
	}
	public String getpollStatus() {
		return pollStatus;
	}
	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}
}
