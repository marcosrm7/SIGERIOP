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
import com.edu.pe.sigeriope.bean.maestros.Estado;
import com.edu.pe.sigeriope.bean.maestros.FrecuenciaEjecucion;
import com.edu.pe.sigeriope.bean.maestros.TipoControl;
import com.edu.pe.sigeriope.bean.maestros.TipoEjecucion;
import com.edu.pe.sigeriope.bean.transaccion.Control;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.controller.procesos.ProcesosController;
import com.edu.pe.sigeriope.enums.TipoEstadoEnum;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.maestros.EstadoService;
import com.edu.pe.sigeriope.service.maestros.FrecuenciaEjecucionService;
import com.edu.pe.sigeriope.service.maestros.TipoControlService;
import com.edu.pe.sigeriope.service.maestros.TipoEjecucionService;
import com.edu.pe.sigeriope.service.transaccion.ControlService;
import com.edu.pe.sigeriope.service.transaccion.RiesgoService;

/**
 * @author Ricardo Santos
 *
 */
@Controller
@RequestMapping({"controles"})
public class ControlesController {
	
	@Autowired
	private ControlService controlService;
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
	private TipoControlService tipoControlService;
	
	@Autowired
	private TipoEjecucionService tipoEjecucionService;
	
	@Autowired
	private FrecuenciaEjecucionService frecuenciaEjecucionService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
    private RiesgoService riesgoService;
	
	public static final Logger log = LoggerFactory.getLogger(ProcesosController.class);
    @SuppressWarnings("rawtypes")
	private HashMap route=new HashMap();
    @SuppressWarnings("rawtypes")
	private HashMap contentHeader=new HashMap<>();
	
    
    @SuppressWarnings("unchecked")
	public ControlesController() {
    	 route.put(Integer.valueOf(4), "Controles");
         contentHeader.put("namemenu", "Controles");
         contentHeader.put("titlemenu","Panel de control");
         contentHeader.put("route", route);
	}
    
    
    @SuppressWarnings("unchecked")
	@GetMapping({""})
    public String wiewPanel(Model model) {
        route.put(Integer.valueOf(4), "Controles");
        contentHeader.put("opmnu","#lim_4:#lim_4");
        model.addAttribute("contentHeader",contentHeader);
        return "controles/control";
    }
    
    @PostMapping("/paginate")
    @ResponseBody
    public ResponseEntity<?> getPaginate(HttpServletRequest request, HttpServletResponse response) {
        List<Control> list = controlService.findAll();
        return ResponseEntity.ok(Map.of("data", list));

    }
    
