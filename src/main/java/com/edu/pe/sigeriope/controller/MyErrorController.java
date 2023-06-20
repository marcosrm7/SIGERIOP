package com.edu.pe.sigeriope.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyErrorController implements ErrorController {
	//implements  ErrorController
	public static final Logger log = LoggerFactory.getLogger(MyErrorController.class);

/*
	public String getErrorPath() {
		// System.out.println("getErrorPath");
		return "error";
	}
*/

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request,HttpServletResponse response) {


		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Exception exception=(Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		System.out.println("******************************* - getErrorPath "+request.getRequestURL());
		System.out.println("******************************* - getErrorPath "+request.getRequestURI());
		System.out.println("******************************* - getErrorPath "+status);

		// Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		//Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			}
			else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				//String mmensaje=exception==null? "N/A":"aaaaaa";
				//System.out.println("internallll--");
				System.out.println(status.toString()+"----"+ exception.toString());
				log.error("Error exception: ",exception);
				return "error-500";
			}
		}
		//System.out.println("error interno");
		response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return "error-500";
	}


	@RequestMapping("/error/403")
	public String error403(HttpServletResponse response) {

		response.setStatus( HttpServletResponse.SC_FORBIDDEN  );
		System.out.println("error:403");
		return "error-403";
	}

}
