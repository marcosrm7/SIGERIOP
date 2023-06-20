/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.Estado;
import com.edu.pe.sigeriope.enums.TipoEstadoEnum;
import com.edu.pe.sigeriope.repository.maestros.EstadoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class EstadoServiceImpl extends BaseSvc<Estado,Integer, EstadoRepoImpl>
implements EstadoService{
	
	public List<Estado> findAllByTipoEstadoId(TipoEstadoEnum tipoEstadoEnum){
		return repository.findAllByTipoEstadoId(tipoEstadoEnum.getId());
	}

}
