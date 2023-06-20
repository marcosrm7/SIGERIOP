package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.TipoCategoriaEvento;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class TipoCategoriaEventoRepoImpl implements BaseRepository<TipoCategoriaEvento,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(TipoCategoriaEventoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<TipoCategoriaEvento> findAll() {
        String sql = "SELECT id, nombre FROM maestro.tipo_categoria_evento";
        List<TipoCategoriaEvento> list;
        try {
            list = (List<TipoCategoriaEvento>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(TipoCategoriaEvento.class));
        } catch (EmptyResultDataAccessException e) {
            list = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
    }

	public List<TipoCategoriaEvento> findAllByTipoEventoId(int tipoEventoId) {
		String sql = "SELECT tce.id, tce.nombre " +
				"FROM maestro.tipo_categoria_evento tce " +
				"inner join maestro.tipo_evento_categoria_evento tece on tece.tipocategoriaevento_id = tce.id " +
				"where tece.tipoevento_id = ?";
		List<TipoCategoriaEvento> list;
		try {
			list = (List<TipoCategoriaEvento>) jdbcTemplate.query(
					sql,
					new BeanPropertyRowMapper(TipoCategoriaEvento.class),
					new Object[] { tipoEventoId });
		} catch (EmptyResultDataAccessException e) {
			list = null;
			// e.printStackTrace();
		}
		return list;
	}

	@Override
	public TipoCategoriaEvento findBy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(TipoCategoriaEvento t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(TipoCategoriaEvento t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

   
}
