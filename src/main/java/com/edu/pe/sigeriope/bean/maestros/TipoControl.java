/**
 * 
 */
package com.edu.pe.sigeriope.bean.maestros;

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
public class TipoControl extends Maestro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String descripcion;
	private BigDecimal porcentaje;
	private BigDecimal peso;

}
