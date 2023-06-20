/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.List;

import com.edu.pe.sigeriope.bean.transaccion.Causa;
import com.edu.pe.sigeriope.bean.transaccion.Consecuencia;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.service.BaseServices;

/**
 * @author Ricardo Santos
 *
 */
public interface RiesgoService extends BaseServices<Riesgo,Integer>{
	
	List<Consecuencia> findByConsecuencia(Integer id);
	List<Causa> findByCausa(Integer id);
	List<Integer> findByProceso(Integer id);
}
