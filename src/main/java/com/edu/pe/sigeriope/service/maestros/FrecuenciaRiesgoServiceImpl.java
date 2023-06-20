/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.FrecuenciaRiesgo;
import com.edu.pe.sigeriope.repository.maestros.FrecuenciaRiesgoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class FrecuenciaRiesgoServiceImpl extends BaseSvc<FrecuenciaRiesgo,Integer, FrecuenciaRiesgoRepoImpl>
implements FrecuenciaRiesgoService{

}
