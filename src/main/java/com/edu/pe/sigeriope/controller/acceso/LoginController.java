/**
 * 
 */
package com.edu.pe.sigeriope.controller.acceso;

import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.menu.MenuProfile;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.edu.pe.sigeriope.util.RecursiveMenu;
import eu.bitwalker.useragentutils.UserAgent;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Klinsmann
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	public static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	SecurityService securityService;
	
	@RequestMapping(method = RequestMethod.GET)
	String homePanel(HttpServletRequest request){
		//System.out.println("login");

		log.info("En SLF4J vienen definidos los siguientes niveles de log: ");
		log.error("ERROR -> Cuando ocurrió un error.");
		log.warn("WARN -> Circunstancia de posible error.");
		log.info("INFO -> Información de la ejecución.");
		log.debug("DEBUG -> Información importante a la hora de hacer debug.");
		log.trace("TRACE -> Información de traza sobre la ejecución.");

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

		boolean BrowAcept=(userAgent.getBrowser().toString().indexOf("IE") < 0 ) ? true : false;
		log.debug("Login : "+userAgent.toString()+" : "+BrowAcept);

		if(BrowAcept) {
			return "login";
		}else {
			return "error-browser";
		}
	}

	@RequestMapping(value="/sign_in", method = RequestMethod.POST)
//	@PostMapping(value="/sign_in")
	String sign_in(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttribute, User user){
		String password=user.getPassword();
		//String codepass=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		String view="";
		System.out.println("sign_in"+ user.toString());
		User userdata=securityService.findUserByLogin(user.getUsername());
		log.debug(user.toString());
		
//		log.error("ERROR -> Cuando ocurrió un error.");
		//System.out.println("ERROR -> Cuando ocurrió un error.");
		
		if(userdata !=null && (BCrypt.checkpw(password, userdata.getPassword())) ) {
			
			    userdata.setProfile(securityService.findProfileById(userdata.getId_profile()));
				System.out.println("userdata"+ userdata.toString());

				HttpSession session = request.getSession();
				//System.out.println(userdata.getUsername().toString());
				
				if(userdata.getProfile()!=null) {
					session.setMaxInactiveInterval(userdata.getProfile().getSession_time() * 60);
				}
				
				String listado = null;
				
				
				List<MenuProfile> menuPaths=securityService.findMenuProfile();
				List<MenuProfile> menuFilter=new ArrayList<>();
				
				for (MenuProfile mp : menuPaths) {
					  if(mp.getId_profile()==userdata.getId_profile()) {
						  menuFilter.add(mp);
					  }
				}

				System.out.println("menus --> "+menuPaths.size());
				
				if (userdata.getProfile().getState() == 2) {
					RecursiveMenu recursiveMenu = new RecursiveMenu();
					listado = recursiveMenu.showMenuFamily(request.getContextPath(), userdata.getId_profile(), menuPaths);
				}

				if(userdata.getLast_name()==null)
					userdata.setLast_name("");
				
				System.out.println("-------"+listado);
				session.setAttribute("foto", userdata.getPhoto()); 
				session.setAttribute("user", userdata);
				session.setAttribute("menus", listado);
				session.setAttribute("menuFilter", menuFilter);
				view="redirect:/home";
//				if(userdata.isReset_password()) {
//					session.setAttribute("menus", null);
//					view="redirect:/security/user/newPassword";
//				}else {
//					session.setAttribute("menus", listado);
//					view="redirect:/home";
//				}

		}else {
			redirectAttribute.addFlashAttribute("rpta","Clave o usuario inválido");
			log.info("Clave o usuario inválido");
			//Clave o usuario inválido
			view="redirect:/login";
		}
		return view;
	}

	@RequestMapping(value = "/sign_out", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
}
