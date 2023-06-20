package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.Origen;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class OrigenRepoImpl implements BaseRepository<Origen,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(OrigenRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Origen> findAll() {
        String sql = "SELECT id, nombre FROM maestro.origen ";
        List<Origen> list;
        try {
            list = (List<Origen>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Origen.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public Origen findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Origen t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(Origen t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
