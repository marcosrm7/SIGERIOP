package com.edu.pe.sigeriope.repository.maestros;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.maestros.CategoriaProceso;
import com.edu.pe.sigeriope.repository.BaseRepository;

@Repository
public class CategoriaProcesoRepoImpl implements BaseRepository<CategoriaProceso,Integer>  {
    public static final Logger log = LoggerFactory.getLogger(CategoriaProcesoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<CategoriaProceso> findAll() {
        String sql = "SELECT id, nombre FROM maestro.categoria_proceso";
        List<CategoriaProceso> categoriaProcesoList;
        try {
            categoriaProcesoList = (List<CategoriaProceso>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(CategoriaProceso.class));
        } catch (EmptyResultDataAccessException e) {
            categoriaProcesoList = null;
            //System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return categoriaProcesoList;
    }

    @Override
    public CategoriaProceso findBy(Integer integer) {
        return null;
    }

    @Override
    public int save(CategoriaProceso categoriaProceso) {
        return 0;
    }

    @Override
    public int edit(CategoriaProceso categoriaProceso) {
        return 0;
    }

    @Override
    public int deleteById(Integer integer) {
        return 0;
    }
}
