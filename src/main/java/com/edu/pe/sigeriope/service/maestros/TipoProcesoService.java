package com.edu.pe.sigeriope.service.maestros;

import com.edu.pe.sigeriope.bean.maestros.TipoProceso;
import com.edu.pe.sigeriope.service.BaseServices;

public interface TipoProcesoService extends BaseServices<TipoProceso,Integer> {

    public int eliminar(Integer integer);
}
