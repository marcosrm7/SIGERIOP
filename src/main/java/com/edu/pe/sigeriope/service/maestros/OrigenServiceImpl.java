/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.Origen;
import com.edu.pe.sigeriope.repository.maestros.OrigenRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class OrigenServiceImpl extends BaseSvc<Origen,Integer, OrigenRepoImpl>
implements OrigenService{

}
