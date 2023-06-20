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

import com.edu.pe.sigeriope.bean.transaccion.Causa;
import com.edu.pe.sigeriope.bean.transaccion.Consecuencia;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;

/**
 * @author Ricardo Santos
 *
 */
@Repository
public class RiesgoRepoImpl implements BaseRepository<Riesgo,Integer>{

	public static final Logger log = LoggerFactory.getLogger(ProcesoRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Riesgo> findAll() {
		 String sql = "select " + 
		 		"	r.id, " + 
		 		"	r.nombre, " + 
		 		"	r.afecta_negocio, " + 
		 		"	r.impactoriesgo_id, " + 
		 		"	r.frecuenciariesgo_id, " + 
		 		"	r.usuario_id, " + 
		 		"	fe.nombre as frecuenciaEjecucionNombre, " + 
		 		"	fe.porcentaje as frecuenciaEjecucionPorcentaje, " + 
		 		"	ir.nombre as impactoRiesgoNombre, " + 
		 		"	ir.porcentaje as impactoRiesgoPorcentaje,  " + 
		 		"   u.persona_nombre ||' '||u.persona_apellido as responsable"+
		 		" from " + 
		 		"	transaccion.riesgo r " + 
		 		" inner join maestro.frecuencia_riesgo fe on " + 
		 		"	r.frecuenciariesgo_id = fe.id " + 
		 		" inner join maestro.impacto_riesgo ir on r.impactoriesgo_id=ir.id "+
		 		" inner join administrativo.usuario u on r.usuario_id = u.usuario_id "+
		 		" where r.delete_id=2 order by r.id ASC";
	        List<Riesgo> list;
	        try {
	        	list = (List<Riesgo>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Riesgo.class));
	        } catch (EmptyResultDataAccessException e) {
	        	list = null;
	            log.info(String.format("findAllRiesgo %s",e.getMessage()));
	            // e.printStackTrace();
	        }
	        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Riesgo findBy(Integer id) {
		String  sql = " SELECT id, nombre, descripcion, afecta_negocio, impactoriesgo_id as impactoRiesgoId, "+
					  " frecuenciariesgo_id as frecuenciaRiesgoId, usuario_id " + 
					  " FROM transaccion.riesgo where id=? ";

        return  (Riesgo) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Riesgo.class),id);
	}

	@Override
	public int save(Riesgo t) {
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO transaccion.riesgo (nombre, descripcion, afecta_negocio, impactoriesgo_id, frecuenciariesgo_id, usuario_id) " +
                "VALUES(?, ?, ?, ?, ?, ?) returning id ";
        
        int rowsAffected=jdbcTemplate.update(c -> {
        	 PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	 ps.setString(1, t.getNombre());
        	 ps.setString(2, t.getDescripcion());
        	 ps.setString(3, t.getAfectaNegocio());
        	 ps.setInt(4, t.getImpactoRiesgoId());
        	 ps.setInt(5, t.getFrecuenciaRiesgoId());
        	 ps.setInt(6, t.getUsuarioId());
        	 return ps;
        },keyHolder);
        int id=keyHolder.getKey().intValue();
        log.info("rowsAffected = {}, id={}", rowsAffected, id);
        return id;
	}

	@Override
	public int edit(Riesgo t) {
		String sql = "UPDATE transaccion.riesgo " + 
					 " SET nombre=?, descripcion=?, afecta_negocio=?, impactoriesgo_id=?, frecuenciariesgo_id=?, usuario_id=? " + 
					 "WHERE id=? ";

        return jdbcTemplate.update(sql, new Object[] {
        		t.getNombre(),
        		t.getDescripcion(),
        		t.getAfectaNegocio(),
        		t.getImpactoRiesgoId(),
        		t.getFrecuenciaRiesgoId(),
        		t.getUsuarioId(),
        		t.getId()
        });
	}

	@Transactional
	@Override
	public int deleteById(Integer id) {
		String sql = "UPDATE transaccion.riesgo SET delete_id=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
	}
	
	public void insertRiesgoProceso(Riesgo t) {

		String sql = " INSERT INTO transaccion.riesgoproceso " + 
				" (riesgo_id, proceso_id) " + 
				" VALUES(?, ?) ";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, t.getId());
				ps.setLong(2, t.getProcesos().get(i));

			}

			@Override
			public int getBatchSize() {
				return t.getProcesos().size();
			}

		});
	}
	
	 public void insertRiesgoConsecuencias(Riesgo t) {

	        String sql = "INSERT INTO transaccion.riesgo_consecuencia " +
	                "(descripcion, riesgo_id, tipoconsecuencia_id) " +
	                "VALUES(?, ?, ?)";

	        List<Object[]> batchArgsList = new ArrayList<Object[]>();
	        for (Consecuencia c: t.getConsecuencias()) {
	            Object[] objectArray = {
	                    c.getDescripcion(),
	                    t.getId(),
	                    c.getTipoConsecuenciaId()
	            };
	            batchArgsList.add(objectArray);
	        }
	        jdbcTemplate.batchUpdate(sql, batchArgsList);
	    }
	 
	 public void insertRiesgoCausa(Riesgo t) {

	        String sql = "INSERT INTO transaccion.riesgo_causa " +
	                "(descripcion, riesgo_id, tipocausa_id) " +
	                "VALUES(?, ?, ?)";

	        List<Object[]> batchArgsList = new ArrayList<Object[]>();
	        for (Causa c: t.getCausas()) {
	            Object[] objectArray = {
	                    c.getDescripcion(),
	                    t.getId(),
	                    c.getTipoCausaId()
	            };
	            batchArgsList.add(objectArray);
	        }
	        jdbcTemplate.batchUpdate(sql, batchArgsList);
	    }
	
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Causa> findByCausa(Integer id) {
		String  sql = " SELECT id, riesgo_id, tipocausa_id as tipoCausaId, descripcion " + 
					  " FROM transaccion.riesgo_causa where riesgo_id=?";
		List<Causa> list;
        try {
        	list = (List<Causa>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Causa.class),id);
        } catch (EmptyResultDataAccessException e) {
        	list = new ArrayList<>();
        }
        return list;
	}
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Consecuencia> findByConsecuencia(Integer id) {
		String  sql = " SELECT id, riesgo_id, tipoconsecuencia_id as tipoConsecuenciaId, descripcion " + 
					  " FROM transaccion.riesgo_consecuencia where riesgo_id=? ";

		List<Consecuencia> list;
        try {
        	list = (List<Consecuencia>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Consecuencia.class),id);
        } catch (EmptyResultDataAccessException e) {
        	list = new ArrayList<>();
        }
        return list;
	}
	
	public List<Integer> findByProceso(Integer id) {
		String  sql = " SELECT proceso_id " + 
					  " FROM transaccion.riesgoproceso where riesgo_id=? ";

		List<Integer> list;
        try {
        	list = jdbcTemplate.query(sql, new RowMapper<Integer>() {

				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt(1);
				}
        		
        	},id);
        } catch (EmptyResultDataAccessException e) {
        	list = new ArrayList<>();
        	e.printStackTrace();
        }
        return list;
	}
	
	public int deleteConsecuenciaById(int riesgoId) {
        String sql = "DELETE FROM transaccion.riesgo_consecuencia " + 
        		     " WHERE riesgo_id = ?";
        return jdbcTemplate.update(sql, riesgoId);
    }
	
	public int deleteCausaById(int riesgoId) {
        String sql = "DELETE FROM transaccion.riesgo_causa " + 
        		     " WHERE riesgo_id = ?";
        return jdbcTemplate.update(sql, riesgoId);
    }
	
	public int deleteProcesoById(int riesgoId) {
        String sql = "DELETE FROM transaccion.riesgoproceso " + 
        		     " WHERE riesgo_id = ?";
        return jdbcTemplate.update(sql, riesgoId);
    }

}
