package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.TipoEjecucion;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class TipoEjecucionRepoImpl implements BaseRepository<TipoEjecucion,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(TipoEjecucionRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<TipoEjecucion> findAll() {
        String sql = "SELECT id, nombre, descripcion, porcentaje, peso  FROM maestro.tipo_ejecucion ";
        List<TipoEjecucion> list;
        try {
            list = (List<TipoEjecucion>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(TipoEjecucion.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public TipoEjecucion findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(TipoEjecucion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(TipoEjecucion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
