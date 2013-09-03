package org.notfunnynerd.opencoinmap.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.notfunnynerd.opencoinmap.models.Place;

import android.util.Log;

public class DataParser {
	// (-?[0-9]+(?:\.[0-9]*)?), (-?[0-9]+(?:\.[0-9]*)?)
	private static final String latlngRegexp = "(-?[0-9]+(?:\\.[0-9]*)?), (-?[0-9]+(?:\\.[0-9]*)?)";
	private static final String iconRegexp = "icon: (\\w+)\\}";
	private static final String titleRegexp = "\"title\": \"(.+)\",";
	private static final String popupRegexp = "bindPopup\\(\"(.+)\"\\)";

	private static final Pattern latLngPattern = Pattern.compile(latlngRegexp);
	private static final Pattern iconPattern = Pattern.compile(iconRegexp);
	private static final Pattern titlePattern = Pattern.compile(titleRegexp);
	private static final Pattern popupPattern = Pattern.compile(popupRegexp);

	private static Matcher m1, m2, m3, m4;

	// L.marker([52.1989253, 0.1390513], {"title": "Devonshire Arms", icon:
	// icon_food_pub}).bindPopup("<b>Devonshire Arms</b> <a href=\"http://openstreetmap.org/browse/node/25285250\" target=\"_blank\">*</a><hr/>Devonshire Road 1<br/>CB1 2BH Cambridge<br/><hr/>website: <a href=\"http://www.individualpubs.co.uk/devonshire/\" target=\"_blank\">http://www.individualpubs.co.uk/devonshire/</a><br/>phone: +44 1223 316610<br/>").addTo(markers);

	// icon: (\w+)}
	// "title": "(.+)",
	// bindPopup\("(.+)"\)

	public static Place getPlaceFromRaw(String raw) {
		Place place = null;
		m1 = latLngPattern.matcher(raw);
		if (m1.find()) {
			double lat = Double.parseDouble(m1.group(1));
			double lng = Double.parseDouble(m1.group(2));
			place = new Place();
			place.setLat(lat);
			place.setLng(lng);
			m2 = iconPattern.matcher(raw);
			if (m2.find()) {
				String icon = m2.group(1);
				// icon_food_pub
				// icon_accommodation_hotel
				// icon_bitcoin
				// icon_shopping_car
				// icon_shopping_book
				// icon_shopping_music
				place.setType(Type.getIdFromIcon(icon));
				m3 = titlePattern.matcher(raw);
				if (m3.find()) {
					String title = m3.group(1);
					place.setTitle(title);
					m4 = popupPattern.matcher(raw);
					if (m4.find()) {
						String popup = m4.group(1);
						place.setPopup(popup);
						return place;

					}
				}

			}
		}
		Log.e("Parser", raw);
		return null;
	}
}
