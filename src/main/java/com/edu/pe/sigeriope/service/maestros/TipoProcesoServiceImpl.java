package com.edu.pe.sigeriope.service.maestros;


import com.edu.pe.sigeriope.bean.maestros.TipoProceso;
import com.edu.pe.sigeriope.repository.maestros.TipoProcesoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;
import org.springframework.stereotype.Service;

@Service
public class TipoProcesoServiceImpl extends BaseSvc<TipoProceso,Integer, TipoProcesoRepoImpl> implements TipoProcesoService{


    @Override
    public int eliminar(Integer integer) {
        return repository.eliminar(integer);
    }
}
