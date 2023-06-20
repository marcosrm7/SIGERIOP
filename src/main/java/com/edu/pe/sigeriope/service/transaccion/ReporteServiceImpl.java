/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class ReporteServiceImpl implements ReporteService {

	@Autowired
	private ReporteRepository reporteRepository;

	public static final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

	@Override
	public List<ReporteDto> findAll() {
		log.info("ReporteServiceImpl.findAll");
		List<ReporteDto> list = reporteRepository.findAll();
//		List<ReporteDto> list2 = reporteRepository.findAll().stream()
//				.collect(Collectors.groupingBy(ReporteDto::getRiesgoId,
//						Collectors.maxBy(Comparator.comparingDouble(ReporteDto::getResultadoControlPorcentaje))))
//				.values().stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toList());
//		log.debug("Lista -> \n{} \n\n lista 2 ->\n {}", list, list2);
		return list;
	}

	@Override
	public ReportePdf findByPdf(ReporteFilterDto obj) {
		ReportePdf reportePdfObj = reporteRepository.findByPdf(obj);
		log.debug("reportePdfObj -> {}", reportePdfObj);
		if (reportePdfObj == null) {
			return reportePdfObj;
		}
		List<ActividadesPlan> actividadesPlanList = reporteRepository.actividadesPlanFindByPlan(obj.getPlanAccionId());
		List<CategoriaEventoReporteDto> categoriaEventoReporteDtoList = reporteRepository
				.categoriaEventoFindByEvento(obj.getEventoId());
		List<CausaConsecuenciasReportesDto> consecuenciaList = reporteRepository
				.consecuenciaFindByRiesgo(obj.getRiesgoId());
		List<CausaConsecuenciasReportesDto> causaList = reporteRepository.causaFindByRiesgo(obj.getRiesgoId());

		log.debug(
				"actividadesPlanList -> {} ; categoriaEventoReporteDtoList -> {} ;  consecuenciaList -> {} ;  causaList -> {}",
				actividadesPlanList, categoriaEventoReporteDtoList, consecuenciaList, causaList);

		if (actividadesPlanList != null && !actividadesPlanList.isEmpty()) {
			reportePdfObj.setActividadesPlanList(actividadesPlanList);
		}

		if (categoriaEventoReporteDtoList != null && !categoriaEventoReporteDtoList.isEmpty()) {
			reportePdfObj.setCategoriaEventoList(categoriaEventoReporteDtoList);
		}

		if (consecuenciaList != null && !consecuenciaList.isEmpty()) {
			reportePdfObj.setConsecuenciaList(consecuenciaList);
		}

		if (causaList != null && !causaList.isEmpty()) {
			reportePdfObj.setCausaList(causaList);
		}

		return reportePdfObj;
	}

	@Override
	public int countProcesos() {
		return reporteRepository.countProcesos();
	}

	@Override
	public int countEventos() {
		return reporteRepository.countEventos();
	}

	@Override
	public int countRiesgos() {
		return reporteRepository.countRiesgos();
	}

	@Override
	public int countControles() {
		return reporteRepository.countControles();
	}

	@Override
	public int countPlanes() {
		return reporteRepository.countPlanes();
	}

	@Override
	public List<ReporteDto> findAllFilter(ReporteFilterDto obj) {
		List<ReporteDto> list =reporteRepository.findAllFilter(obj);
//		List<ReporteDto> list2=reporteRepository.findAllFilter(obj).stream()
//				.collect(Collectors.groupingBy(ReporteDto::getRiesgoId,
//						Collectors.maxBy(Comparator.comparingDouble(ReporteDto::getResultadoControlPorcentaje))))
//				.values().stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toList());
//		log.debug("Lista -> \n{} \n\n lista 2 ->\n {}", list, list2);
		return list;
	}

}
