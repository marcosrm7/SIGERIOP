package com.edu.pe.sigeriope.repository.transaccion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.repository.BaseRepository;
@Repository
public class ProcesoRepoImpl implements BaseRepository<Proceso,Integer> {
    public static final Logger log = LoggerFactory.getLogger(ProcesoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<Proceso> findAll() {
        String sql = "select p.id, p.descripcion, p.tipoproceso_id, p.categoria_id, cp.nombre  as categoria_nombre, " +
                " tp.nombre  as tipoproceso_nombre , p.usuario_id, p.nombre from transaccion.proceso p " +
                " inner join maestro.tipo_proceso tp on tp.id = p.tipoproceso_id " +
                " inner join maestro.categoria_proceso cp on cp.id = p.categoria_id "+
                " where p.delete_id=2 order by p.id ASC";
        List<Proceso> procesoList;
        try {
            procesoList = (List<Proceso>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Proceso.class));
        } catch (EmptyResultDataAccessException e) {
            procesoList = null;
            log.info(String.format("saveProcesoo %s",e.getMessage()));
            // e.printStackTrace();
        }
        return procesoList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Proceso findBy(Integer id) {
    	String  sql = "SELECT id, descripcion, tipoproceso_id, categoria_id, usuario_id, nombre " + 
    				  " FROM transaccion.proceso "+
    				  " where id=?";
    	 return  (Proceso) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Proceso.class),id);
    }

    @Override
    @Transactional
    public int save(Proceso proceso) {
        String sql = "INSERT INTO transaccion.proceso (descripcion, tipoproceso_id, categoria_id, usuario_id, nombre) " +
                "VALUES(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {
                proceso.getDescripcion(),
                proceso.getTipoprocesoId(),
                proceso.getCategoriaId(),
                proceso.getUsuarioId(),
                proceso.getNombre()
        });
    }

    @Override
    @Transactional
    public int edit(Proceso proceso) {
    	String sql = "UPDATE transaccion.proceso " + 
    			" SET descripcion=?, tipoproceso_id=?, categoria_id=?, usuario_id=?, nombre=? " + 
    			" WHERE id=? ";
        return jdbcTemplate.update(sql, new Object[] {
                proceso.getDescripcion(),
                proceso.getTipoprocesoId(),
                proceso.getCategoriaId(),
                proceso.getUsuarioId(),
                proceso.getNombre(),
                proceso.getId()
        });
    }
    
    @Transactional
    @Override
    public int deleteById(Integer id) {
    	String sql = "UPDATE transaccion.proceso SET delete_id=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}
