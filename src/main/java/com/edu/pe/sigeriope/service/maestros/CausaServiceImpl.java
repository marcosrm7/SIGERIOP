/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.Causa;
import com.edu.pe.sigeriope.repository.maestros.CausaRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class CausaServiceImpl extends BaseSvc<Causa,Integer, CausaRepoImpl> implements CausaService{

}
