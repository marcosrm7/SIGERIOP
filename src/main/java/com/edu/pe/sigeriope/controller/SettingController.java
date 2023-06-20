/**
 * 
 */
package com.edu.pe.sigeriope.controller;


import com.edu.pe.sigeriope.bean.acceso.Password;
import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


/**
 * @author dasamo
 *
 */

@Controller
@RequestMapping("security")
public class SettingController {
	
	private HashMap route = new HashMap<>();
	private HashMap contentHeader = new HashMap<>();
	
	@Autowired
	SecurityService securityService;
	
	public SettingController() {
		// TODO Auto-generated constructor stub
		route.put(Integer.valueOf(1), "Ajustes de usuario");
		contentHeader.put("namemenu", "Editar perfil");
		
		//contentHeader.put("titlemenu","Nuevo");
		contentHeader.put("route", route);
	}

	@GetMapping("/setting/newPassword")
	public String wiewNewPassword(Model model) {
		route.put(Integer.valueOf(2), "Nueva Contraseña");
		//contentHeader.put("opmnu","#lim_1:#lim_3");

		model.addAttribute("contentHeader",contentHeader);
		return "acceso/new-password";
	}
/*
	@GetMapping({"/setting/view/password"})
	public String wiewNewPassword(Model model) {
		route.put(Integer.valueOf(2), "Nueva Contraseña");
		//contentHeader.put("opmnu","#lim_1:#lim_3");
		
		model.addAttribute("contentHeader",contentHeader);
		return "new-password";
	}
	*/
	
	@GetMapping({"/setting/profile"})
	public String wiewProfile(Model model,HttpServletRequest request) {
		
		route.put(Integer.valueOf(2), "Editar perfil");
		//contentHeader.put("opmnu","#lim_1:#lim_3");
		HttpSession session = request.getSession(false);
		
		User user=securityService.findUserById(((User) session.getAttribute("user")).getId_user());
		
		model.addAttribute("contentHeader",contentHeader);
		model.addAttribute("user", user);
		return "settings-profile";
	}
	
	@PutMapping(value = {"/setting/setNewPassword"})
	@ResponseBody
	public int setNewPassword(HttpServletRequest request, @RequestBody Password password) {
			
		System.out.println(password.toString());
		int rpta=0;
		
		User userMap=securityService.findUserById(password.getId_user());
		
		if(BCrypt.checkpw(password.getCurrentPassword(),userMap.getPassword())) {
			
				if(password.getNewPassword().trim().equals(password.getConfirNewPassword().trim())) {
					System.out.println("concide");
					rpta=securityService.updateNewPassword(password.getId_user(), BCrypt.hashpw(password.getNewPassword(), BCrypt.gensalt()),false);
					HttpSession session = request.getSession(false);
					if (session != null) {
						session.invalidate();
					}
				}else {
					rpta=2;
				}
			
		}else {
			rpta=3;//password incorrecto;
		}
		
		//securityService.editUserPasswordById(id, oldPassword, newPassword);
		return rpta;
	}
	
	@PutMapping(value = {"/setting/profile"})
	@ResponseBody
	public int editUser(@RequestBody User user) {
		user.setPassword("");
		System.out.println(user.getPassword()+" user::: "+user.toString());
		
		System.out.println("User update successs");
		return securityService.editUser(user);
	}
}
