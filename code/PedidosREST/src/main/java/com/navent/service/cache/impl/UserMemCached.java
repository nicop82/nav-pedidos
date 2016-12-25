package com.navent.service.cache.impl;

import com.navent.model.User;
import com.navent.service.cache.GenericMemCached;

public class UserMemCached extends GenericMemCached<String, User> {

	public UserMemCached(long timeToLive, long timerInterval, int maxItems) {
		super(timeToLive, timerInterval, maxItems);
	}
	
	private static UserMemCached instance = null;
	
	public static UserMemCached getInstance() {
		if (instance == null) {
			instance = new UserMemCached(2000000, 2000000, 4);
		}
		return instance;
	} 

}
