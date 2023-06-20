package com.edu.pe.sigeriope.service;

import com.edu.pe.sigeriope.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Ricardo Santos
 *
 */
public abstract class BaseSvc<T, ID, R extends BaseRepository<T, ID>> {
	
	@Autowired
	protected R repository;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public int save(T t) {
		log.info("Guardando nuevos registros ->{}",t);
		return repository.save(t);
	}
	
	public T findBy(ID id){
		log.info("Busqueda por id ->{}",id);
		return repository.findBy(id);
	}
	
	public List<T> findAll(){
		log.info("Listar todos los registros");
		return repository.findAll();
	}
	
	
	public int edit(T t) {
		log.info("Actualizando registros existentes ->{}",t);
		return repository.edit(t);
	}
	
	
	public boolean deleteById(ID id) {
		boolean flag;
		log.info("Eliminar ->",id);
		try {
			repository.deleteById(id);
			flag=true;
		} catch (Exception e) {
			log.info("Error message ->{}  StackTrace->{} ",e.getMessage(),e.getStackTrace());
			flag= false;
		}
		
		return flag;
	}
}
