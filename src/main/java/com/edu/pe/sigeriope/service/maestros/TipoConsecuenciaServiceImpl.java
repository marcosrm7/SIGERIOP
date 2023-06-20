/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoConsecuencia;
import com.edu.pe.sigeriope.repository.maestros.TipoConsecuenciaRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoConsecuenciaServiceImpl extends BaseSvc<TipoConsecuencia,Integer, TipoConsecuenciaRepoImpl>
implements TipoConsecuenciaService{

}
