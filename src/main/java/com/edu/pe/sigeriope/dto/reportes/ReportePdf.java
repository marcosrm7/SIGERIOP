/**
 * 
 */
package com.edu.pe.sigeriope.dto.reportes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.fasterxml.jackson.annotation.JsonFormat;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ricardo Santos
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReportePdf implements Serializable{ 

	private static final long serialVersionUID = 1L;
	
	private int eventoId;
	private String eventoNombre;
	private String eventoDescripcion;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime eventoFechaInicio;
	private int procesoId;
	private String procesoNombre;
	private String procesoDescripcion;
	private String procesoCategoria;
	private String procesoTipo;
	private String responsable;
	private String areaNombre;
	private String  montoPerdida;
	private String impactoRiesgo;
	private String impactoRiesgoPorcentaje;
	private String frecuenciaRiesgo;
	private String frecuenciaRiesgoPorcentaje;
	private String nivelRiesgo;
	private String nivelRiesgoPorcentaje;
	private String tipoEvento;
	private List<CausaConsecuenciasReportesDto> causaList;
	private List<CausaConsecuenciasReportesDto> consecuenciaList;
	private List<CategoriaEventoReporteDto> categoriaEventoList;
	private String planNombre;
	private String planDescripcion;
	private List<ActividadesPlan> actividadesPlanList;
	private String controlNombre;
	private String controlDescripcion;
	private String tipoControl;
	private String tipoEjecucion;
	private String resultaDisenoControl;
	private String resultadoEjecucionControl;
	private String resultadoControl;
	private String resultaDisenoControlPorcentaje;
	private String resultadoEjecucionControlPorcentaje;
	private String resultadoControlPorcentaje;
	private String riesgoNombre;
	private String riesgoDescripcion;
	
}

