/**
 * 
 */
package com.edu.pe.sigeriope.bean.maestros;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public class Consecuencia extends Maestro implements Serializable {
	
	private static final long serialVersionUID = 1L;

}
