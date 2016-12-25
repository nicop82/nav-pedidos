package com.navent.dao.impl;

import com.navent.dao.GenericDaoI;

public abstract class GenericDAOImpl<T> implements GenericDaoI<T> {
	public T insertOrUpdate(final T t) {
		return null;
	}
	
	public T select(final Object id) {
		return null;
	}
	
	public void delete(final Object id) {
		
	}
}
