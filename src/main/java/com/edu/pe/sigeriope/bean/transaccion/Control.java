/**
 * 
 */
package com.edu.pe.sigeriope.bean.transaccion;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Control implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String descripcion;
	private int estadoDocumentacionId;
	private int estadoEfectividadId;
	private int estadoEventoId;
	private int estadoEvidencia2Id;
	private int estadoEvidenciaId;
	private int estadoResponsableId;
	private int frecuenciaEjecucionId;
	private BigDecimal resultadoControl;
	private BigDecimal resultadoDisenoControl;
	private BigDecimal resultadoEjecucionControl;
	private int tipoControlId;
	private int tipoEjecucionId;
	private int usuarioId;
	private List<Integer> riesgos;
	private String tipoControl;

}
