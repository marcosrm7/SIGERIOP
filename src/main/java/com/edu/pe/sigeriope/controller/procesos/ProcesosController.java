package com.edu.pe.sigeriope.controller.procesos;

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
import com.edu.pe.sigeriope.bean.maestros.CategoriaProceso;
import com.edu.pe.sigeriope.bean.maestros.TipoProceso;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.maestros.CategoriaProcesoService;
import com.edu.pe.sigeriope.service.maestros.TipoProcesoService;
import com.edu.pe.sigeriope.service.transaccion.ProcesoService;

@Controller
@RequestMapping({"procesos"})
public class ProcesosController {
    public static final Logger log = LoggerFactory.getLogger(ProcesosController.class);
    @SuppressWarnings("rawtypes")
	private HashMap route=new HashMap();
    @SuppressWarnings("rawtypes")
	private HashMap contentHeader=new HashMap<>();

    @Autowired
    TipoProcesoService tipoProcesoService;

    @Autowired
    CategoriaProcesoService categoriaProcesoService;

    @Autowired
    SecurityService securityService;

    @Autowired
    ProcesoService procesoService;

    @SuppressWarnings("unchecked")
    public ProcesosController() {
        route.put(Integer.valueOf(1), "Procesos");
        contentHeader.put("namemenu", "Procesos");
        contentHeader.put("titlemenu","Panel de control");
        contentHeader.put("route", route);
    }
    
    @SuppressWarnings("unchecked")
    @GetMapping({""})
    public String wiewPanelProcesos(Model model) {
        route.put(Integer.valueOf(2), "Procesos");
        contentHeader.put("opmnu","#lim_2:#lim_2");
        model.addAttribute("contentHeader",contentHeader);
        return "procesos/home";
    }

    @PostMapping("/paginate")
    @ResponseBody
    public ResponseEntity<?> getLisProcesosJson(HttpServletRequest request, HttpServletResponse response) {
        List<Proceso> listProcesos = procesoService.findAll();
        return ResponseEntity.ok(Map.of("data", listProcesos));

    }

    @GetMapping(value = {"/view/add"})
    public String getViewProceso(Model model) {
        List<TipoProceso> tipoProcesoList = tipoProcesoService.findAll();
        List<CategoriaProceso> categoriaProcesoList = categoriaProcesoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        log.debug(String.format("getViewProceso: %s",tipoProcesoList.toString()));
        model.addAttribute("formProceso", "formSaveProceso");
        model.addAttribute("tiposProceso",tipoProcesoList);
        model.addAttribute("categoriasProceso",categoriaProcesoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("nameBtn","Crear");
        return "procesos/new-proceso";
    }

    @PostMapping(value = {"/add"})
    @ResponseBody
    public ResponseEntity<?> saveProceso(@RequestBody Proceso proceso) {
        log.debug(String.format("saveProceso %s",proceso.toString()));
        return ResponseEntity.ok(procesoService.save(proceso));
    }
    
    @GetMapping(value = {"/view/edit/{id}"})
    public String getViewEditProceso(Model model,@PathVariable("id")  int id) {
        List<TipoProceso> tipoProcesoList = tipoProcesoService.findAll();
        List<CategoriaProceso> categoriaProcesoList = categoriaProcesoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        Proceso proceso= procesoService.findBy(id);
        log.debug("getViewEditProceso: {}",proceso);
        model.addAttribute("formProceso", "formEditProceso");
        model.addAttribute("tiposProceso",tipoProcesoList);
        model.addAttribute("categoriasProceso",categoriaProcesoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("proceso",proceso);
        model.addAttribute("nameBtn","Actualizar");
        return "procesos/new-proceso";
    }
    
    
    @PutMapping(value = {"/edit"})
    @ResponseBody
    public ResponseEntity<?> editProceso(@RequestBody Proceso proceso) {
        log.debug("editProceso {}",proceso);
        return ResponseEntity.ok(procesoService.edit(proceso));
    }
    
    
    @GetMapping(value = {"/view/detail/{id}"})
    public String getViewDeatilProceso(Model model,@PathVariable("id")  int id) {
        List<TipoProceso> tipoProcesoList = tipoProcesoService.findAll();
        List<CategoriaProceso> categoriaProcesoList = categoriaProcesoService.findAll();
        List<Responsable> responsableList = securityService.getResponsables();
        Proceso proceso= procesoService.findBy(id);
        log.debug("getViewEditProceso: {}",proceso);
        model.addAttribute("formProceso", "formViewProceso");
        model.addAttribute("tiposProceso",tipoProcesoList);
        model.addAttribute("categoriasProceso",categoriaProcesoList);
        model.addAttribute("responsables",responsableList);
        model.addAttribute("proceso",proceso);
        return "procesos/new-proceso";
    }
    
    
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id")  int id) {
        log.debug("id -> {}",id);
        return ResponseEntity.ok(procesoService.deleteById(id));
    }
    
}