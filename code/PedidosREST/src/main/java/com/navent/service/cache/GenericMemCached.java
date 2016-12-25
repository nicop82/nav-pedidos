package com.navent.service.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

/**
 * <p>Implementación propia de una cache en Memoria Genérica que luego será
 * utilizada en {@link com.navent.service.cache.impl.BumexMemCached}</p>
 * 
 * <p>
 * Entiendo que hubo un cambio a la consigna sobre los métodos a implementar
 * pero considero que implementar una Cache Generica es mejor ya que se podría
 * reutilizar en cualquier lugar del sistema si el mismo crece
 * </p>
 * 
 * <p>
 * Se utiliza un LRUMap ofrecido por apache.commons el cual extiende de HashMap.
 * Se eligió este tipo de colección por la velocidad de búsqueda que ofrece el
 * HashMap y su particularidad de ser FIFO (first in first out) cuando se supera
 * la máxima cantidad de registros permitidos y se quiere insertar un nuevo
 * registro.
 * </p>
 * 
 * <p>
 * Con respecto a los posibles problemas de concurrencia que pueden surgir al
 * tener una cache se implementó una sincronización sobre el LRUMap cuando se
 * accede al mismo.
 * </p>
 * 
 * @author nicolasp
 *
 * @param <K>
 * @param <V>
 */
/**
 * @author nicolasp
 *
 * @param <K>
 * @param <V>
 */
public abstract class GenericMemCached<K, V> implements CacheI<K, V> {

	private static final int INITIAL_ENTRIES = 10;
	private long timeToLive;
	private LRUMap cacheMap;

	/**
	 * Inner class que es una especie de wrapper para agregar la última vez que
	 * se modificó el objeto en la cache.
	 * 
	 * @author nicolasp
	 *
	 */
	protected class MemCacheObject {
		public long lastAccessed = System.currentTimeMillis();
		public V value;

		protected MemCacheObject(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}
	}

	/**
	 * Constructor de la clase GenericMemCached
	 * 
	 * @param timeToLive
	 *            - tiempo que vive un objeto dentro de la cache
	 * @param cleanUpTimerInterval
	 *            - Intervalo de tiempo en que se limpia la cache
	 *            automáticamente mediante un thread
	 * @param maxItemsInCache
	 *            - Máxima cantidad de items permitidos en la cache
	 */
	public GenericMemCached(long timeToLive, final long cleanUpTimerInterval, int maxItemsInCache) {
		this.timeToLive = timeToLive * 1000;

		cacheMap = new LRUMap(INITIAL_ENTRIES, maxItemsInCache);

		if (timeToLive > 0 && cleanUpTimerInterval > 0) {

			Thread t = runCleanUpThread(cleanUpTimerInterval);

			t.setDaemon(true);
			t.start();
		}
	}

	/**
	 * Crea un thread que cada un intervalo de tiempo (timerInterval) limpia la
	 * cache
	 * 
	 * @param cleanUpTimerInterval
	 * @return
	 */
	private Thread runCleanUpThread(final long cleanUpTimerInterval) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(cleanUpTimerInterval * 1000);
					} catch (InterruptedException ex) {
					}
					cleanup();
				}
			}
		});
		return t;
	}

	/**
	 * <p>
	 * Recorre la cache y se fija si el ttl (timeToLive) del objeto expiró. Si
	 * expiró lo deja en un ArrayList y luego borra todos los objetos que se
	 * encuentran en esa lista.
	 * </p>
	 * <p>
	 * El método thread.yield() se utiliza para liberar a la cpu del thread
	 * hasta que este sea llamado nuevamente.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	protected void cleanup() {
		System.out.println("cleaning up...");
		long now = System.currentTimeMillis();
		ArrayList<K> deleteKey = null;

		if (!cacheMap.isEmpty()) {
			synchronized (cacheMap) {

				MapIterator itr = cacheMap.mapIterator();

				deleteKey = new ArrayList<K>();
				K key = null;
				MemCacheObject c = null;

				while (itr.hasNext()) {
					key = (K) itr.next();
					c = (MemCacheObject) itr.getValue();

					if (c != null && (now > (timeToLive + c.lastAccessed))) {
						deleteKey.add(key);
					}
				}
			}

			for (K key : deleteKey) {
				synchronized (cacheMap) {
					cacheMap.remove(key);
				}
				Thread.yield();
			}
		}

	}

	/*
	 * Método que guarda en cache el key, value.
	 */
	public V set(K key, V value) {
		synchronized (cacheMap) {
			cacheMap.put(key, new MemCacheObject(value));
			return value;
		}
	}

	/*
	 * Método que obtiene de la cache un objeto (V) mediante la key (K)
	 */
	public V get(K key) {
		synchronized (cacheMap) {
			MemCacheObject object = (MemCacheObject) cacheMap.get(key);

			if (object == null)
				return null;
			else {
				object.lastAccessed = System.currentTimeMillis();
				return object.value;
			}
		}
	}

	/**
	 * Método que devuelve el mapa de la cache
	 * 
	 * @return
	 */
	public LRUMap<K, V> getAll() {
		return cacheMap;
	}

	/*
	 * Método que elimina el registro de la cache según su key
	 */
	public void delete(K key) {
		synchronized (cacheMap) {
			if (cacheMap.containsKey(key)) {
				cacheMap.remove(key);
			}
		}
	}

	/**
	 * Método que verifica si la cache está vacía. Cache vacía = false.
	 * 
	 * @return
	 */
	public Boolean isEmpty() {
		return cacheMap.isEmpty();
	}

	/**
	 * Devuelve un objeto según su clave.
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(K key) {
		return cacheMap.containsKey(key);
	}

	/**
	 * Método que crea o modifica un objeto en la cache
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean update(K key, V value) {
		Boolean result = false;
		if (cacheMap.containsKey(key)) {
			this.delete(key);
			this.set(key, value);
		} else {
			this.set(key, value);
		}
		return result;
	}

	/**
	 * Método que devuelve un HashSet que contienen solo los objetos guardados
	 * sin sus keys.
	 * 
	 * @return
	 */
	public HashSet<V> getValues() {
		HashSet<V> set = new HashSet<V>();
		Set<Entry> entries = cacheMap.entrySet();
		for (Entry entry : entries) {
			MemCacheObject memCacheObject = (MemCacheObject) entry.getValue();
			set.add(memCacheObject.getValue());
		}
		return set;
	}
}
