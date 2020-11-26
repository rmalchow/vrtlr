package de.disk0.shrtnr.client.entities;

public enum LockState {
	
	UNCALIBRATED(0),
	LOCKED(1),
	UNLOCKING(2),
	UNLOCKED(3),
	LOCKING(4),
	UNLATCHED(5),
	UNLOCKED_N_GO(6),
	UNLATCHING(7),
	BLOCKED(254),
	UNDEFINED(255);
	
	public final int id;
	
	LockState(int id) {
		this.id = id;
	}

	public static LockState getById(int i) {
		for(LockState ls : values()) {
			if(ls.id == i) return ls;
		}
		return UNDEFINED;
	}
	
}
