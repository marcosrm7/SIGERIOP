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
import com.edu.pe.sigeriope.bean.maestros.Causa;
import com.edu.pe.sigeriope.bean.maestros.Consecuencia;
import com.edu.pe.sigeriope.bean.maestros.FrecuenciaRiesgo;
import com.edu.pe.sigeriope.bean.maestros.ImpactoRiesgo;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccion;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.controller.procesos.ProcesosController;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.maestros.CausaService;
import com.edu.pe.sigeriope.service.maestros.ConsecuenciaService;
import com.edu.pe.sigeriope.service.maestros.FrecuenciaRiesgoService;
import com.edu.pe.sigeriope.service.maestros.ImpactoRiesgoService;
import com.edu.pe.sigeriope.service.transaccion.ProcesoService;
import com.edu.pe.sigeriope.service.transaccion.RiesgoService;

/**
 * @author Ricardo Santos
 *
 */
@Controller
@RequestMapping({"riesgos"})
public class RiesgoController {
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
    private ImpactoRiesgoService impactoRiesgoService;
    
    @Autowired
    private FrecuenciaRiesgoService frecuenciaRiesgoService;
    
    @Autowired
    private CausaService causaService;
    
    @Autowired
    private ConsecuenciaService consecuenciaService;
    
    @Autowired
    private ProcesoService procesoService;
    
    @Autowired
    private RiesgoService riesgoService;
	
	public static final Logger log = LoggerFactory.getLogger(ProcesosController.class);
	
    @SuppressWarnings("rawtypes")
	private HashMap route=new HashMap();
    @SuppressWarnings("rawtypes")
	private HashMap contentHeader=new HashMap<>();
	
    
    @SuppressWarnings("unchecked")
	public RiesgoController() {
    	 route.put(Integer.valueOf(1), "Riesgos");
         contentHeader.put("namemenu", "Riesgos");
         contentHeader.put("titlemenu","Panel de control");
         contentHeader.put("route", route);
	}
    
    
    @SuppressWarnings("unchecked")
	@GetMapping({""})
    public String wiewPanel(Model model) {
        route.put(Integer.valueOf(3), "Riesgos");
        contentHeader.put("opmnu","#lim_3:#lim_3");
        model.addAttribute("contentHeader",contentHeader);
        return "riesgos/riesgo";
    }
    
    @PostMapping("/paginate")
    @ResponseBody
    public ResponseEntity<?> getLisColegioJson(HttpServletRequest request, HttpServletResponse response) {
        List<Riesgo> list = riesgoService.findAll();
        return ResponseEntity.ok(Map.of("data", list));

    }
    
    @GetMapping(value = {"/view/add"})
    public String getView(Model model) {
    	List<ImpactoRiesgo> impactoRiesgoList = impactoRiesgoService.findAll();
        List<FrecuenciaRiesgo> frecuenciaRiesgoList = frecuenciaRiesgoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Causa>causaList=causaService.findAll();
        List<Consecuencia> consecuenciaList=consecuenciaService.findAll();
        List<Proceso> procesoList =procesoService.findAll();
        model.addAttribute("formRiesgo", "formSaveRiesgo");
        model.addAttribute("impactoRiesgoList",impactoRiesgoList);
        model.addAttribute("frecuenciaRiesgoList",frecuenciaRiesgoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("causaList",causaList);
        model.addAttribute("consecuenciaList",consecuenciaList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("nameBtn","Crear");
        return "riesgos/new-riesgo";
    }
    
    @PostMapping(value = {"/add"})
    @ResponseBody
    public ResponseEntity<?> saveColegio(@RequestBody Riesgo riesgo) {
        log.debug(String.format("saveRiesgo %s",riesgo.toString()));
        return ResponseEntity.ok(riesgoService.save(riesgo));
    }

    @GetMapping(value = {"/view/edit/{id}"})
    public String getViewEdit(Model model, @PathVariable("id")  int id) {
    	
    	List<ImpactoRiesgo> impactoRiesgoList = impactoRiesgoService.findAll();
        List<FrecuenciaRiesgo> frecuenciaRiesgoList = frecuenciaRiesgoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Causa>causaList=causaService.findAll();
        List<Consecuencia> consecuenciaList=consecuenciaService.findAll();
        List<Proceso> procesoList =procesoService.findAll();
        Riesgo riesgo=riesgoService.findBy(id);
        riesgo.setCausas(riesgoService.findByCausa(id));
        riesgo.setConsecuencias(riesgoService.findByConsecuencia(id));
        riesgo.setProcesos(riesgoService.findByProceso(id));
        log.debug("riesgo -> {}",riesgo);
        model.addAttribute("formRiesgo", "formEditRiesgo");
        model.addAttribute("impactoRiesgoList",impactoRiesgoList);
        model.addAttribute("frecuenciaRiesgoList",frecuenciaRiesgoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("causaList",causaList);
        model.addAttribute("consecuenciaList",consecuenciaList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("riesgo",riesgo);
        model.addAttribute("nameBtn","Actualizar");
        return "riesgos/new-riesgo";
    }
    
    @PutMapping(value = {"/edit"})
    @ResponseBody
    public ResponseEntity<?> editRiesgo(@RequestBody Riesgo riesgo) {
        log.debug("EditRiesgo {}",riesgo);
        return ResponseEntity.ok(riesgoService.edit(riesgo));
        
    }
    
    @GetMapping(value = {"/view/detail/{id}"})
    public String getViewDetail(Model model, @PathVariable("id")  int id) {
    	
    	List<ImpactoRiesgo> impactoRiesgoList = impactoRiesgoService.findAll();
        List<FrecuenciaRiesgo> frecuenciaRiesgoList = frecuenciaRiesgoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        List<Causa>causaList=causaService.findAll();
        List<Consecuencia> consecuenciaList=consecuenciaService.findAll();
        List<Proceso> procesoList =procesoService.findAll();
        Riesgo riesgo=riesgoService.findBy(id);
        riesgo.setCausas(riesgoService.findByCausa(id));
        riesgo.setConsecuencias(riesgoService.findByConsecuencia(id));
        riesgo.setProcesos(riesgoService.findByProceso(id));
        log.debug("riesgo -> {}",riesgo);
        model.addAttribute("formRiesgo", "formViewRiesgo");
        model.addAttribute("impactoRiesgoList",impactoRiesgoList);
        model.addAttribute("frecuenciaRiesgoList",frecuenciaRiesgoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("causaList",causaList);
        model.addAttribute("consecuenciaList",consecuenciaList);
        model.addAttribute("procesoList",procesoList);
        model.addAttribute("riesgo",riesgo);
        return "riesgos/new-riesgo";
    }
    
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id")  int id) {
        log.debug("id -> {}",id);
        return ResponseEntity.ok(riesgoService.deleteById(id));
    }
}
