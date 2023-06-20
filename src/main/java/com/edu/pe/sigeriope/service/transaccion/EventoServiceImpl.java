package com.edu.pe.sigeriope.service.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.Evento;
import com.edu.pe.sigeriope.repository.transaccion.EventoRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;
import org.springframework.stereotype.Service;

@Service
public class EventoServiceImpl extends BaseSvc<Evento,Integer, EventoRepoImpl> implements EventoService {

	@Override
	public Integer findByTipoEvento(Integer id) {
		return repository.findByTipoEvento(id);
	}
	
}
