package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.FrecuenciaRiesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class FrecuenciaRiesgoRepoImpl implements BaseRepository<FrecuenciaRiesgo,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(FrecuenciaRiesgoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<FrecuenciaRiesgo> findAll() {
        String sql = "SELECT id, nombre, porcentaje  FROM maestro.frecuencia_riesgo";
        List<FrecuenciaRiesgo> list;
        try {
            list = (List<FrecuenciaRiesgo>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(FrecuenciaRiesgo.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public FrecuenciaRiesgo findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(FrecuenciaRiesgo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(FrecuenciaRiesgo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
