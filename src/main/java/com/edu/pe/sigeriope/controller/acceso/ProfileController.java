package com.edu.pe.sigeriope.controller.acceso;

import com.edu.pe.sigeriope.bean.acceso.Profile;
import com.edu.pe.sigeriope.bean.menu.MenuProfile;
import com.edu.pe.sigeriope.bean.menu.TreeviewData;
import com.edu.pe.sigeriope.bean.menu.TreeviewNodeState;
import com.edu.pe.sigeriope.service.acceso.SecurityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"security"})
public class ProfileController {

    public static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private List<MenuProfile> listMenu;
    private HashMap route = new HashMap();
    private HashMap contentHeader = new HashMap<>();

    @Autowired
    SecurityService securityService;

    public ProfileController() {
        // TODO Auto-generated constructor stub
        route.put(Integer.valueOf(1), "Seguridad");

        contentHeader.put("namemenu", "Tipo de Usuario");
        contentHeader.put("titlemenu", "panel");
        contentHeader.put("route", route);
    }

    @RequestMapping({"/profile"})
    public String wiewPanelProfile(Model model) {

        //HashMap route = new HashMap();
        route.put(Integer.valueOf(2), "Tipo de Usuario");
        contentHeader.put("opmnu", "#lim_1:#lim_2");

        model.addAttribute("contentHeader", contentHeader);
        return "profile";
    }

    @PostMapping("/profile/paginate")
    @ResponseBody
    public ResponseEntity<?> getListProfileJson(HttpServletRequest request, HttpServletResponse response) {
//		DataTableObject dataTableObject = new DataTableObject();
//		ArrayList<Profile> profileList = securityService.findProfileAll();
//		dataTableObject.setAaData(profileList);
//		dataTableObject.setiTotalDisplayRecords(profileList.size());
//		dataTableObject.setiTotalRecords(profileList.size());
//		Gson gson = new GsonBuilder()
//				.setPrettyPrinting()
//				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//				.create();
//		return gson.toJson(dataTableObject);
        List<Profile> listProfile = securityService.findProfileAll();

        Map<String, Object> data = new HashMap<>();
        data.put("data", listProfile);
        return ResponseEntity.ok(data);

    }

    @GetMapping(value = {"profile/view/add"})
    //@ResponseBody
    public String getViewProfile(Model model) {
        //ModelAndView mav = new ModelAndView();
        //mav.setViewName("view_new_profile");
        model.addAttribute("formProfile", "formSaveProfile");

        return "fragments/new-profile";
    }

    @RequestMapping(value = {"/profile/saveProfile"}, method = {RequestMethod.POST})
    @ResponseBody
    public int ActRegistraPerfil(@RequestBody Profile profile) {
        int rs = 0;
        profile.setRegistration_date(LocalDateTime.now());
        //profile.setState(0);
        profile.setSession_time(1200);
        System.out.println(profile.toString());
        log.debug(profile.toString());
        if (securityService.findProfileByName(profile.getName_profile()) == null) {
            rs = securityService.saveProfile(profile);
        } else {
            rs = 0;
        }

        return rs;
    }

    @GetMapping(value = {"/profile/view/edit/{idProfile}"})
    //@ResponseBody
    public String getViewEditProfile(
            Model model, @PathVariable("idProfile") int idProfile, HttpServletRequest request
    ) {
        ModelAndView mav = new ModelAndView();
        Profile p = securityService.findProfileById(idProfile);
        System.out.println(p);
        //mav.setViewName("view_new_profile");
        model.addAttribute("formProfile", "formEditProfile");
        model.addAttribute("profile", p);

        return "fragments/new-profile";
    }

    @RequestMapping(value = {"/profile/editProfile"}, method = {RequestMethod.POST})
    @ResponseBody
    public int editProfile(@RequestBody Profile profile) {
        System.out.println("editProfile:" + profile.toString());
        return securityService.editProfile(profile);
    }

