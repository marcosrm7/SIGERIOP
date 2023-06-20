/**
 * 
 */
package com.edu.pe.sigeriope.service.maestros;

import org.springframework.stereotype.Service;

import com.edu.pe.sigeriope.bean.maestros.TipoCategoriaEvento;
import com.edu.pe.sigeriope.repository.maestros.TipoCategoriaEventoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;

import java.util.List;

/**
 * @author Ricardo Santos
 *
 */
@Service
public class TipoCategoriaEventoServiceImpl extends BaseSvc<TipoCategoriaEvento,Integer, TipoCategoriaEventoRepoImpl>
implements TipoCategoriaEventoService{

    @Override
    public List<TipoCategoriaEvento> findAllByTipoEventoId(int tipoEstadoEnum) {
        return repository.findAllByTipoEventoId(tipoEstadoEnum);
    }
}
