/**
 * 
 */
package com.edu.pe.sigeriope.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * @author Klinsmann
 *
 */

@Controller
public class HomeController {
	public static final Logger log = LoggerFactory.getLogger(HomeController.class);
	@GetMapping("/home")
	String home(Model model) {
		log.debug("Index");

		HashMap contentHeader = new HashMap<>();
		contentHeader.put("namemenu", "Main Panel");
		contentHeader.put("titlemenu", "inicio");

		model.addAttribute("contentHeader", contentHeader);
		return "home";
	}
}
