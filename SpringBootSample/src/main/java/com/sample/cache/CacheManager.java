package com.sample.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sample.model.Cacheable;
import com.sample.model.impl.CachedObject;

/**
 * create a singleton cache with lazy initialization the objects stored in the
 * cache are available during all the life of the application
 * 
 * 
 * @author andrucris
 *
 */
public class CacheManager {

	private static CacheManager instance;

	private CacheManager() {
	}

	/**
	 * @return the lazy instance
	 */
	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}

	/** The map where to cache the parameters */
	private final Map<Cacheable, String> cache = Collections.synchronizedMap(new HashMap<Cacheable, String>());

	/**
	 * insert cacheable objects into the cache
	 * 
	 * @param object
	 */
	public synchronized void putCache(Cacheable object, String result) {

		cache.put(object, result);
	}

	/**
	 * create cacheableObjects
	 * 
	 * @param obj
	 * @return
	 */
	public Cacheable createCacheableObject(Object obj) {
		return new CachedObject(obj.hashCode(), obj);
	}

	/**
	 * 
	 * Returns objects from the Cache
	 * 
	 * @return objects from the cache.
	 */
	public synchronized String getObject(Object key) {
		if (this.cache.size() >= 1) {

			return (String) cache.get(key);
		} else {
			return null;
		}
	}

	/**
	 * close the cache by clearing the hashmap
	 */
	public void shutDown() {
		clearCache();
		instance = null;

	}

	/**
	 * clear the hashmap
	 */
	public void clearCache() {
		this.cache.clear();
	}

	/**
	 * @return the cache
	 */
	public Map<Cacheable, String> getCache() {
		return cache;
	}

}
