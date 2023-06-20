package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.TipoEvento;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class TipoEventoRepoImpl implements BaseRepository<TipoEvento,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(TipoEventoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<TipoEvento> findAll() {
        String sql = "SELECT id, nombre FROM maestro.tipo_evento";
        List<TipoEvento> list;
        try {
            list = (List<TipoEvento>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(TipoEvento.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public TipoEvento findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(TipoEvento t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(TipoEvento t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
