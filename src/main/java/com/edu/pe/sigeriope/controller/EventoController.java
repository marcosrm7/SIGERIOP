/**
 * 
 */
package com.edu.pe.sigeriope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.pe.sigeriope.bean.acceso.Responsable;
import com.edu.pe.sigeriope.bean.maestros.Criticidad;
import com.edu.pe.sigeriope.bean.maestros.TipoCategoriaEvento;
import com.edu.pe.sigeriope.bean.maestros.TipoEvento;
import com.edu.pe.sigeriope.bean.transaccion.Evento;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.controller.procesos.ProcesosController;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.maestros.CriticidadService;
import com.edu.pe.sigeriope.service.maestros.TipoCategoriaEventoService;
import com.edu.pe.sigeriope.service.maestros.TipoEventoService;
import com.edu.pe.sigeriope.service.transaccion.EventoService;
import com.edu.pe.sigeriope.service.transaccion.ProcesoService;

/**
 * @author Ricardo Santos
 *
 */
@Controller
@RequestMapping({"eventos"})
public class EventoController {
	
	public static final Logger log = LoggerFactory.getLogger(ProcesosController.class);
    @SuppressWarnings("rawtypes")
	private HashMap route=new HashMap();
    @SuppressWarnings("rawtypes")
	private HashMap contentHeader=new HashMap<>();

    @Autowired
    private EventoService eventoService;
    @Autowired
    private TipoEventoService tipoEventoService;
    @Autowired
    private TipoCategoriaEventoService tipoCategoriaEventoService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProcesoService procesoService;
    @Autowired
    private CriticidadService criticidadService;

    
    @SuppressWarnings("unchecked")
	public EventoController() {
    	 route.put(Integer.valueOf(5), "Eventos");
         contentHeader.put("namemenu", "Eventos");
         contentHeader.put("titlemenu","Panel de control");
         contentHeader.put("route", route);
	}
    
    
    @SuppressWarnings("unchecked")
	@GetMapping({""})
    public String wiewPanel(Model model) {
        route.put(Integer.valueOf(5), "Eventos");
        contentHeader.put("opmnu","#lim_5:#lim_5");
        model.addAttribute("contentHeader",contentHeader);
        return "eventos/evento";
    }

    @PostMapping("/paginate")
    @ResponseBody
    public ResponseEntity<?> getLisEventoJson(HttpServletRequest request, HttpServletResponse response) {
        List<Evento> listEventos = eventoService.findAll();
        return ResponseEntity.ok(Map.of("data", listEventos));

    }

    @GetMapping(value = {"/view/add"})
    public String getView(Model model) {
        List<TipoEvento> tipoEventoList = tipoEventoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Proceso> procesoList = procesoService.findAll();
        List<Criticidad> criticidadList = criticidadService.findAll();
        model.addAttribute("formEvento", "formSaveEvento");
        model.addAttribute("tiposevento",tipoEventoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("criticidadList",criticidadList);
        model.addAttribute("nameBtn","Crear");
        return "eventos/new-evento";
    }

    @GetMapping("tipos/{tipoeventoId}/categoria")
    @ResponseBody
    public ResponseEntity<?> getLisAnioelectivoByGradoId(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("tipoeventoId") int tipoeventoId
    ) {
        List<TipoCategoriaEvento> tipoCategoriaEventoList = tipoCategoriaEventoService.findAllByTipoEventoId(tipoeventoId);
        return ResponseEntity.ok(tipoCategoriaEventoList);
    }


    @PostMapping(value = {"/add"})
    @ResponseBody
    public ResponseEntity<?> saveColegio(@RequestBody Evento evento) {
        log.debug("saveEvento {}",evento);
        return ResponseEntity.ok(eventoService.save(evento));
    }
    
    @GetMapping(value = {"/view/edit/{id}"})
    public String getViewEdit(Model model, @PathVariable("id")  int id) {
        List<TipoEvento> tipoEventoList = tipoEventoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Proceso> procesoList = procesoService.findAll();
        List<Criticidad> criticidadList = criticidadService.findAll();
        List<TipoCategoriaEvento> categoriaseventoList = tipoCategoriaEventoService.findAll();
        Evento evento= eventoService.findBy(id);
        evento.setEventoTipoCategoriaId(eventoService.findByTipoEvento(evento.getTipoeventoId()));
        log.debug("evento -> {}",evento);
        model.addAttribute("formEvento", "formEditEvento");
        model.addAttribute("tiposevento",tipoEventoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("criticidadList",criticidadList);
        model.addAttribute("categoriaseventoList",categoriaseventoList);
        model.addAttribute("evento",evento);
        model.addAttribute("nameBtn","Actualizar");
        return "eventos/new-evento";
    }
    
    @PutMapping(value = {"/edit"})
    @ResponseBody
    public ResponseEntity<?> editEvento(@RequestBody Evento evento) {
        log.debug("EditEvento {}",evento);
        return ResponseEntity.ok(eventoService.edit(evento));
    }
    
    @GetMapping(value = {"/view/detail/{id}"})
    public String getViewDetail(Model model, @PathVariable("id")  int id) {
        List<TipoEvento> tipoEventoList = tipoEventoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Proceso> procesoList = procesoService.findAll();
        List<Criticidad> criticidadList = criticidadService.findAll();
        List<TipoCategoriaEvento> categoriaseventoList = tipoCategoriaEventoService.findAll();
        Evento evento= eventoService.findBy(id);
        evento.setEventoTipoCategoriaId(eventoService.findByTipoEvento(evento.getTipoeventoId()));
        log.debug("evento -> {}",evento);
        model.addAttribute("formEvento", "formViewEvento");
        model.addAttribute("tiposevento",tipoEventoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("criticidadList",criticidadList);
        model.addAttribute("categoriaseventoList",categoriaseventoList);
        model.addAttribute("evento",evento);
        return "eventos/new-evento";
    }
    
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id")  int id) {
        log.debug("id -> {}",id);
        return ResponseEntity.ok(eventoService.deleteById(id));
    }

}
