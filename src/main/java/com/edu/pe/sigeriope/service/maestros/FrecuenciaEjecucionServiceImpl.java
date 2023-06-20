/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.FrecuenciaEjecucion;
import com.edu.pe.sigeriope.repository.maestros.FrecuenciaEjecucionRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class FrecuenciaEjecucionServiceImpl extends BaseSvc<FrecuenciaEjecucion,Integer, FrecuenciaEjecucionRepoImpl>
implements FrecuenciaEjecucionService{

}
