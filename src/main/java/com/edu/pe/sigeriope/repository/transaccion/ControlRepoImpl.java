/**
 * 
 */
package com.edu.pe.sigeriope.repository.transaccion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.sigeriope.bean.transaccion.Control;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;

/**
 * @author Ricardo Santos
 *
 */
@Repository
public class ControlRepoImpl  implements BaseRepository<Control,Integer>{

	public static final Logger log = LoggerFactory.getLogger(ProcesoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Override
	public List<Control> findAll() {
		 String sql = "select c.id, c.nombre , c.resultado_diseno_control, " + 
		 		" c.resultado_ejecucion_control , c.resultado_control , tc.nombre as tipoControl " + 
		 		" from transaccion.control c  " + 
		 		" inner join maestro.tipo_control tc on c.tipocontrol_id = tc.id "+
		 		" where c.delete_id=2 order by c.id ASC ";
		        List<Control> list;
		        try {
		        	list = (List<Control>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Control.class));
		        } catch (EmptyResultDataAccessException e) {
		        	list = null;
		            log.info(String.format("findAllControl %s",e.getMessage()));
		            // e.printStackTrace();
		        }
		        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Control findBy(Integer id) {
		String  sql = " SELECT id, tipocontrol_id as tipoControlId, tipoejecucion_id as tipoEjecucionId, "+
					  " frecuenciaejecucion_id as frecuenciaEjecucionId, "+
					  " estadodocumentacion_id as estadoDocumentacionId, estadoevidencia_id as estadoEvidenciaId, "+
					  " estadoresponsables_id as estadoResponsableId, "+
				  	  " estadoeventos_id as estadoEventoId, estadoefectividad_id as estadoEfectividadId, "+
				  	  " estadoevidencia2_id as estadoEvidencia2Id, "+
					  " resultado_diseno_control, resultado_ejecucion_control, descripcion, "+
					  " resultado_control, usuario_id, nombre " + 
					  " FROM transaccion.control where id=? ";

		return  (Control) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Control.class),id);

	}

	@Override
	public int save(Control t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO transaccion.control " + 
				"(tipocontrol_id, tipoejecucion_id, frecuenciaejecucion_id, estadodocumentacion_id, "
				+ " estadoevidencia_id, estadoresponsables_id, estadoeventos_id, estadoefectividad_id, "
				+ " estadoevidencia2_id, resultado_diseno_control, resultado_ejecucion_control, descripcion, "
				+ " resultado_control, usuario_id, nombre) " + 
				"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id";
        
        int rowsAffected=jdbcTemplate.update(c -> {
        	 PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	 ps.setInt(1, t.getTipoControlId());
        	 ps.setInt(2, t.getTipoEjecucionId());
        	 ps.setInt(3, t.getFrecuenciaEjecucionId());
        	 ps.setInt(4, t.getEstadoDocumentacionId());
        	 ps.setInt(5, t.getEstadoEvidenciaId());
        	 ps.setInt(6,t.getEstadoResponsableId());
        	 ps.setInt(7,t.getEstadoEventoId());
        	 ps.setInt(8,t.getEstadoEfectividadId());
        	 ps.setInt(9,t.getEstadoEvidencia2Id());
        	 ps.setBigDecimal(10,t.getResultadoDisenoControl());
        	 ps.setBigDecimal(11,t.getResultadoEjecucionControl());
        	 ps.setString(12,t.getDescripcion());
        	 ps.setBigDecimal(13,t.getResultadoControl());
        	 ps.setInt(14,t.getUsuarioId());
        	 ps.setString(15,t.getNombre());
        	 return ps;
        },keyHolder);
        int id=keyHolder.getKey().intValue();
        log.info("rowsAffected = {}, id={}", rowsAffected, id);
        return id;
	}

	@Override
	public int edit(Control t) {
		String sql = "UPDATE transaccion.control " + 
					 " SET tipocontrol_id=?, tipoejecucion_id=?, frecuenciaejecucion_id=?, "+
					 " estadodocumentacion_id=?, estadoevidencia_id=?, estadoresponsables_id=?, "+
					 " estadoeventos_id=?, estadoefectividad_id=?, estadoevidencia2_id=?, "+
					 " resultado_diseno_control=?, resultado_ejecucion_control=?, descripcion=?, "+
					 " resultado_control=?, usuario_id=?, nombre=? " + 
					 "WHERE id=? ";

	   return jdbcTemplate.update(sql, new Object[] {
	   		t.getTipoControlId(),
	   		t.getTipoEjecucionId(),
	   		t.getFrecuenciaEjecucionId(),
	   		t.getEstadoDocumentacionId(),
	   		t.getEstadoEvidenciaId(),
	   		t.getEstadoResponsableId(),
	   		t.getEstadoEventoId(),
	   		t.getEstadoEfectividadId(),
	   		t.getEstadoEvidencia2Id(),
	   		t.getResultadoDisenoControl(),
	   		t.getResultadoEjecucionControl(),
	   		t.getDescripcion(),
	   		t.getResultadoControl(),
	   		t.getUsuarioId(),
	   		t.getNombre(),
	   		t.getId()
	   });
	}

	@Transactional
	@Override
	public int deleteById(Integer id) {
		String sql = "UPDATE transaccion.control SET delete_id=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
	}
	
	public void insertControlRiesgo(Control t) {

		String sql = " INSERT INTO transaccion.riesgocontrol " + 
				" (control_id,riesgo_id ) " + 
				" VALUES(?, ?) ";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, t.getId());
				ps.setLong(2, t.getRiesgos().get(i));

			}

			@Override
			public int getBatchSize() {
				return t.getRiesgos().size();
			}

		});
	}
	
	public List<Integer> findRiesgo(Integer controlId) {
		String  sql = " SELECT riesgo_id  " + 
					  " FROM transaccion.riesgocontrol where control_id=? ";

		List<Integer> list;
        try {
        	list = jdbcTemplate.query(sql, new RowMapper<Integer>() {

				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt(1);
				}
        		
        	},controlId);
        } catch (EmptyResultDataAccessException e) {
        	list = new ArrayList<>();
        	e.printStackTrace();
        }
        return list;
	}
	
	public int deleteRiesgoById(int controlId) {
        String sql = " DELETE FROM transaccion.riesgocontrol " + 
        			 " WHERE control_id = ?";
        return jdbcTemplate.update(sql, controlId);
    }

}
