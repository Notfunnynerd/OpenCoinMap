package org.notfunnynerd.opencoinmap.models;

public class Filter {

	private String name;

	private boolean selected;

	public Filter(String name, boolean selected) {
		this.name = name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
