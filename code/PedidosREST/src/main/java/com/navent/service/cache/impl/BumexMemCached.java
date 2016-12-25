package com.navent.service.cache.impl;

import com.navent.model.Pedido;
import com.navent.service.cache.GenericMemCached;

/**
 * <p>
 * Consigna: La clase BumexMemcached es un singleton que tiene los siguientes
 * métodos (tomarlos como ya implementados, no es necesario codificarlos): void
 * set(String key, Object value), Object get(String key) y void delete(String
 * key)
 * </p>
 * <p>
 * Entiendo que hubo un cambio a la consigna sobre los métodos a implementar
 * pero considero que implementar una Cache Genérica es mejor ya que se podría
 * reutilizar en cualquier lugar del sistema si el mismo crece
 * </p>
 * 
 * @author nicolasp
 *
 */
public class BumexMemCached extends GenericMemCached<String, Pedido> {
	
	private static BumexMemCached instance = null;
	
	protected BumexMemCached(long timeToLive, long timerInterval, int maxItems) {
		super(timeToLive, timerInterval, maxItems);
	}

	public static BumexMemCached getInstance() {
		if (instance == null) {
			instance = new BumexMemCached(2000, 5000, 4);
		}
		return instance;
	} 
}
