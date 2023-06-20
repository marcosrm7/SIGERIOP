/**
 * 
 */
package com.edu.pe.sigeriope.repository;

import java.util.List;

/**
 * @author Ricardo Santos
 *
 */
public interface BaseRepository<T, ID> {

	public List<T> findAll();

	public T findBy(ID id);

	public int save(T t);

	public int edit(T t);
	
	public int deleteById(ID id);
	

}
