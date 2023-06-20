/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import java.util.List;

import com.edu.pe.sigeriope.bean.maestros.Estado;
import com.edu.pe.sigeriope.enums.TipoEstadoEnum;
import com.edu.pe.sigeriope.service.BaseServices;

/**
 * @author Ricardo Santos
 *
 */
public interface EstadoService extends BaseServices<Estado,Integer> {
	
	List<Estado> findAllByTipoEstadoId(TipoEstadoEnum tipoEstadoEnum);

}
