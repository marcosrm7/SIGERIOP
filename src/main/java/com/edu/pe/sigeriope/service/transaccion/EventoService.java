package com.edu.pe.sigeriope.service.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.Evento;
import com.edu.pe.sigeriope.service.BaseServices;

public interface EventoService extends BaseServices<Evento,Integer> {
	Integer findByTipoEvento(Integer id);
}
