/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoControl;
import com.edu.pe.sigeriope.repository.maestros.TipoControlRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoControlServiceImpl extends BaseSvc<TipoControl,Integer, TipoControlRepoImpl>
implements TipoControlService{

}
