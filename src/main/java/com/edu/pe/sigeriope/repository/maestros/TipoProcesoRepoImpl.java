package com.edu.pe.sigeriope.repository.maestros;

import com.edu.pe.sigeriope.bean.maestros.TipoProceso;
import com.edu.pe.sigeriope.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoProcesoRepoImpl implements BaseRepository<TipoProceso,Integer> {

    public static final Logger log = LoggerFactory.getLogger(TipoProcesoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TipoProceso> findAll() {
        String sql = "SELECT id, nombre FROM maestro.tipo_proceso";
        List<TipoProceso> tipoProcesoList;
        try {
            tipoProcesoList = (List<TipoProceso>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(TipoProceso.class));
        } catch (EmptyResultDataAccessException e) {
            tipoProcesoList = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return tipoProcesoList;
    }

    @Override
    public TipoProceso findBy(Integer integer) {
        return null;
    }

    @Override
    public int save(TipoProceso tipoProceso) {
        return 0;
    }

    @Override
    public int edit(TipoProceso tipoProceso) {
        return 0;
    }

    @Override
    public int deleteById(Integer integer) {
        return 0;
    }

    public int eliminar(Integer integer) {
        return 0;
    }

}
