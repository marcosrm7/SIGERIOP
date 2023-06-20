/**
 * 
 */
package com.edu.pe.sigeriope.dto.reportes;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Ricardo Santos
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ReporteDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private int riesgoId;
	private String riesgoNombre;
	private String impactoRiesgo;
	private String frecuenciaRiesgo;
	private int procesoId;
	private String procersoNombre;
	private int controlId;
	private String controlNombre;
	private int planAccionId;
	private String planAccionNombre;
	private int eventoId;
	private String eventoNombre;
	private String nivelRiesgo;
	private String resultadoControl;
	private Double resultadoControlPorcentaje;
	
	
	
}
