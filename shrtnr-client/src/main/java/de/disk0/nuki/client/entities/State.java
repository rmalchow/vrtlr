package de.disk0.shrtnr.client.entities;

import java.util.Date;

public class State {

	private String state;
	private String stateName;
	private boolean batteryCritical;
	private Date timestamp;

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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
