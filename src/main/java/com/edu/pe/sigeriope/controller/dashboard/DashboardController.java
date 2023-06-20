package com.edu.pe.sigeriope.controller.dashboard;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.pe.sigeriope.service.transaccion.ReporteService;

@Controller
@RequestMapping({"dashboard"})
public class DashboardController {
    public static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    private ReporteService reporteService;

    private HashMap route=new HashMap();
    private HashMap contentHeader=new HashMap<>();

    public DashboardController() {
        route.put(Integer.valueOf(1), "Dashboard");
        contentHeader.put("namemenu", "Dashboard");
        contentHeader.put("titlemenu","Panel de control");
        contentHeader.put("route", route);
    }

    @GetMapping({""})
    public String wiewPanelArea(Model model) {
        route.put(Integer.valueOf(2), "Dashboard");
        contentHeader.put("opmnu","#lim_1:#lim_1");
        model.addAttribute("contentHeader",contentHeader);
        model.addAttribute("evento",reporteService.countEventos());
        model.addAttribute("control",reporteService.countControles());
        model.addAttribute("riesgo",reporteService.countRiesgos());
        model.addAttribute("plan",reporteService.countPlanes());
        model.addAttribute("proceso",reporteService.countProcesos());
        return "dashboard/home";
    }
}
