package com.edu.pe.sigeriope.service.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.repository.transaccion.ProcesoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;
import org.springframework.stereotype.Service;

@Service
public class ProcesoServiceImpl extends BaseSvc<Proceso,Integer, ProcesoRepoImpl> implements ProcesoService {
}
