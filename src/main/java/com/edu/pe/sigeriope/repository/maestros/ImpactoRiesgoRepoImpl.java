package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.ImpactoRiesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class ImpactoRiesgoRepoImpl implements BaseRepository<ImpactoRiesgo,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(ImpactoRiesgoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<ImpactoRiesgo> findAll() {
        String sql = "SELECT id, nombre, porcentaje FROM maestro.impacto_riesgo	";
        List<ImpactoRiesgo> list;
        try {
            list = (List<ImpactoRiesgo>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(ImpactoRiesgo.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public ImpactoRiesgo findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(ImpactoRiesgo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(ImpactoRiesgo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
