package com.edu.pe.sigeriope.bean.menu;
/**
 * @author dasamo
 *
 */

public class MenuProfile implements Comparable<MenuProfile> {

	public MenuProfile() {
		// TODO Auto-generated constructor stub
	}

	private int id_profile;
	private int id_menu;
	private int father;
	private int level;
	private String menu_name;
	private String url;
	private Integer state;
	private int nroh;
	private String icon;
	private int order;

	public int getId_profile() {
		return id_profile;
	}

	public void setId_profile(int id_profile) {
		this.id_profile = id_profile;
	}

	public int getId_menu() {
		return id_menu;
	}

	public void setId_menu(int id_menu) {
		this.id_menu = id_menu;
	}

	public int getFather() {
		return father;
	}

	public void setFather(int father) {
		this.father = father;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public int getNroh() {
		return nroh;
	}

	public void setNroh(int nroh) {
		this.nroh = nroh;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int compareTo(MenuProfile o) {
		// throw new UnsupportedOperationException("Not supported yet."); //To change
		// body of generated methods, choose Tools | Templates.
		return this.getOrder() - o.getOrder();
	}

	@Override
	public String toString() {
		return "MenuProfile [id_profile=" + id_profile + ", id_menu=" + id_menu + ", father=" + father + ", level="
				+ level + ", menu_name=" + menu_name + ", url=" + url + ", state=" + state + ", nroh=" + nroh
				+ ", icon=" + icon + ", order=" + order + "]";
	}

}
