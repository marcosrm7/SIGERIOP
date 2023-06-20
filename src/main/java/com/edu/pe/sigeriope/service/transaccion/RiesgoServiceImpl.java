/**
 * 
 */
package com.edu.pe.sigeriope.service.transaccion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.pe.sigeriope.bean.transaccion.Causa;
import com.edu.pe.sigeriope.bean.transaccion.Consecuencia;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.repository.transaccion.RiesgoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class RiesgoServiceImpl extends BaseSvc<Riesgo,Integer, RiesgoRepoImpl> implements RiesgoService{

	public static final Logger log = LoggerFactory.getLogger(RiesgoServiceImpl.class);
	
	@Override
	public int save(Riesgo riesgo) {
		int resp=1;
		try {
			riesgo.setId(repository.save(riesgo));
			log.debug("RiesgoId -->{}",riesgo.getId());
			if(riesgo.getProcesos()!=null && riesgo.getProcesos().size()>0) {
				repository.insertRiesgoProceso(riesgo);
			}
			if(riesgo.getCausas()!=null && riesgo.getCausas().size()>0) {
				repository.insertRiesgoCausa(riesgo);
			}
			if(riesgo.getConsecuencias()!=null && riesgo.getConsecuencias().size()>0) {
				repository.insertRiesgoConsecuencias(riesgo);
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
	public int edit(Riesgo riesgo) {
		int resp = 1;
		try {
			repository.deleteCausaById(riesgo.getId());
			repository.deleteConsecuenciaById(riesgo.getId());
			repository.deleteProcesoById(riesgo.getId());
			repository.edit(riesgo);
			if(riesgo.getProcesos()!=null && riesgo.getProcesos().size()>0) {
				repository.insertRiesgoProceso(riesgo);
			}
			if (riesgo.getCausas() != null && riesgo.getCausas().size() > 0) {
				repository.insertRiesgoCausa(riesgo);
			}
			if (riesgo.getConsecuencias() != null && riesgo.getConsecuencias().size() > 0) {
				repository.insertRiesgoConsecuencias(riesgo);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			resp = 3;
		}
		return resp;
	}

	@Override
	public List<Consecuencia> findByConsecuencia(Integer id) {
		return repository.findByConsecuencia(id);
	}

	@Override
	public List<Causa> findByCausa(Integer id) {
		return repository.findByCausa(id);
	}
	
	@Override
	public List<Integer> findByProceso(Integer id) {
		return repository.findByProceso(id);
	}
}
