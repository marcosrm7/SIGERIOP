package com.edu.pe.sigeriope.repository.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.Evento;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventoRepoImpl implements BaseRepository<Evento, Integer> {
    public static final Logger log = LoggerFactory.getLogger(EventoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Evento> findAll() {

        String  sql = "SELECT e.id, e.nombre, e.descripcion, e.genera_perdida, e.fecha_inicio_evento, " +
                " e.fecha_descubrimiento_evento, e.tipoevento_id, e.monto_perdida, e.criticidad_id, " +
                " e.proceso_id, e.usuario_id, e.lugar, CONCAT(u.persona_nombre, ' ', u.persona_apellido) as " +
                " responsable , c.nombre as criticidad " +
                " FROM transaccion.evento e " +
                " inner join administrativo.usuario u on e.usuario_id = u.usuario_id " +
                " inner join maestro.criticidad c  on c.id = e.criticidad_id "+
                " where e.delete_id=2 order by  e.id ASC ";
        List<Evento> eventoList;
        try {
            eventoList = (List<Evento>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Evento.class));
        } catch (EmptyResultDataAccessException e) {
            eventoList = null;
            //log.info(String.format("proceso %s",e.getMessage()));
            // e.printStackTrace();
        }
        return eventoList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Evento findBy(Integer id) {
    	String  sql = " SELECT id, nombre, descripcion, genera_perdida, fecha_inicio_evento, "+
    				  " fecha_descubrimiento_evento, tipoevento_id, monto_perdida, criticidad_id, "+
    				  " proceso_id, usuario_id, lugar " + 
    				  " FROM transaccion.evento where id=? ";

    	return  (Evento) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Evento.class),id);

    }

    @Override
    public int save(Evento evento) {

        String sql = "INSERT INTO transaccion.evento " +
                "(nombre, descripcion, genera_perdida, fecha_inicio_evento, fecha_descubrimiento_evento, " +
                "tipoevento_id,monto_perdida, criticidad_id, proceso_id, usuario_id, lugar) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getGeneraPerdida(),
                evento.getFechaInicioEvento(),
                evento.getFechaDescubrimientoEvento(),
                evento.getTipoeventoId(),
                evento.getMontoPerdida(),
                evento.getCriticidadId(),
                evento.getProcesoId(),
                evento.getUsuarioId(),
                evento.getLugar()
        });
    }

    @Override
    public int edit(Evento evento) {
    	String sql = " UPDATE transaccion.evento " + 
    				 " SET nombre=?, descripcion=?, genera_perdida=?, fecha_inicio_evento=?, "+
    				 " fecha_descubrimiento_evento=?, tipoevento_id=?, monto_perdida=?, "+
    				 " criticidad_id=?, proceso_id=?, usuario_id=?, lugar=? " + 
    				 " WHERE id=? ";

	   return jdbcTemplate.update(sql, new Object[] {
			   evento.getNombre(),
               evento.getDescripcion(),
               evento.getGeneraPerdida(),
               evento.getFechaInicioEvento(),
               evento.getFechaDescubrimientoEvento(),
               evento.getTipoeventoId(),
               evento.getMontoPerdida(),
               evento.getCriticidadId(),
               evento.getProcesoId(),
               evento.getUsuarioId(),
               evento.getLugar(),
               evento.getId()
	   });
    }

    @Transactional
    @Override
    public int deleteById(Integer id) {
    	String sql = "UPDATE transaccion.evento SET delete_id=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
    
	public Integer findByTipoEvento(Integer id) {
		String  sql = " select te.id from maestro.tipo_evento_categoria_evento tece  " + 
					  " inner join maestro.tipo_evento te on tece.tipoevento_id = te.id  " + 
					  " where tece.tipocategoriaevento_id =? ";

        return  jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
        	
        },id);
	}
}
