package com.edu.pe.sigeriope.bean.menu;

public class TreeviewNodeState {
	private boolean checked;
	private boolean disabled;
	private boolean expanded;
	private boolean selected;
	public TreeviewNodeState(boolean checked, boolean disabled, boolean expanded, boolean selected) {
		this.checked = checked;
		this.disabled = disabled;
		this.expanded = expanded;
		this.selected = selected;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	

}
