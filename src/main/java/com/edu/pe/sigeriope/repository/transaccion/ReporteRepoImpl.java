/**
 * 
 */
package com.edu.pe.sigeriope.repository.transaccion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.edu.pe.sigeriope.dto.reportes.CategoriaEventoReporteDto;
import com.edu.pe.sigeriope.dto.reportes.CausaConsecuenciasReportesDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteFilterDto;
import com.edu.pe.sigeriope.dto.reportes.ReportePdf;
import com.edu.pe.sigeriope.repository.ReporteRepository;

/**
 * @author Ricardo Santos
 *
 */
@Repository
public class ReporteRepoImpl implements ReporteRepository {

	public static final Logger log = LoggerFactory.getLogger(ProcesoRepoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteDto> findAll() {
		String sql = " select r.id as riesgoId, r.nombre as riesgoNombre, " + 
				" ir.nombre as impactoRiesgo, fr.nombre as frecuenciaRiesgo, " + 
				" p.id as procesoId, p.nombre  as procersoNombre, " + 
				" c.id as controlId, c.nombre  as controlNombre, " + 
				" pa.id as planAccionId, pa.nombre  as planAccionNombre, " + 
				" e.id as eventoId, e.nombre  as eventoNombre, " + 
				" case when ((ir.porcentaje/100)*(fr.porcentaje/100)) < 0.25 then 'bajo' " + 
				"	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.25 and ((ir.porcentaje/100)*(fr.porcentaje/100)) <0.70 then 'medio' " + 
				"	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.70 then 'alto' " + 
				" end as nivelRiesgo, " + 
				" case when c.resultado_control < 25 then 'bajo' " + 
				"	 when c.resultado_control >= 25 and c.resultado_control <70 then 'medio' " + 
				"	 when c.resultado_control >= 70 then 'alto' " + 
				" end as resultadoControl, c.resultado_control as resultadoControlPorcentaje "+
				" from transaccion.riesgo r  " + 
				" inner join transaccion.riesgoproceso rp on r.id = rp.riesgo_id  " + 
				" inner join transaccion.proceso p on rp.proceso_id = p.id  " + 
				" inner join transaccion.riesgocontrol rc on r.id = rc.riesgo_id " + 
				" inner join transaccion.control c on rc.control_id = c.id " + 
				" inner join transaccion.plan_accion_riesgo par on r.id = par.riesgo_id " + 
				" inner join transaccion.plan_accion pa on par.planaccion_id = pa.id  " + 
				" inner join transaccion.evento e on p.id = e.proceso_id " + 
				" inner join maestro.impacto_riesgo ir on r.impactoriesgo_id = ir.id  " + 
				" inner join maestro.frecuencia_riesgo fr  on r.frecuenciariesgo_id  = fr.id  " + 
				" where r.delete_id=2 and p.delete_id=2 and c.delete_id=2 and "+
				" pa.delete_id=2 and e.delete_id=2 order by r.id ASC ";
		List<ReporteDto> list;
		try {
			list = (List<ReporteDto>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReporteDto.class));
		} catch (EmptyResultDataAccessException e) {
			list = null;
			log.info(String.format("findAllControl %s", e.getMessage()));
			// e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ReportePdf findByPdf(ReporteFilterDto obj) {
		 String sql = " select e.id as eventoId, e.nombre as  eventoNombre, e.descripcion as eventoDescripcion, e.fecha_inicio_evento as eventoFechaInicio, " +
		 		" p.id as procesoId, p.nombre  as procesoNombre, p.descripcion as procesoDescripcion, cp.nombre as procesoCategoria,  " + 
		 		" tp.nombre as procesoTipo, u.persona_nombre ||' '|| u.persona_apellido as responsable, a.nombre as areaNombre, " + 
		 		" e.monto_perdida as montoPerdida, ir.nombre as impactoRiesgo ,fr.nombre as frecuenciaRiesgo, r.nombre as riesgoNombre, r.descripcion as riesgoDescripcion, " +
		 		" ir.porcentaje  as impactoRiesgoPorcentaje, fr.porcentaje  as frecuenciaRiesgoPorcentaje, "+
		 		" ((ir.porcentaje/100)*(fr.porcentaje/100))  as nivelRiesgoPorcentaje, te.nombre as tipoEvento, "+
		 		" case when ((ir.porcentaje/100)*(fr.porcentaje/100)) < 0.25 then 'bajo' " + 
		 		"	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.25 and ((ir.porcentaje/100)*(fr.porcentaje/100)) <0.70 then 'medio' " + 
		 		"	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.70 then 'alto' " + 
		 		" end as nivelRiesgo, " + 
		 		" pa.nombre as planNombre, pa.descripcion as planDescripcion, " + 
		 		" c.nombre as controlNombre, c.descripcion as controlDescripcion, tc.nombre as tipoControl, te2.nombre as tipoEjecucion, " +
		 		" c.resultado_diseno_control as resultaDisenoControlPorcentaje,  " + 
		 		" c.resultado_ejecucion_control as  resultadoEjecucionControlPorcentaje, " + 
		 		" c.resultado_control as resultadoControlPorcentaje, " + 
		 		"  case when c.resultado_diseno_control < 25 then 'bajo' " + 
		 		"	 when c.resultado_diseno_control >= 25 and c.resultado_diseno_control <70 then 'medio' " + 
		 		"	 when c.resultado_diseno_control >= 70 then 'alto' " + 
		 		" end as resultaDisenoControl, " + 
		 		" case when c.resultado_ejecucion_control < 25 then 'bajo' " + 
		 		"	 when c.resultado_ejecucion_control >= 25 and c.resultado_ejecucion_control <70 then 'medio' " + 
		 		"	 when c.resultado_ejecucion_control >= 70 then 'alto' " + 
		 		" end as resultadoEjecucionControl , " + 
		 		" case when c.resultado_control < 25 then 'bajo' " + 
		 		"	 when c.resultado_control >= 25 and c.resultado_control <70 then 'medio' " + 
		 		"	 when c.resultado_control >= 70 then 'alto' " + 
		 		" end as resultadoControl" +
		 		" from transaccion.riesgo r  " + 
		 		" inner join transaccion.riesgoproceso rp on r.id = rp.riesgo_id  " + 
		 		" inner join transaccion.proceso p on rp.proceso_id = p.id  " + 
		 		" inner join transaccion.riesgocontrol rc on r.id = rc.riesgo_id " + 
		 		" inner join transaccion.control c on rc.control_id = c.id " + 
		 		" inner join transaccion.plan_accion_riesgo par on r.id = par.riesgo_id " + 
		 		" inner join transaccion.plan_accion pa on par.planaccion_id = pa.id  " + 
		 		" inner join transaccion.evento e on p.id = e.proceso_id " + 
		 		" inner join maestro.impacto_riesgo ir on r.impactoriesgo_id = ir.id  " + 
		 		" inner join maestro.frecuencia_riesgo fr  on r.frecuenciariesgo_id  = fr.id  " + 
		 		" inner join maestro.categoria_proceso cp on p.categoria_id = cp.id " + 
		 		" inner join maestro.tipo_proceso tp on p.tipoproceso_id = tp.id " + 
		 		" inner join maestro.tipo_categoria_evento te on e.tipoevento_id = te.id  " + 
		 		" inner join administrativo.usuario u on p.usuario_id = u.usuario_id  " + 
		 		" inner join maestro.tipo_control tc on c.tipocontrol_id = tc.id  " + 
		 		" inner join maestro.tipo_ejecucion te2 on c.tipoejecucion_id = te2.id  " + 
		 		" inner join administrativo.area a on u.area_id = a.id " + 
		 		" where r.id = ? and p.id = ? and c.id = ? and pa.id = ? and e.id = ? " + 
		 		"order by r.id ASC";

		 ReportePdf reportePdf;
	        try {
	        	reportePdf = (ReportePdf) jdbcTemplate.queryForObject(
	                    sql, new BeanPropertyRowMapper(ReportePdf.class),
	                    new Object[] {
	                    		obj.getRiesgoId(),
	                    		obj.getProcesoId(),
	                    		obj.getControlId(),
	                    		obj.getPlanAccionId(),
	                    		obj.getEventoId()
	                    }//riesgoId -> 11 - procesoId -> 3 - controlId -> 8 - planAccionId -> 4 - eventoId -> 6
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	reportePdf = null;
	            System.out.println(e.getMessage());
	            // e.printStackTrace();
	        }
	        return reportePdf;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CategoriaEventoReporteDto> categoriaEventoFindByEvento(int eventoId) {
		String sql = " select tce.id , tce.nombre  from maestro.tipo_categoria_evento tce " + 
				" inner join maestro.tipo_evento_categoria_evento tece on tce.id = tece.tipocategoriaevento_id  " + 
				" where tece.tipoevento_id = ?  order by  tce.id asc ";

        List<CategoriaEventoReporteDto> list;
        try {
            list = (List<CategoriaEventoReporteDto>) jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper(CategoriaEventoReporteDto.class),
                                                   new Object[] {
                                                		   eventoId
                    }
            );

        } catch (EmptyResultDataAccessException e) {
            list = null;
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ActividadesPlan> actividadesPlanFindByPlan(int planId) {
		String sql = " select ap.id, ap.descripcion, e.nombre as estado from transaccion.actividades_plan ap   " + 
				" inner join maestro.estado e on ap.estado_id = e.id  " + 
				" where ap.planaccion_id = ? order by ap.id asc ";

        List<ActividadesPlan> list;
        try {
            list = (List<ActividadesPlan>) jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper(ActividadesPlan.class),
                                                   new Object[] {
                                                		   planId
                    }
            );

        } catch (EmptyResultDataAccessException e) {
            list = null;
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CausaConsecuenciasReportesDto> causaFindByRiesgo(int riesgoId) {
		String sql = " select tc.nombre , rc.descripcion  from maestro.tipo_causa tc " + 
				" inner join transaccion.riesgo_causa rc on tc.id = rc.tipocausa_id  " + 
				" where rc.riesgo_id = ? order by tc.id asc ";

        List<CausaConsecuenciasReportesDto> list;
        try {
            list = (List<CausaConsecuenciasReportesDto>) jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper(CausaConsecuenciasReportesDto.class),
                                                   new Object[] {
                                                		   riesgoId
                    }
            );

        } catch (EmptyResultDataAccessException e) {
            list = null;
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CausaConsecuenciasReportesDto> consecuenciaFindByRiesgo(int riesgoId) {
		String sql = " select tc.nombre , rc.descripcion  from maestro.tipo_consecuencia  tc " + 
				" inner join transaccion.riesgo_consecuencia rc on tc.id = rc.tipoconsecuencia_id   " + 
				" where rc.riesgo_id = ? order by tc.id asc ";

        List<CausaConsecuenciasReportesDto> list;
        try {
            list = (List<CausaConsecuenciasReportesDto>) jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper(CausaConsecuenciasReportesDto.class),
                                                   new Object[] {
                                                		   riesgoId
                    }
            );

        } catch (EmptyResultDataAccessException e) {
            list = null;
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
        return list;
	}

	@Override
	public int countProcesos() {
		String sql = "select count(*) from transaccion.proceso where delete_id=2";

		 int resp;
	        try {
	        	resp = jdbcTemplate.queryForObject(
	                    sql, Integer.class
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	resp = 0;
	            System.out.println(e.getMessage());
	        }
	        return resp;
	}

	@Override
	public int countEventos() {
		String sql = "select count(*) from transaccion.evento where delete_id=2";

		 int resp;
	        try {
	        	resp = jdbcTemplate.queryForObject(
	                    sql, Integer.class
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	resp = 0;
	            System.out.println(e.getMessage());
	        }
	        return resp;
	}

	@Override
	public int countRiesgos() {
		String sql = "select count(*) from transaccion.riesgo where delete_id=2";

		 int resp;
	        try {
	        	resp = jdbcTemplate.queryForObject(
	                    sql, Integer.class
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	resp = 0;
	            System.out.println(e.getMessage());
	        }
	        return resp;
	}

	@Override
	public int countControles() {
		String sql = "select count(*) from transaccion.control where delete_id=2";

		 int resp;
	        try {
	        	resp = jdbcTemplate.queryForObject(
	                    sql, Integer.class
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	resp = 0;
	            System.out.println(e.getMessage());
	        }
	        return resp;
	}

	@Override
	public int countPlanes() {
		String sql = "select count(*) from transaccion.plan_accion where delete_id=2";

		 int resp;
	        try {
	        	resp = jdbcTemplate.queryForObject(
	                    sql, Integer.class
	            );

	        } catch (EmptyResultDataAccessException e) {
	        	resp = 0;
	            System.out.println(e.getMessage());
	        }
	        return resp;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReporteDto> findAllFilter(ReporteFilterDto obj) {
		String sql = " select r.id as riesgoId, r.nombre as riesgoNombre,      " + 
					 " ir.nombre as impactoRiesgo, fr.nombre as frecuenciaRiesgo,      " + 
					 " p.id as procesoId, p.nombre  as procersoNombre,      " + 
					 " c.id as controlId, c.nombre  as controlNombre,      " + 
					 " pa.id as planAccionId, pa.nombre  as planAccionNombre,      " + 
					 " e.id as eventoId, e.nombre  as eventoNombre , " + 
					 " case when ((ir.porcentaje/100)*(fr.porcentaje/100)) < 0.25 then 'bajo' " + 
				  	 "	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.25 and ((ir.porcentaje/100)*(fr.porcentaje/100)) <0.70 then 'medio' " + 
					 "	 when ((ir.porcentaje/100)*(fr.porcentaje/100)) >= 0.70 then 'alto' " + 
					 " end as nivelRiesgo, " + 
					 " case when c.resultado_control < 25 then 'bajo' " + 
					 "	 when c.resultado_control >= 25 and c.resultado_control <70 then 'medio' " + 
					 "	 when c.resultado_control >= 70 then 'alto' " + 
					 " end as resultadoControl "+
					 " from transaccion.riesgo r       " + 
					 " inner join transaccion.riesgoproceso rp on r.id = rp.riesgo_id       " + 
					 " inner join transaccion.proceso p on rp.proceso_id = p.id       " + 
					 " inner join transaccion.riesgocontrol rc on r.id = rc.riesgo_id      " + 
					 " inner join transaccion.control c on rc.control_id = c.id      " + 
					 " inner join transaccion.plan_accion_riesgo par on r.id = par.riesgo_id      " + 
					 " inner join transaccion.plan_accion pa on par.planaccion_id = pa.id       " + 
					 " inner join transaccion.evento e on p.id = e.proceso_id      " + 
					 " inner join maestro.impacto_riesgo ir on r.impactoriesgo_id = ir.id       " + 
					 " inner join maestro.frecuencia_riesgo fr  on r.frecuenciariesgo_id  = fr.id      " + 
					 " inner join administrativo.usuario u on p.usuario_id = u.usuario_id  " + 
					 " where r.delete_id=2 and p.delete_id=2 and c.delete_id=2 and    " + 
					 " pa.delete_id=2 and e.delete_id=2  " + 
					 " and (0=? or u.usuario_id=? ) " + 
				  	 " and (0=? or p.id=?) " + 
					 " and (0=? or r.id=?) " + 
					"order by r.id ASC";
		List<ReporteDto> list;
		try {
			list = (List<ReporteDto>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReporteDto.class),
                    new Object[] {
                 		  obj.getUsuarioId(),
                 		  obj.getUsuarioId(),
                 		  obj.getProcesoId(),
                 		  obj.getProcesoId(),
                 		  obj.getRiesgoId(),
                 		  obj.getRiesgoId()
}					);
		} catch (EmptyResultDataAccessException e) {
			list = null;
			log.info(String.format("findAllControl %s", e.getMessage()));
			// e.printStackTrace();
		}
		return list;
	}

}