    @RequestMapping(value = {"/profile/{id_profile}/permits"}, method = {RequestMethod.POST})
    @ResponseBody
    public int assignPermits(
            @PathVariable("id_profile") int id_profile,
            @RequestParam("id_menu") int id_menu,
            @RequestParam("option") boolean option
    ) {

        if (option) {
            return securityService.assignPermits(id_profile, id_menu);
        } else {
            return securityService.removePermits(id_profile, id_menu);
        }

    }
/*
	@GetMapping(value = {"/profile/{idProfile}/view/permitxx"})
	@ResponseBody
	public ResponseEntity<?> viewPermitxx(@PathVariable("idProfile") int idProfile, HttpServletRequest request) {
		Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
		listMenu = securityService.findMenuProfileByIdProfile(idProfile); //this.serviceAccesos.get_menus_accesos_perfil(idPerfil);
		List<Menu> listMenus = new ArrayList<Menu>();
		
		System.out.println(listMenu.size());
		
		 for (MenuProfile m : listMenu) {
			 
	           if(m.getLevel()==0){
	             Menu menu=null;
	             System.out.println("objeto:"+m.toString());
	            if(m.getNroh()>0){
	                System.out.println("entra if"+m.getNroh());
	                menu=new MenuCompound();
	                menu.setId(m.getId_menu());
	                menu.setHref("#");
	                menu.setName(m.getMenu_name());
	                menu.setParentId(""+m.getLevel());
	                menu.setText(m.getMenu_name());
	                addMenu(menu);
	                
	            }else{
	                menu=new MenuSimple();
	                menu.setId(m.getId_menu());
	                menu.setHref("#");
	                menu.setName(m.getMenu_name());
	                menu.setParentId(""+m.getLevel());
	                menu.setText(m.getMenu_name());    
	            }
	               System.out.println(":::::::::::::::"+m.toString());
	            boolean cheked=(m.getId_profile()==0)? false : true;
	            menu.setState(new MenuState(cheked, false, true, false));
	            
	            listMenus.add(menu);
	           }
	        }
		
		
		 //System.out.println(listMenus.toString());
		//return listMenus;
		return ResponseEntity.ok(gson.toJson(listMenus));
		//return listMenus;
	}*/

    @GetMapping(value = {"/profile/{idProfile}/view/permit"})
    @ResponseBody
    public ResponseEntity<?> viewPermit(@PathVariable("idProfile") int idProfile, HttpServletRequest request) {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        listMenu = securityService.findMenuProfileByIdProfile(
                idProfile);

        ArrayList<TreeviewData> listaMenus = new ArrayList<TreeviewData>();

        for (MenuProfile m : listMenu) {
            TreeviewData menu = null;
            if (m.getLevel() == 0) {
                if (m.getNroh() > 0) {
                    menu = new TreeviewData();
                    menu.setId(Long.valueOf(m.getId_menu()));
                    menu.setHref("#");
                    menu.setName(m.getMenu_name());
                    menu.setParent_id("" + m.getLevel());
                    menu.setText(m.getMenu_name());
                    menu.setIcon(m.getIcon());
                    llenarMenu(menu, idProfile);
                } else {
                    menu = new TreeviewData();
                    menu.setId(Long.valueOf(m.getId_menu()));
                    menu.setHref("#");
                    menu.setName(m.getMenu_name());
                    menu.setParent_id("" + m.getLevel());
                    menu.setText(m.getMenu_name());
                    menu.setIcon(m.getIcon());
                    menu.setNodes(null);
                }
                boolean cheked = (m.getId_profile() == 0) ? false : true;
                menu.setState(new TreeviewNodeState(cheked, false, true, false));
                listaMenus.add(menu);
            }

        }
        //log.debug("------------------------------------------");
        //log.debug(gson.toJson(listaMenus));

        return ResponseEntity.ok(listaMenus);//gson.toJson(listaMenus);
    }

