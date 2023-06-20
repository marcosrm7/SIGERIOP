package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.Causa;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class CausaRepoImpl implements BaseRepository<Causa,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(CausaRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Causa> findAll() {
        String sql = "SELECT id, nombre FROM maestro.tipo_causa ";
        List<Causa> list;
        try {
            list = (List<Causa>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Causa.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	@Override
	public Causa findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Causa t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(Causa t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
