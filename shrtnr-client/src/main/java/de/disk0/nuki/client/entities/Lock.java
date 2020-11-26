package de.disk0.shrtnr.client.entities;

public class Lock {

	private int deviceType;
	private String name;
	private String shrtnrId;
	private State lastKnownState;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getshrtnrId() {
		return shrtnrId;
	}

	public void setshrtnrId(String shrtnrId) {
		this.shrtnrId = shrtnrId;
	}

	public State getLastKnownState() {
		return lastKnownState;
	}

	public void setLastKnownState(State lastKnownState) {
		this.lastKnownState = lastKnownState;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
}
