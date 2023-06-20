package com.edu.pe.sigeriope.service.maestros;

import com.edu.pe.sigeriope.bean.maestros.CategoriaProceso;
import com.edu.pe.sigeriope.repository.maestros.CategoriaProcesoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;
import org.springframework.stereotype.Service;

@Service
public class CategoriaProcesoServiceImpl extends BaseSvc<CategoriaProceso,Integer, CategoriaProcesoRepoImpl>
        implements CategoriaProcesoService {

}
