/**
 * 
 */
package com.edu.pe.sigeriope.controller;

import com.edu.pe.sigeriope.bean.acceso.Responsable;
import com.edu.pe.sigeriope.bean.maestros.Estado;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccion;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccionRiesgo;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.enums.TipoEstadoEnum;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.maestros.EstadoService;
import com.edu.pe.sigeriope.service.transaccion.PlanAccionService;
import com.edu.pe.sigeriope.service.transaccion.RiesgoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ricardo Santos
 *
 */
@Controller
@RequestMapping({"planes"})
public class PlanAccionController {
	
	public static final Logger log = LoggerFactory.getLogger(PlanAccionController.class);
    @SuppressWarnings("rawtypes")
	private HashMap route=new HashMap();
    @SuppressWarnings("rawtypes")
	private HashMap contentHeader=new HashMap<>();

    @Autowired
    SecurityService securityService;
    @Autowired
    RiesgoService riesgoService;
    @Autowired
    EstadoService estadoService;
    @Autowired
    PlanAccionService planAccionService;

    @SuppressWarnings("unchecked")
	public PlanAccionController() {
    	 route.put(Integer.valueOf(6), "Planes de Acción");
         contentHeader.put("namemenu", "Planes de Acción");
         contentHeader.put("titlemenu","Panel de control");
         contentHeader.put("route", route);
	}
    
    
    @SuppressWarnings("unchecked")
	@GetMapping({""})
    public String wiewPanel(Model model) {
        route.put(Integer.valueOf(6), "Planes de Acción");
        contentHeader.put("opmnu","#lim_6:#lim_6");
        model.addAttribute("contentHeader",contentHeader);
        return "planes/plan";
    }

    @PostMapping("/paginate")
    @ResponseBody
    public ResponseEntity<?> getLisPlanJson(HttpServletRequest request, HttpServletResponse response) {
        List<PlanAccion> listPlanes = planAccionService.findAll();
        return ResponseEntity.ok(Map.of("data", listPlanes));

    }


    @GetMapping(value = {"/view/add"})
    public String getViewPlan(Model model) {

        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList = riesgoService.findAll();
        List<Estado> estadoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.PLAN_ACCION);
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("formPlan", "formSavePlan");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("estadoList",estadoList);
        model.addAttribute("nameBtn","Crear");

        return "planes/new-plan";
    }

    @PostMapping(value = {"/add"})
    @ResponseBody
    public ResponseEntity<?> savePlan(@RequestBody PlanAccion planAccion) {
        log.debug(String.format("savePlanAccion %s",planAccion.toString()));
        return ResponseEntity.ok(planAccionService.save(planAccion));
    }

    @GetMapping(value = {"/view/edit/{id}"})
    public String getViewPlanEdit(Model model,
                                  @PathVariable("id")  int id
                                  ) {

        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList = riesgoService.findAll();
        List<Estado> estadoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.PLAN_ACCION);
        PlanAccion planAccion = planAccionService.findBy(id);

        planAccion.setRiesgos(planAccionService.getPlanAccionRiesgoByPlanaccionId(id)
                .stream().map(PlanAccionRiesgo::getRiesgoId).collect(Collectors.toList()));
        planAccion.setActividades(planAccionService.getActividadesPlanByPlanaccionId(id));
        log.debug(String.format("PlanAccion %s",planAccion.toString()));
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("formPlan", "formEditPlan");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("estadoList",estadoList);
        model.addAttribute("planAccion",planAccion);
        model.addAttribute("nameBtn","Actualizar");
        return "planes/new-plan";
    }

    @PutMapping(value = {"/edit"})
    @ResponseBody
    public ResponseEntity<?> editPlan(@RequestBody PlanAccion planAccion) {
        log.debug("EditPlanAccion {}",planAccion);
        return ResponseEntity.ok(planAccionService.edit(planAccion));
    }
    
    @GetMapping(value = {"/view/detail/{id}"})
    public String getViewDetail(Model model,
                                  @PathVariable("id")  int id
                                  ) {

        List<Responsable> responsableList = securityService.getResponsables();
        List<Riesgo> riesgoList = riesgoService.findAll();
        List<Estado> estadoList= estadoService.findAllByTipoEstadoId(TipoEstadoEnum.PLAN_ACCION);
        PlanAccion planAccion = planAccionService.findBy(id);

        planAccion.setRiesgos(planAccionService.getPlanAccionRiesgoByPlanaccionId(id)
                .stream().map(PlanAccionRiesgo::getRiesgoId).collect(Collectors.toList()));
        planAccion.setActividades(planAccionService.getActividadesPlanByPlanaccionId(id));
        log.debug("PlanAccion {}",planAccion);
        model.addAttribute("riesgoList",riesgoList);
        model.addAttribute("formPlan", "formViewPlan");
        model.addAttribute("responsables",responsableList);
        model.addAttribute("estadoList",estadoList);
        model.addAttribute("planAccion",planAccion);
        return "planes/new-plan";
    }
    
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id")  int id) {
        log.debug("id -> {}",id);
        return ResponseEntity.ok(planAccionService.deleteById(id));
    }
}
