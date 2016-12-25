package com.navent.service.cache;

public interface CacheI<K, T> {
	
	public T set(K key, T value);
	
	public T get(K key);
	
	public void delete(K key);
}
