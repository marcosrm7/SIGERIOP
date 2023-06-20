/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoCausa;
import com.edu.pe.sigeriope.repository.maestros.TipoCausaRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoCausaServiceImpl extends BaseSvc<TipoCausa,Integer, TipoCausaRepoImpl>
implements TipoCausaService{

}
