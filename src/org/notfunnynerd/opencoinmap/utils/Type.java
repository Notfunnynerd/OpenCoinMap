package org.notfunnynerd.opencoinmap.utils;

import org.notfunnynerd.opencoinmap.R;

public class Type {
	private static final String[] TYPE_ICONS = { "icon_sport_leisure_centre",
			"icon_shopping_toys", "icon_shopping_supermarket",
			"icon_shopping_music", "icon_shopping_marketplace",
			"icon_shopping_jewelry", "icon_shopping_hairdresser",
			"icon_shopping_greengrocer", "icon_shopping_garden_centre",
			"icon_shopping_florist", "icon_shopping_diy",
			"icon_shopping_convenience", "icon_shopping_clothes",
			"icon_shopping_car_repair", "icon_shopping_car",
			"icon_shopping_book", "icon_shopping_bakery",
			"icon_shopping_alcohol", "icon_money_bank2", "icon_landuse_grass",
			"icon_food_restaurant", "icon_food_pub", "icon_food_fastfood",
			"icon_food_cafe", "icon_food_bar", "icon_education_university",
			"icon_education_school", "icon_bitcoin", "icon_amenity_recycling",
			"icon_amenity_library", "icon_accommodation_youth_hostel",
			"icon_accommodation_hotel", "icon_accommodation_camping",
			"icon_accommodation_bed_and_breakfast",
			"icon_accommodation_alpinehut" };

	private static final int[] TYPE_RES = { R.drawable.sport_leisure_centre,
			R.drawable.shopping_toys, R.drawable.shopping_supermarket,
			R.drawable.shopping_music, R.drawable.shopping_marketplace,
			R.drawable.shopping_jewelry, R.drawable.shopping_hairdresser,
			R.drawable.shopping_greengrocer, R.drawable.shopping_garden_centre,
			R.drawable.shopping_florist, R.drawable.shopping_diy,
			R.drawable.shopping_convenience, R.drawable.shopping_clothes,
			R.drawable.shopping_car_repair, R.drawable.shopping_car,
			R.drawable.shopping_book, R.drawable.shopping_bakery,
			R.drawable.shopping_alcohol, R.drawable.money_bank2,
			R.drawable.landuse_grass, R.drawable.food_restaurant,
			R.drawable.food_pub, R.drawable.food_fastfood,
			R.drawable.food_cafe, R.drawable.food_bar,
			R.drawable.education_university, R.drawable.education_school,
			R.drawable.bitcoin, R.drawable.amenity_recycling,
			R.drawable.amenity_library, R.drawable.accommodation_youth_hostel,
			R.drawable.accommodation_hotel, R.drawable.accommodation_camping,
			R.drawable.accommodation_bed_and_breakfast,
			R.drawable.accommodation_alpinehut };

	public static final String[] TYPE_NAMES = { "sport leisure centre",
			"shopping toys", "shopping supermarket", "shopping music",
			"shopping marketplace", "shopping jewelry", "shopping hairdresser",
			"shopping greengrocer", "shopping garden centre",
			"shopping florist", "shopping diy", "shopping convenience",
			"shopping clothes", "shopping car repair", "shopping car",
			"shopping book", "shopping bakery", "shopping alcohol",
			"money bank2", "landuse grass", "food restaurant", "food pub",
			"food fastfood", "food cafe", "food bar", "education university",
			"education school", "bitcoin", "amenity recycling",
			"amenity library", "accommodation youth hostel",
			"accommodation hotel", "accommodation camping",
			"accommodation bed and breakfast", "accommodation alpinehut" };

	// public static Drawable getAndroidDrawable(String drawable){
	// int resourceId=Resources.getSystem().getIdentifier(drawable, "drawable",
	// "android");
	// if(resourceId==0){
	// return null;
	// } else {
	// return Resources.getSystem().getDrawable(resourceId);
	// }
	// }
	public static int getIdFromIcon(String icon) {
		int id = -1;
		for (int i = 0; i < TYPE_ICONS.length; i++) {
			if (TYPE_ICONS[i].equals(icon)) {
				id = i;
				break;
			}
		}
		return id;
	}

	public static String getIconFromId(int id) {
		String type = "bitcoin";
		if (id != -1 && id < TYPE_NAMES.length) {
			type = TYPE_NAMES[id];
		}
		return type;
	}

	public static int getResourceFromId(int id) {
		int res = R.drawable.bitcoin;
		if (id != -1 && id < TYPE_RES.length) {
			res = TYPE_RES[id];
		}
		return res;
	}

	public static int getIdFromType(String type) {
		int id = -1;
		for (int i = 0; i < TYPE_NAMES.length; i++) {
			if (TYPE_NAMES[i].equals(type)) {
				id = i;
				break;
			}
		}
		return id;
	}

	public static String getTypeFromId(int id) {
		String type = "Unknown";
		if (id != 1 && id < TYPE_NAMES.length) {
			type = TYPE_NAMES[id];
		}
		return type;
	}
}
