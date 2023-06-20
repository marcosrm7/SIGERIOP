/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoEvento;
import com.edu.pe.sigeriope.repository.maestros.TipoEventoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoEventoServiceImpl extends BaseSvc<TipoEvento,Integer, TipoEventoRepoImpl>
implements TipoEventoService{

}
