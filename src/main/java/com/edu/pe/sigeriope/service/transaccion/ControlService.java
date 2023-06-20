/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.List;

import com.edu.pe.sigeriope.bean.transaccion.Control;
import com.edu.pe.sigeriope.service.BaseServices;

/**
 * @author Ricardo Santos
 *
 */
public interface ControlService extends BaseServices<Control,Integer>{
	
	List<Integer> findRiesgo(Integer controlId);

}
