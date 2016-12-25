package com.navent.service.impl;

import java.util.HashSet;

import com.navent.model.User;
import com.navent.service.cache.impl.UserMemCached;

public class UserService {
	private static UserService instance = null;
	private static UserMemCached userMemCached = null;

	private UserService() {

	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();

			// Obtengo instancia de memCache para no tener que pedirla siempre
			userMemCached = UserMemCached.getInstance();
		}
		return instance;
	}

	public HashSet<User> initCache() {

		if (userMemCached.isEmpty()) {
			userMemCached.set("test", new User("test", "test", "Test", "Tester"));
		}
		return this.getUsuarios();
	}

	public HashSet<User> getUsuarios() {
		return userMemCached.getValues();
	}

	public User get(User user) {
		return userMemCached.get(user.getNombre());
	}

	public User auth(String nombre, String password) {
		User userSaved = userMemCached.get(nombre);
		if (userSaved != null && userSaved.getPassword().equals(password)) {
			return userSaved;
		} else {
			return null;
		}
	}
}
