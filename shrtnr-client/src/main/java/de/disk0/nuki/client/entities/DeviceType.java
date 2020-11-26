package de.disk0.shrtnr.client.entities;

public enum DeviceType {

	LOCK(0),
	OPENER(2);
	
	public final int id;
	
	DeviceType(int id) {
		this.id = id;
	}

}
