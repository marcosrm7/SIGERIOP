/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.sigeriope.bean.transaccion.Control;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.repository.transaccion.ControlRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class ControlServiceImpl extends BaseSvc<Control,Integer, ControlRepoImpl> implements ControlService{

	public static final Logger log = LoggerFactory.getLogger(ControlServiceImpl.class);
	
	@Override
	public int save(Control control) {
		int resp=1;
		try {
			control.setId(repository.save(control));
			log.debug("RiesgoId -->{}",control.getId());
			
			if (control.getRiesgos() != null && control.getRiesgos().size() > 0) {
				repository.insertControlRiesgo(control);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			resp=3;
		}
		
		return resp;
	}

	@Override
	@Transactional
	public int edit(Control control) {
		int resp = 1;
		try {
			repository.deleteRiesgoById(control.getId());
			repository.edit(control);
			
			if (control.getRiesgos() != null && control.getRiesgos().size() > 0) {
				repository.insertControlRiesgo(control);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			resp = 3;
		}
		return resp;
	}
	
	@Override
	public List<Integer> findRiesgo(Integer controlId) {
		return repository.findRiesgo(controlId);
	}
}
