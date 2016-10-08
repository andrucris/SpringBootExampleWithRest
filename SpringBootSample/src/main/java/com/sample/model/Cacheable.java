package com.sample.model;

/**
 * interface for cacheable type objects
 * 
 * @author andrucris
 *
 */
public interface Cacheable {

	/**
	 * @return the identifier
	 */
	public Object getIdentifier();

	/**
	 * @return the object
	 */
	public Object getObject();

}