    private void llenarMenu(TreeviewData menuPadre, int id_profile) {


        List<MenuProfile> llmenu = findHijos(menuPadre.getId());

        log.debug("llenarMenu" + llmenu.size());

        TreeviewData menu;
        for (MenuProfile m : llmenu) {

            log.debug("boolean  ::: " + m.getNroh() + "---" + m.toString());
            if (m.getNroh() > 0) {
                menu = new TreeviewData();


                //TreeviewDatanodeButton btn2=new TreeviewDatanodeButton();
                //listButton.add(btn2);

                menu.setId(Long.valueOf(m.getId_menu()));
                menu.setHref("#");
                menu.setName(m.getMenu_name());
                menu.setParent_id("" + m.getLevel());
                menu.setText(m.getMenu_name());
                menuPadre.agregarMenuHijo(menu);
                menu.setIcon(m.getIcon());
                llenarMenu(menu, id_profile);
                //System.out.println("cantidad de hijos :::: " +  menu.getMenu().size());
            } else {

                menu = new TreeviewData();
                menu.setId(Long.valueOf(m.getId_menu()));
                menu.setHref("#");
                menu.setName(m.getMenu_name());
                menu.setParent_id("" + m.getLevel());
                menu.setText(m.getMenu_name());
                menuPadre.agregarMenuHijo(menu);
                menu.setIcon(m.getIcon());
                menu.setNodes(null);
                //System.out.println("padreeee:::"+menuPadre.getMenu().size());
            }
            boolean cheked = (m.getId_profile() == 0) ? false : true;
            // menu.setState(new MenuEstado(cheked,false,true,false));
            menu.setState(new TreeviewNodeState(cheked, false, true, false));
            //listaMenus.add(menu);
        }

    }

	private List<MenuProfile> findHijos(long idpadre) {

		List<MenuProfile> mp = new ArrayList<MenuProfile>();

		for (MenuProfile lm : listMenu) {

			if (lm.getFather() == idpadre) {
				log.debug("llenar hijos" + lm.toString());
				mp.add(lm);
			}
		}

		return mp;
	}
	/*
	private List<MenuProfile> findChildren(int idfather) {
		 ArrayList<MenuProfile> mp=new ArrayList<MenuProfile>();
	       for (MenuProfile lm : listMenu) {
	           
	           if(lm.getFather()==idfather){
	               //System.out.println("llenar hijos"+lm.toString());
	               mp.add(lm);
	           }
	       }
	       
	       return mp;
	}*/
/*
	private void addMenu(Menu menuFather) {
		 List<MenuProfile> llmenu =findChildren(menuFather.getId());
         
	        System.out.println("llenarMenu"+llmenu.size());
	         
	          Menu menu;
	         for (MenuProfile m : llmenu) {
	            
	            System.out.println("boolean  ::: "+m.getNroh()+"---"+ (m.getNroh()>0));
	            if(m.getNroh()>0){
	                menu=new MenuCompound();
	                menu.setId(m.getId_menu());
	                menu.setHref("#");
	                menu.setName(m.getMenu_name());
	                menu.setParentId(""+m.getLevel());
	                menu.setText(m.getMenu_name());
	                menuFather.addMenuChildren(menu);
	                addMenu(menu);
	                //System.out.println("cantidad de hijos :::: " +  menu.getMenu().size());
	            }else{
	                
	                menu=new MenuSimple();
	                menu.setId(m.getId_menu());
	                menu.setHref("#");
	                menu.setName(m.getMenu_name());
	                menu.setParentId(""+m.getLevel());
	                menu.setText(m.getMenu_name());
	                menuFather.addMenuChildren(menu);
	                //System.out.println("padreeee:::"+menuPadre.getMenu().size());
	            }
	            boolean cheked=(m.getId_profile()==0)? false : true;
	           // menu.setState(new MenuEstado(cheked,false,true,false));
	            menu.setState(new MenuState(cheked,false,true,false));
	         //listaMenus.add(menu);
	        }
	}*/

}