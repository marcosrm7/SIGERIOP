/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import com.edu.pe.sigeriope.bean.maestros.TipoCategoriaEvento;
import com.edu.pe.sigeriope.service.BaseServices;

import java.util.List;

/**
 * @author Ricardo Santos
 *
 */
public interface TipoCategoriaEventoService extends BaseServices<TipoCategoriaEvento,Integer>{

    List<TipoCategoriaEvento> findAllByTipoEventoId(int tipoEventoId);

}
