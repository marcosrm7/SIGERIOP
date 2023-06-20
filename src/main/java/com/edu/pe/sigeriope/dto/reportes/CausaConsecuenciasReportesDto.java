/**
 * 
 */
package com.edu.pe.sigeriope.dto.reportes;

import java.io.Serializable;

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
public class CausaConsecuenciasReportesDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
}
