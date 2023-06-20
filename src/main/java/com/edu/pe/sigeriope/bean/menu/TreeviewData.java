package com.edu.pe.sigeriope.bean.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeviewData {
	
	protected Long id;
	protected String name;
	protected String text;
	protected String href;
	protected String parent_id;
	protected String icon;
	//protected ArrayList<TreeviewDatanodeButton> buttons;
	
	private TreeviewNodeState state;
	private ArrayList<TreeviewData> nodes = new ArrayList<>();
	//private ArrayList<Modules> modules= new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long long1) {
		this.id = long1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public ArrayList<TreeviewData> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<TreeviewData> nodes) {
		this.nodes = nodes;
	}
	public void agregarMenuHijo(TreeviewData menu) {
		this.nodes.add(menu);
	}
	public boolean tieneMenuHijo() {
		return (this.nodes.size() > 0)? true : false;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public TreeviewNodeState getState() {
		return state;
	}
	public void setState(TreeviewNodeState state) {
		this.state = state;
	}

}
