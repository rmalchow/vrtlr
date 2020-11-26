package de.disk0.shrtnr.client.entities;

public class LockResponse {

	private String state;
	private String stateName;
	private boolean batteryCritical;
	private boolean success;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public boolean isBatteryCritical() {
		return batteryCritical;
	}

	public void setBatteryCritical(boolean batteryCritical) {
		this.batteryCritical = batteryCritical;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
