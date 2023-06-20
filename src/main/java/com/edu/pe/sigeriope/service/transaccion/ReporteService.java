/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.List;

import com.edu.pe.sigeriope.dto.reportes.ReporteDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteFilterDto;
import com.edu.pe.sigeriope.dto.reportes.ReportePdf;

/**
 * @author Ricardo Santos
 *
 */
public interface ReporteService {

	List<ReporteDto> findAll();
	ReportePdf findByPdf(ReporteFilterDto obj);
	int countProcesos();
	int countEventos();
	int countRiesgos();
	int countControles();
	int countPlanes();
	List<ReporteDto> findAllFilter(ReporteFilterDto obj);
}
