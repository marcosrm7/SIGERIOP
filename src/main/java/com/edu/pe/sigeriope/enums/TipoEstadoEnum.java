/**
 * 
 */
package com.edu.pe.sigeriope.enums;

/**
 * @author Ricardo Santos
 *
 */
public enum TipoEstadoEnum {
	
	SISTEMA(1),
	EVENTOS(2),
	EFECTIVIDAD(3),
	EVIDENCIA(4),
	EVIDENCIA2(5),
	DOCUMENTACION(6),
	RESPONSABLES(7),
	PLAN_ACCION(8),
	ACTVIDADES_PLAN(9);
	
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	private TipoEstadoEnum(int id) {
		this.id = id;
	}

	
	
	
}
