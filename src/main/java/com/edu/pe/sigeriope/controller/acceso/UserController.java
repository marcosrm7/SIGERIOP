package com.edu.pe.sigeriope.controller.acceso;




import com.edu.pe.sigeriope.bean.acceso.Profile;
import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.datatables.DataTableParam;
import com.edu.pe.sigeriope.bean.datatables.DataTableResponse;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping({"security"})
public class UserController {
	
	private HashMap route=new HashMap();
	private HashMap contentHeader=new HashMap<>();
	
	@Autowired
	SecurityService securityService;

	public UserController() {
		// TODO Auto-generated constructor stub
		route.put(Integer.valueOf(1), "Seguridad");
		
		contentHeader.put("namemenu", "Usuarios");
		contentHeader.put("titlemenu","panel");
		contentHeader.put("route", route);
	}
	

	@RequestMapping({"/user"})
	public String wiewPaneluser(Model model) {
		
		route.put(Integer.valueOf(2), "Usuarios");
		contentHeader.put("opmnu","#lim_1:#lim_3");
		model.addAttribute("contentHeader",contentHeader);
		return "user";
	}
	
//	@RequestMapping({"/user/newPassword"})
//	public String wiewNewPassword(Model model) {
//		
//		route.put(Integer.valueOf(2), "Nueva Contraseña");
//		//contentHeader.put("opmnu","#lim_1:#lim_3");
//		
//		model.addAttribute("contentHeader",contentHeader);
//		return "new-password";
//	}
	

	//@RequestMapping(value = {"/user/getListUsersJson"}, method = {RequestMethod.POST})
	//@ResponseBody
	@GetMapping("/user/getListUsersJson")
	@ResponseBody
	public ResponseEntity<?> getListUsersJson(HttpServletRequest request, HttpServletResponse response) {
		List<User> userList = securityService.findUserProfileAll();
		Map<String, Object> data=new HashMap<>();
		data.put("data", userList);
		return ResponseEntity.ok(data);
		//return data;
	}

	@PostMapping("/user/lista/paginate")
	@ResponseBody
	public ResponseEntity<?> getListUsersJsonPaginate(HttpServletRequest request, HttpServletResponse response,
													  @RequestBody DataTableParam dataTableParam
	) {
		//String baseurl = request.getContextPath();
		DataTableResponse data = securityService.findUserProfileAllPaginate(dataTableParam);
		return ResponseEntity.ok(data);
	}

	@GetMapping(value = {"/user/view/add"})
	//@ResponseBody
	public String getViewUser(Model model) {
		//ArrayList listPerfil = this.serviceAccesos.get_list_perfiles();
		List<Profile> profileList=securityService.findProfileAll();
		model.addAttribute("formUser", "formSaveUser");
		model.addAttribute("profiles",profileList);
		
		return "fragments/new-user";
	}

	@RequestMapping(value = {"/user/saveUser"}, method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> saveUser(@RequestBody User user ) {
		int rpta=0;
			System.out.println(user.getPassword()+" user::: "+user.toString());
			User userMap=securityService.findUserByLogin(user.getUsername());
			if(userMap==null) {
				System.out.println("el usaurio no existe");
				rpta=securityService.saveUser(user);
			}else {
				rpta=2;
			}
			
			 //System.out.println("busqueda::"+userMap);
			 //rpta = securityService.saveUser(user);
			 //System.out.println("##############:"+rpta);


		return ResponseEntity.ok(rpta);
	}

	@GetMapping(value = {"/user/view/edit/{idUser}"})
	//@ResponseBody
	public String getViewEditUser(Model model,@PathVariable("idUser") int idUser, HttpServletRequest request) {
		List<Profile> profileList=securityService.findProfileAll();
		User user=securityService.findUserById(idUser);
		//InfoUserBean usuarioBean = this.serviceAccesos.get_usuario(idUsuario);
		//ModelAndView mav = new ModelAndView();
		//mav.setViewName("view_new_user");
		model.addAttribute("profiles", profileList);
		model.addAttribute("user", user);
		model.addAttribute("formUser", "formEditUser");
		
		return "fragments/new-user";
	}


	@PutMapping("/user/editUser")
	@ResponseBody
	public int editUser(@RequestBody User user) {
		if(user.getPassword().trim().length() > 0) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			user.setReset_password(true);
		}	
		System.out.println(user.getPassword()+" user::: "+user.toString());
		return securityService.editUser(user);
	}

	@DeleteMapping(value = {"/user/delete/{idUser}"})
	@ResponseBody
	public int deleteUser(@PathVariable("idUser") int idUser) {
		return securityService.deleteUser(idUser);
	}
	/*
	@RequestMapping(value = {"/user/changePassword"}, method = {RequestMethod.POST})
	@ResponseBody
	public int ActCambioPassword(@RequestParam("id") int id, @RequestParam("oldPassword") String oldPassword,
		
		@RequestParam("newPassword") String newPassword) {
		int rpta =securityService.editUserPasswordById(id, oldPassword, newPassword);
		return rpta;
	}
		*/
}