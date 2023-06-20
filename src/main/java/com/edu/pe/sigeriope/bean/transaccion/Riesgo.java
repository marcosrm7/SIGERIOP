/**
 * 
 */
package com.edu.pe.sigeriope.bean.transaccion;

import java.io.Serializable;
import java.util.List;

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
public class Riesgo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String descripcion;
	private String afectaNegocio;
	private int usuarioId;
	private String responsable;
	private int impactoRiesgoId;
	private int frecuenciaRiesgoId;
	private String frecuenciaEjecucionNombre;
	private String frecuenciaEjecucionPorcentaje;
	private String impactoRiesgoNombre;
	private String impactoRiesgoPorcentaje;
	private List<Integer> procesos;
	private List<Consecuencia> consecuencias;
	private List<Causa> causas;

}
