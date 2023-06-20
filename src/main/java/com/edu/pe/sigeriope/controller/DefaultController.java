package com.edu.pe.sigeriope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class DefaultController {
    @GetMapping("/")
    String index(HttpServletRequest request, HttpServletResponse response){
        //System.out.println("index");
        //return "home";
        HttpSession session = request.getSession(false);
        String view = (session == null) ? "redirect:/login": (session.getAttribute("user") == null) ? "redirect:/login" : "redirect:/home";
        return view;

    }
}
