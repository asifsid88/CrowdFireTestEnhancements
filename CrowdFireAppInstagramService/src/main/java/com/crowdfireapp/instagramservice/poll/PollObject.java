package com.crowdfireapp.instagramservice.poll;

public abstract class PollObject {
	protected Integer currentProcessingElement;
	protected PollState pollState;
	
	public PollObject() {
		this.pollState = PollState.INPROCESS;
		this.currentProcessingElement = 0;
	}
	
	protected abstract void updateState();

	public PollState getCurrentPollState() {
		return this.pollState;
	}
}
