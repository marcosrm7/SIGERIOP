/**
 * 
 */
package com.edu.pe.sigeriope.bean.maestros;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Ricardo Santos
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Maestro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;

}
