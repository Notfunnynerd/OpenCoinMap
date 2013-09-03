package org.notfunnynerd.opencoinmap.models;

public class Place {
	// (-?[0-9]+(?:\.[0-9]*)?), (-?[0-9]+(?:\.[0-9]*)?)
	private long id;
	private double lat;
	private double lng;
	private String title;
	private String popup;
	private int type;

	public void setId(long id) {
		this.id = id;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPopup() {
		return popup;
	}

	public int getType() {
		return type;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public String toString() {
		return lat + ", " + lng;
	}

}