    @GetMapping(value = {"/view/add"})
    public String getView(Model model) {
        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList =riesgoService.findAll();
        List<TipoControl> tipoControlList=tipoControlService.findAll();
        List<TipoEjecucion> tipoEjecucionList=tipoEjecucionService.findAll();
        List<FrecuenciaEjecucion> frecuenciaEjecucionList= frecuenciaEjecucionService.findAll();
        List<Estado> estadoDocumentacionList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.DOCUMENTACION);
        List<Estado> estadoEvidenciaList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA);
        List<Estado> estadoResponsableList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.RESPONSABLES);
        List<Estado> estadoEventoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVENTOS);
        List<Estado> estadoEfectividadList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EFECTIVIDAD);
        List<Estado> estadoEvidencia2List= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA2);
        model.addAttribute("form", "formSaveControl");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("tipoControlList",tipoControlList);
        model.addAttribute("tipoEjecucionList",tipoEjecucionList);
        model.addAttribute("frecuenciaEjecucionList",frecuenciaEjecucionList);
        model.addAttribute("estadoDocumentacionList",estadoDocumentacionList);
        model.addAttribute("estadoEvidenciaList",estadoEvidenciaList);
        model.addAttribute("estadoResponsableList",estadoResponsableList);
        model.addAttribute("estadoEventoList",estadoEventoList);
        model.addAttribute("estadoEfectividadList",estadoEfectividadList);
        model.addAttribute("estadoEvidencia2List",estadoEvidencia2List);
        model.addAttribute("nameBtn","Crear");
        return "controles/new-control";
    }
    
    @PostMapping(value = {"/add"})
    @ResponseBody
    public ResponseEntity<?> saveControl(@RequestBody Control control) {
        log.debug(String.format("saveControl %s",control.toString()));
        return ResponseEntity.ok(controlService.save(control));
    }
    
    @GetMapping(value = {"/view/edit/{id}"})
    public String getViewEdit(Model model, @PathVariable("id")  int id) {
        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList =riesgoService.findAll();
        List<TipoControl> tipoControlList=tipoControlService.findAll();
        List<TipoEjecucion> tipoEjecucionList=tipoEjecucionService.findAll();
        List<FrecuenciaEjecucion> frecuenciaEjecucionList= frecuenciaEjecucionService.findAll();
        List<Estado> estadoDocumentacionList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.DOCUMENTACION);
        List<Estado> estadoEvidenciaList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA);
        List<Estado> estadoResponsableList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.RESPONSABLES);
        List<Estado> estadoEventoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVENTOS);
        List<Estado> estadoEfectividadList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EFECTIVIDAD);
        List<Estado> estadoEvidencia2List= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA2);
        Control control= controlService.findBy(id);
        control.setRiesgos(controlService.findRiesgo(id));
        log.debug("control -> {}",control);
        model.addAttribute("form", "formEditControl");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("tipoControlList",tipoControlList);
        model.addAttribute("tipoEjecucionList",tipoEjecucionList);
        model.addAttribute("frecuenciaEjecucionList",frecuenciaEjecucionList);
        model.addAttribute("estadoDocumentacionList",estadoDocumentacionList);
        model.addAttribute("estadoEvidenciaList",estadoEvidenciaList);
        model.addAttribute("estadoResponsableList",estadoResponsableList);
        model.addAttribute("estadoEventoList",estadoEventoList);
        model.addAttribute("estadoEfectividadList",estadoEfectividadList);
        model.addAttribute("estadoEvidencia2List",estadoEvidencia2List);
        model.addAttribute("control", control);
        model.addAttribute("nameBtn","Actualizar");
        return "controles/new-control";
    }
    
    @PutMapping(value = {"/edit"})
    @ResponseBody
    public ResponseEntity<?> editControl(@RequestBody Control control) {
        log.debug("editControl {}",control);
        return ResponseEntity.ok(controlService.edit(control));
    }
    
    @GetMapping(value = {"/view/detail/{id}"})
    public String getViewDetail(Model model, @PathVariable("id")  int id) {
        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList =riesgoService.findAll();
        List<TipoControl> tipoControlList=tipoControlService.findAll();
        List<TipoEjecucion> tipoEjecucionList=tipoEjecucionService.findAll();
        List<FrecuenciaEjecucion> frecuenciaEjecucionList= frecuenciaEjecucionService.findAll();
        List<Estado> estadoDocumentacionList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.DOCUMENTACION);
        List<Estado> estadoEvidenciaList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA);
        List<Estado> estadoResponsableList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.RESPONSABLES);
        List<Estado> estadoEventoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVENTOS);
        List<Estado> estadoEfectividadList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EFECTIVIDAD);
        List<Estado> estadoEvidencia2List= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.EVIDENCIA2);
        Control control= controlService.findBy(id);
        control.setRiesgos(controlService.findRiesgo(id));
        log.debug("control -> {}",control);
        model.addAttribute("form", "formViewControl");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("tipoControlList",tipoControlList);
        model.addAttribute("tipoEjecucionList",tipoEjecucionList);
        model.addAttribute("frecuenciaEjecucionList",frecuenciaEjecucionList);
        model.addAttribute("estadoDocumentacionList",estadoDocumentacionList);
        model.addAttribute("estadoEvidenciaList",estadoEvidenciaList);
        model.addAttribute("estadoResponsableList",estadoResponsableList);
        model.addAttribute("estadoEventoList",estadoEventoList);
        model.addAttribute("estadoEfectividadList",estadoEfectividadList);
        model.addAttribute("estadoEvidencia2List",estadoEvidencia2List);
        model.addAttribute("control", control);
        return "controles/new-control";
    }
    
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id")  int id) {
        log.debug("id -> {}",id);
        return ResponseEntity.ok(controlService.deleteById(id));
    }

}
