package com.crowdfireapp.instagramservice.poll;

public enum PollState {
	INPROCESS("inprogress"),
	COMPLETE("complete");
	
	private String status;
	private PollState(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.status;
	}
}
