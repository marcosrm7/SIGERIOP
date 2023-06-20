/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoEjecucion;
import com.edu.pe.sigeriope.repository.maestros.TipoEjecucionRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoEjecucionServiceImpl extends BaseSvc<TipoEjecucion,Integer, TipoEjecucionRepoImpl>
implements TipoEjecucionService {

}
