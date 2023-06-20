/**
 * 
 */
package com.edu.pe.sigeriope.service;

import java.util.List;

/**
 * @author Ricardo Santos
 *
 */
public interface BaseServices<T, ID> {
	
	public int save(T t);
	
	public T findBy(ID id);
	
	public List<T> findAll();
	
	public int edit(T t);
	
	public boolean deleteById(ID id);

}
