package org.notfunnynerd.opencoinmap.models;

/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
 * Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 * 
 * Largely inspired by Prusnak's work : https://github.com/prusnak/coinmap
 * Map Data CC-BY-SA by OpenStreetMap http://openstreetmap.org/
 * Icons CC-0 by Brian Quinion http://www.sjjb.co.uk/mapicons/
 * 
 * @author NotFunnyNerd <notfunnynerd@gmail.com> - 2013
 * 
 */

public class Filter {

	private String name;
	private String beautifulName;

	private boolean selected;

	public Filter(String name, boolean selected) {
		this.name = name;
		this.selected = selected;
		this.beautifulName = beautifyName(name);

	}

	public static String beautifyName(String name) {
		String beautifulName = "Other";
		int i = name.indexOf('_');
		if (i != -1) {
			beautifulName = name.substring(i + 1);
			beautifulName = beautifulName.replaceAll("_", " ");
			beautifulName = Character.toUpperCase(beautifulName.charAt(0))
					+ beautifulName.substring(1);
		}
		return beautifulName;
	}

	public String getName() {
		return name;
	}

	public String getBeautifulName() {
		return beautifulName;
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
