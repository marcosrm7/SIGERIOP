/**
 * 
 */
package com.edu.pe.sigeriope.bean.maestros;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Ricardo Santos
 *
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@SuperBuilder
public class TipoProceso extends Maestro implements Serializable {
	
	private static final long serialVersionUID = 1L;


}
