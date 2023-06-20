/**
 * 
 */
package com.edu.pe.sigeriope.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.edu.pe.sigeriope.bean.acceso.Responsable;
import com.edu.pe.sigeriope.bean.transaccion.Proceso;
import com.edu.pe.sigeriope.bean.transaccion.Riesgo;
import com.edu.pe.sigeriope.controller.procesos.ProcesosController;
import com.edu.pe.sigeriope.dto.reportes.ReporteDto;
import com.edu.pe.sigeriope.dto.reportes.ReporteFilterDto;
import com.edu.pe.sigeriope.dto.reportes.ReportePdf;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.service.transaccion.ProcesoService;
import com.edu.pe.sigeriope.service.transaccion.ReporteService;
import com.edu.pe.sigeriope.service.transaccion.RiesgoService;
import com.itextpdf.html2pdf.HtmlConverter;

/**
 * @author Ricardo Santos
 *
 */
@Controller
@RequestMapping({ "reportes" })
public class ReportesController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RiesgoService riesgoService;

	@Autowired
	private ProcesoService procesoService;

	@Autowired
	private ReporteService reporteService;

	public static final Logger log = LoggerFactory.getLogger(ProcesosController.class);
	@SuppressWarnings("rawtypes")
	private HashMap route = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap contentHeader = new HashMap<>();

	@SuppressWarnings("unchecked")
	public ReportesController() {
		route.put(Integer.valueOf(7), "Reportes");
		contentHeader.put("namemenu", "Reportes");
		contentHeader.put("titlemenu", "Panel de control");
		contentHeader.put("route", route);

	}

	@SuppressWarnings("unchecked")
	@GetMapping({ "" })
	public String wiewPanel(Model model) {
		route.put(Integer.valueOf(7), "Reportes");
		contentHeader.put("opmnu", "#lim_7:#lim_7");
		model.addAttribute("contentHeader", contentHeader);

		List<Responsable> responsableList = securityService.getResponsables();
		List<Riesgo> riesgoList = riesgoService.findAll();
		List<Proceso> procesoList = procesoService.findAll();
		model.addAttribute("responsableList", responsableList);
		model.addAttribute("riesgoList", riesgoList);
		model.addAttribute("procesoList", procesoList);
		return "reportes/reporte";
	}

	@PostMapping("/paginate")
	@ResponseBody
	public ResponseEntity<?> getPaginate(HttpServletRequest request, HttpServletResponse response) {
		List<ReporteDto> list = reporteService.findAll();
		return ResponseEntity.ok(Map.of("data", list));

	}

	@GetMapping("/pdf/{riesgoId}/{procesoId}/{controlId}/{planAccionId}/{eventoId}")
	public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("riesgoId") Integer riesgoId, 
			@PathVariable("procesoId") Integer procesoId,
			@PathVariable("controlId") Integer controlId, 
			@PathVariable("planAccionId") Integer planAccionId,
			@PathVariable("eventoId") Integer eventoId) throws IOException {

		log.info("riesgoId -> {} - procesoId -> {} - controlId -> {} - planAccionId -> {} - eventoId -> {}", riesgoId,
				procesoId, controlId, planAccionId, eventoId);
     
		/* Do Business Logic */

		ReporteFilterDto obj= ReporteFilterDto.builder()
				.riesgoId(riesgoId)
				.procesoId(procesoId)
				.controlId(controlId)
				.planAccionId(planAccionId)
				.eventoId(eventoId)
				.build();
		// Order order = OrderHelper.getOrder();
		ReportePdf reportePdf=reporteService.findByPdf(obj);
		/* Create HTML using Thymeleaf template Engine */
		log.debug("reportePdf -> {}",reportePdf);
		Context context = new Context();
		context.setVariable("obj", reportePdf);
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("reportes/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		String orderHtml = templateEngine.process("new-pdf", context);

		/* Setup Source and target I/O streams */
		ByteArrayOutputStream target = new ByteArrayOutputStream();

		/* Call convert method */
		HtmlConverter.convertToPdf(orderHtml, target);

		/* extract output as bytes */
		byte[] bytes = target.toByteArray();

		/* Send the response as downloadable PDF */
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);

	}
	
	@PostMapping(value = {"/search"})
    @ResponseBody
    public ResponseEntity<?> findAllFilter(@RequestBody ReporteFilterDto obj) {
        log.debug("findAllFilterReporte {}",obj);
        return ResponseEntity.ok(reporteService.findAllFilter(obj));
    }
}
