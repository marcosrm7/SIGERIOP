package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.FrecuenciaEjecucion;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class FrecuenciaEjecucionRepoImpl implements BaseRepository<FrecuenciaEjecucion,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(FrecuenciaEjecucionRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<FrecuenciaEjecucion> findAll() {
        String sql = "SELECT id, nombre, descripcion, porcentaje, peso  FROM maestro.frecuencia_ejecucion";
        List<FrecuenciaEjecucion> list;
        try {
            list = (List<FrecuenciaEjecucion>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(FrecuenciaEjecucion.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public FrecuenciaEjecucion findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(FrecuenciaEjecucion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(FrecuenciaEjecucion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
