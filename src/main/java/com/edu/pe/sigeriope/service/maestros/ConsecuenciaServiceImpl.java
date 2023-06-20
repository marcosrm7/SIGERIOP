/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.Consecuencia;
import com.edu.pe.sigeriope.repository.maestros.ConsecuenciaRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class ConsecuenciaServiceImpl extends BaseSvc<Consecuencia,Integer, ConsecuenciaRepoImpl> implements ConsecuenciaService{

}
