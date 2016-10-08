package com.sample.model.impl;

import com.sample.model.Cacheable;

/**
 * class for objects that can be cached and that should implement interface
 * Cacheable
 * 
 * @author andrucris
 *
 */
public class CachedObject implements Cacheable {
	/**
	 * Id contains a unique identifier that distinguishes the obj parameter from
	 * all other objects residing in the cache.
	 */
	private Object identifier = null;

	/**
	 * The object that is shared
	 */
	public Object object = null;

	public CachedObject(Object id, Object obj) {
		this.object = obj;
		this.identifier = id;

	}

	/**
	 * @return the unique identifier for the object
	 */
	@Override
	public Object getIdentifier() {
		return this.identifier;
	}

	/**
	 * @return the object
	 */
	@Override
	public Object getObject() {
		return this.object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CachedObject other = (CachedObject) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

}
