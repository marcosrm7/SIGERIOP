
/**
 * 
 */
package com.edu.pe.sigeriope.repository;

import java.util.List;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.edu.pe.sigeriope.dto.reportes.CategoriaEventoReporteDto;
import com.edu.pe.sigeriope.dto.reportes.CausaConsecuenciasReportesDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteFilterDto;
import com.edu.pe.sigeriope.dto.reportes.ReportePdf;

/**
 * @author Ricardo Santos
 *
 */
public interface ReporteRepository {
	
	List<ReporteDto> findAll();
	ReportePdf findByPdf(ReporteFilterDto obj);
	List<CategoriaEventoReporteDto> categoriaEventoFindByEvento(int eventoId);
	List<ActividadesPlan> actividadesPlanFindByPlan(int planId);
	List<CausaConsecuenciasReportesDto> causaFindByRiesgo(int riesgoId);
	List<CausaConsecuenciasReportesDto> consecuenciaFindByRiesgo(int riesgoId);
	int countProcesos();
	int countEventos();
	int countRiesgos();
	int countControles();
	int countPlanes();
	List<ReporteDto> findAllFilter(ReporteFilterDto obj);
	
}
