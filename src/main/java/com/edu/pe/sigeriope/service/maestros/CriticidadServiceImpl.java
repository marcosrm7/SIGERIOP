/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.Criticidad;
import com.edu.pe.sigeriope.repository.maestros.CriticidadRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class CriticidadServiceImpl extends BaseSvc<Criticidad,Integer, CriticidadRepoImpl>
implements CriticidadService{

}
