package de.disk0.shrtnr.client.entities;

public enum LockAction {
	
	UNLOCK(1),
	LOCK(2),
	UNLATCH(3);
	
	public final int id;
	
	LockAction(int id) {
		this.id = id;
	}

	
}
