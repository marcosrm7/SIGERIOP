package com.edu.pe.sigeriope.util;

import com.edu.pe.sigeriope.bean.menu.MenuProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursiveMenu {

	//private ServiceAccesos serviceAccesos = new ServiceAccesos();
	public int level = -1;
	public String _html = "";

	public ArrayList<MenuProfile> search(List<MenuProfile> menus, int father, int id_profile) {
	       ArrayList<MenuProfile> data=new ArrayList<MenuProfile>();
	       
	       //Collections.sort(data);
	       for (MenuProfile mp : menus) {
	          System.out.println("menus:::>"+mp.toString()+" father::"+father+" id_profile::"+id_profile);
	           if(mp.getId_profile()==id_profile && mp.getFather()==father){
	               data.add(mp);
	           }
	           
	       }
	       
	       Collections.sort(data);
	       /*for (MenuPerfil dp : data) {
	           System.out.println("parseada"+dp.toString()); 
	       }*/
	       
	       //
	        
	    return data;
	}

	public String show_menu(int father, String nivelAnterior, String contextPath, int id_profile, List<MenuProfile> menus) {

	    
	      ArrayList<MenuProfile> datamenus =this.search(menus, father, id_profile);  

	      System.out.println("sizeeee:::::::::"+datamenus.size());
	       for(int i=0;i<datamenus.size();i++){
	                   System.out.println(datamenus.get(i).getId_menu());
	               
	         if (this.level==-1) {

	            _html +="<li id=\"lim_"+datamenus.get(i).getId_menu()+"\" class=\"treeview\" >\n";

	        }else{
	            int  difference=datamenus.get(i).getLevel()-this.level;
	            if (difference==0)  _html+="</li>\n<li id=\"lim_"+datamenus.get(i).getId_menu()+"\" class=\"treeview\">\n";
	            if (difference==1) _html+="<ul class=\"treeview-menu\">\n<li id=\"lim_"+datamenus.get(i).getId_menu()+"\" >\n";
	            if (difference==-1) _html+="</li>\n</ul>\n<li id=\"lim_"+datamenus.get(i).getId_menu()+"\" >\n";

	            if(difference < (-1))
	            {
	                    _html += "</li>";
	                    for(int e=difference;e<0;e++)
	                    {
	                            _html += "</ul>\n</li>\n";
	                    }
	                    _html += "<li id=\"lim_"+datamenus.get(i).getId_menu()+"\" >\n";
	            }
	            
	         }
	         
	            //System.out.println(datamenus.get(i).getNombreMenu()+"   nivel::"+this.nivel+"  url::"+datamenus.get(i).getUrl());
	            String urldb;
	            urldb=("".equals(datamenus.get(i).getUrl()) || datamenus.get(i).getUrl() == null)? "javascript:void(0)" : contextPath+"/"+datamenus.get(i).getUrl();
	            
	            
	            String parent=(datamenus.get(i).getFather()==0 && datamenus.get(i).getUrl() == null)? "<i class=\"fa fa-angle-left pull-right\"></i>":"";
	            //System.out.println("nivel:"+this.nivel+"  --url:"+datamenus.get(i).getUrl());
	            
	            _html += "<a id=\"m_"+datamenus.get(i).getId_menu()+"\" href='"+urldb+"'><i class=\""+datamenus.get(i).getIcon()+"\"></i><span>"+datamenus.get(i).getMenu_name()+"</span>"+parent+"</a>";
	            this.level = datamenus.get(i).getLevel();
	            //this.muestra_menu(datamenus.get(i).getIdMenu(),String.valueOf(datamenus.get(i).getNivel()),contextPath);
	            this.show_menu(datamenus.get(i).getId_menu(),String.valueOf(datamenus.get(i).getLevel()),contextPath,id_profile,menus);
	               
	       }
	    
	       return _html;
	}

	public String showMenuFamily(String contextPath, int id_profile, List<MenuProfile> datamenu) {
		//ArrayList datamenus =da //this.serviceAccesos.get_menus_accesos_perfiles();
		System.out.println("muestra_menu_familias" + datamenu.size());
		String string = "";
		string = string + this.show_menu(0, "", contextPath, id_profile, datamenu);
		string = string + "</li>\n";
		return string;
	}
}
