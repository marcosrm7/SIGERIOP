/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.ImpactoRiesgo;
import com.edu.pe.sigeriope.repository.maestros.ImpactoRiesgoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class ImpactoRiesgoServiceImpl extends BaseSvc<ImpactoRiesgo,Integer, ImpactoRiesgoRepoImpl>
implements ImpactoRiesgoService{

}
