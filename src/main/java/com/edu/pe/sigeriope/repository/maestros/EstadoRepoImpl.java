package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.Estado;
import com.edu.pe.sigeriope.repository.BaseRepository;
import java.util.Collections;

@Repository
public class EstadoRepoImpl implements BaseRepository<Estado,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(EstadoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Estado> findAllByTipoEstadoId(int tipoEstadoid) {
        String sql = "SELECT id, nombre, descripcion, porcentaje, peso, tipoestado_id as tipoEstadoId  "+
        		 "FROM maestro.estado "+
        		 "WHERE tipoestado_id=? ";
        List<Estado> list;
        try {
            list = (List<Estado>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Estado.class),tipoEstadoid);
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Estado> findAll() {
        return Collections.emptyList();
    }

	@Override
	public Estado findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Estado t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(Estado t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
