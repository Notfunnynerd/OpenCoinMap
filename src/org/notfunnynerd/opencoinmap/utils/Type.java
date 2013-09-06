package org.notfunnynerd.opencoinmap.utils;

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

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.notfunnynerd.opencoinmap.R;

public class Type {

	public static final HashMap<String, Integer> typeToRes = new HashMap<String, Integer>() {
		{
			put("transport_airport", R.drawable.transport_airport);
			put("transport_airport_gate", R.drawable.transport_airport_gate);
			put("transport_helicopter_pad", R.drawable.transport_helicopter_pad);
			put("transport_airport_terminal",
					R.drawable.transport_airport_terminal);
			put("money_atm", R.drawable.money_atm);
			put("money_bank2", R.drawable.money_bank2);
			put("food_bar", R.drawable.food_bar);
			put("tourist_picnic", R.drawable.tourist_picnic);
			put("amenity_bench", R.drawable.amenity_bench);
			put("transport_parking_bicycle",
					R.drawable.transport_parking_bicycle);
			put("transport_rental_bicycle", R.drawable.transport_rental_bicycle);
			put("transport_bus_station", R.drawable.transport_bus_station);
			put("food_cafe", R.drawable.food_cafe);
			put("transport_rental_car", R.drawable.transport_rental_car);
			put("tourist_cinema", R.drawable.tourist_cinema);
			put("education_university", R.drawable.education_university);
			put("food_drinkingtap", R.drawable.food_drinkingtap);
			put("food_fastfood", R.drawable.food_fastfood);
			put("transport_port", R.drawable.transport_port);
			put("amenity_firestation2", R.drawable.amenity_firestation2);
			put("amenity_fountain2", R.drawable.amenity_fountain2);
			put("transport_fuel", R.drawable.transport_fuel);
			put("place_of_worship_unknown3",
					R.drawable.place_of_worship_unknown3);
			put("health_hospital", R.drawable.health_hospital);
			put("sport_shooting", R.drawable.sport_shooting);
			put("education_nursery3", R.drawable.education_nursery3);
			put("amenity_library", R.drawable.amenity_library);
			put("shopping_marketplace", R.drawable.shopping_marketplace);
			put("food_bar", R.drawable.food_bar);
			put("transport_parking_car", R.drawable.transport_parking_car);
			put("health_pharmacy", R.drawable.health_pharmacy);
			put("place_of_worship_unknown", R.drawable.place_of_worship_unknown);
			put("amenity_police2", R.drawable.amenity_police2);
			put("amenity_post_box", R.drawable.amenity_post_box);
			put("amenity_post_office", R.drawable.amenity_post_office);
			put("food_pub", R.drawable.food_pub);
			put("amenity_recycling", R.drawable.amenity_recycling);
			put("food_restaurant", R.drawable.food_restaurant);
			put("education_school", R.drawable.education_school);
			put("accommodation_shelter2", R.drawable.accommodation_shelter2);
			put("sport_swimming_outdoor", R.drawable.sport_swimming_outdoor);
			put("transport_taxi_rank", R.drawable.transport_taxi_rank);
			put("amenity_telephone", R.drawable.amenity_telephone);
			put("tourist_theatre", R.drawable.tourist_theatre);
			put("amenity_toilets", R.drawable.amenity_toilets);
			put("amenity_town_hall", R.drawable.amenity_town_hall);
			put("education_university", R.drawable.education_university);
			put("shopping_vending_machine", R.drawable.shopping_vending_machine);
			put("health_veterinary", R.drawable.health_veterinary);
			put("amenity_waste_bin", R.drawable.amenity_waste_bin);
			put("barrier_blocks", R.drawable.barrier_blocks);
			put("barrier_bollard", R.drawable.barrier_bollard);
			put("barrier_cattle_grid", R.drawable.barrier_cattle_grid);
			put("barrier_cycle_barrier", R.drawable.barrier_cycle_barrier);
			put("barrier_gate", R.drawable.barrier_gate);
			put("barrier_kissing_gate", R.drawable.barrier_kissing_gate);
			put("barrier_lift_gate", R.drawable.barrier_lift_gate);
			put("barrier_stile", R.drawable.barrier_stile);
			put("barrier_toll_booth", R.drawable.barrier_toll_booth);
			put("poi_boundary_administrative",
					R.drawable.poi_boundary_administrative);
			put("transport_bus_stop2", R.drawable.transport_bus_stop2);
			put("transport_zebra_crossing", R.drawable.transport_zebra_crossing);
			put("transport_miniroundabout_anticlockwise",
					R.drawable.transport_miniroundabout_anticlockwise);
			put("transport_turning_circle", R.drawable.transport_turning_circle);
			put("tourist_archaeological", R.drawable.tourist_archaeological);
			put("tourist_battlefield", R.drawable.tourist_battlefield);
			put("tourist_castle", R.drawable.tourist_castle);
			put("tourist_memorial", R.drawable.tourist_memorial);
			put("tourist_monument", R.drawable.tourist_monument);
			put("tourist_ruin", R.drawable.tourist_ruin);
			put("place_of_worship_unknown3",
					R.drawable.place_of_worship_unknown3);
			put("landuse_grass", R.drawable.landuse_grass);
			put("landuse_grass", R.drawable.landuse_grass);
			put("landuse_grass", R.drawable.landuse_grass);
			put("landuse_coniferous", R.drawable.landuse_coniferous);
			put("landuse_grass", R.drawable.landuse_grass);
			put("poi_military_bunker", R.drawable.poi_military_bunker);
			put("landuse_grass", R.drawable.landuse_grass);
			put("poi_mine", R.drawable.poi_mine);
			put("landuse_grass", R.drawable.landuse_grass);
			put("sport_golf", R.drawable.sport_golf);
			put("transport_marina", R.drawable.transport_marina);
			put("sport_leisure_centre", R.drawable.sport_leisure_centre);
			put("amenity_playground", R.drawable.amenity_playground);
			put("sport_leisure_centre", R.drawable.sport_leisure_centre);
			put("transport_slipway", R.drawable.transport_slipway);
			put("sport_leisure_centre", R.drawable.sport_leisure_centre);
			put("sport_stadium", R.drawable.sport_stadium);
			put("sport_leisure_centre", R.drawable.sport_leisure_centre);
			put("landuse_coniferous", R.drawable.landuse_coniferous);
			put("poi_place_city", R.drawable.poi_place_city);
			put("poi_place_hamlet", R.drawable.poi_place_hamlet);
			put("poi_place_suburb", R.drawable.poi_place_suburb);
			put("poi_place_suburb", R.drawable.poi_place_suburb);
			put("poi_place_town", R.drawable.poi_place_town);
			put("poi_place_village", R.drawable.poi_place_village);
			put("power_tower_low", R.drawable.power_tower_low);
			put("power_substation", R.drawable.power_substation);
			put("power_transformer", R.drawable.power_transformer);
			put("power_tower_high2", R.drawable.power_tower_high2);
			put("transport_train_station", R.drawable.transport_train_station);
			put("transport_tram_stop", R.drawable.transport_tram_stop);
			put("shopping_alcohol", R.drawable.shopping_alcohol);
			put("shopping_bakery", R.drawable.shopping_bakery);
			put("shopping_bicycle", R.drawable.shopping_bicycle);
			put("shopping_book", R.drawable.shopping_book);
			put("shopping_butcher", R.drawable.shopping_butcher);
			put("shopping_car_repair", R.drawable.shopping_car_repair);
			put("shopping_car", R.drawable.shopping_car);
			put("shopping_clothes", R.drawable.shopping_clothes);
			put("shopping_confectionery", R.drawable.shopping_confectionery);
			put("shopping_convenience", R.drawable.shopping_convenience);
			put("shopping_department_store",
					R.drawable.shopping_department_store);
			put("shopping_diy", R.drawable.shopping_diy);
			put("shopping_fish", R.drawable.shopping_fish);
			put("shopping_florist", R.drawable.shopping_florist);
			put("shopping_garden_centre", R.drawable.shopping_garden_centre);
			put("shopping_gift", R.drawable.shopping_gift);
			put("shopping_greengrocer", R.drawable.shopping_greengrocer);
			put("shopping_hairdresser", R.drawable.shopping_hairdresser);
			put("shopping_hifi", R.drawable.shopping_hifi);
			put("shopping_jewelry", R.drawable.shopping_jewelry);
			put("shopping_kiosk", R.drawable.shopping_kiosk);
			put("shopping_laundrette", R.drawable.shopping_laundrette);
			put("shopping_motorcycle", R.drawable.shopping_motorcycle);
			put("shopping_music", R.drawable.shopping_music);
			put("shopping_supermarket", R.drawable.shopping_supermarket);
			put("shopping_supermarket", R.drawable.shopping_supermarket);
			put("shopping_toys", R.drawable.shopping_toys);
			put("accommodation_alpinehut", R.drawable.accommodation_alpinehut);
			put("tourist_art_gallery2", R.drawable.tourist_art_gallery2);
			put("tourist_attraction", R.drawable.tourist_attraction);
			put("accommodation_camping", R.drawable.accommodation_camping);
			put("accommodation_caravan_park",
					R.drawable.accommodation_caravan_park);
			put("accommodation_chalet", R.drawable.accommodation_chalet);
			put("accommodation_bed_and_breakfast",
					R.drawable.accommodation_bed_and_breakfast);
			put("accommodation_youth_hostel",
					R.drawable.accommodation_youth_hostel);
			put("accommodation_hotel", R.drawable.accommodation_hotel);
			put("accommodation_motel", R.drawable.accommodation_motel);
			put("tourist_museum", R.drawable.tourist_museum);
			put("tourist_picnic", R.drawable.tourist_picnic);
			put("tourist_theme_park", R.drawable.tourist_theme_park);
			put("tourist_view_point", R.drawable.tourist_view_point);
			put("tourist_zoo", R.drawable.tourist_zoo);
			put("transport_speedbump", R.drawable.transport_speedbump);
		}
	};
	public static final HashMap<String, String> tagsToType = new HashMap<String, String>() {
		{
			// Source : https://github.com/prusnak/coinmap by Prusnak
			put("aeroway:aerodrome", "transport_airport");
			put("aeroway:gate", "transport_airport_gate");
			put("aeroway:helipad", "transport_helicopter_pad");
			put("aeroway:terminal", "transport_airport_terminal");
			put("amenity:atm", "money_atm");
			put("amenity:bank", "money_bank2");
			put("amenity:bar", "food_bar");
			put("amenity:bbq", "tourist_picnic");
			put("amenity:bench", "amenity_bench");
			put("amenity:bicycle_parking", "transport_parking_bicycle");
			put("amenity:bicycle_rental", "transport_rental_bicycle");
			put("amenity:bus_station", "transport_bus_station");
			put("amenity:cafe", "food_cafe");
			put("amenity:car_rental", "transport_rental_car");
			put("amenity:cinema", "tourist_cinema");
			put("amenity:college", "education_university");
			put("amenity:drinking_water", "food_drinkingtap");
			put("amenity:fast_food", "food_fastfood");
			put("amenity:ferry_terminal", "transport_port");
			put("amenity:fire_station", "amenity_firestation2");
			put("amenity:fountain", "amenity_fountain2");
			put("amenity:fuel", "transport_fuel");
			put("amenity:grave_yard", "place_of_worship_unknown3");
			put("amenity:hospital", "health_hospital");
			put("amenity:hunting_stand", "sport_shooting");
			put("amenity:kindergarten", "education_nursery3");
			put("amenity:library", "amenity_library");
			put("amenity:marketplace", "shopping_marketplace");
			put("amenity:nightclub", "food_bar");
			put("amenity:parking", "transport_parking_car");
			put("amenity:pharmacy", "health_pharmacy");
			put("amenity:place_of_worship", "place_of_worship_unknown");
			put("amenity:police", "amenity_police2");
			put("amenity:post_box", "amenity_post_box");
			put("amenity:post_office", "amenity_post_office");
			put("amenity:pub", "food_pub");
			put("amenity:recycling", "amenity_recycling");
			put("amenity:restaurant", "food_restaurant");
			put("amenity:school", "education_school");
			put("amenity:shelter", "accommodation_shelter2");
			put("amenity:swimming_pool", "sport_swimming_outdoor");
			put("amenity:taxi", "transport_taxi_rank");
			put("amenity:telephone", "amenity_telephone");
			put("amenity:theatre", "tourist_theatre");
			put("amenity:toilets", "amenity_toilets");
			put("amenity:townhall", "amenity_town_hall");
			put("amenity:university", "education_university");
			put("amenity:vending_machine", "shopping_vending_machine");
			put("amenity:veterinary", "health_veterinary");
			put("amenity:waste_basket", "amenity_waste_bin");
			put("barrier:block", "barrier_blocks");
			put("barrier:bollard", "barrier_bollard");
			put("barrier:cattle_grid", "barrier_cattle_grid");
			put("barrier:cycle_barrier", "barrier_cycle_barrier");
			put("barrier:gate", "barrier_gate");
			put("barrier:kissing_gate", "barrier_kissing_gate");
			put("barrier:lift_gate", "barrier_lift_gate");
			put("barrier:stile", "barrier_stile");
			put("barrier:toll_booth", "barrier_toll_booth");
			put("boundary:administrative", "poi_boundary_administrative");
			put("highway:bus_stop", "transport_bus_stop2");
			put("highway:crossing", "transport_zebra_crossing");
			put("highway:mini_roundabout",
					"transport_miniroundabout_anticlockwise");
			put("highway:turning_circle", "transport_turning_circle");
			put("historic:archaeological_site", "tourist_archaeological");
			put("historic:battlefield", "tourist_battlefield");
			put("historic:castle", "tourist_castle");
			put("historic:memorial", "tourist_memorial");
			put("historic:monument", "tourist_monument");
			put("historic:ruins", "tourist_ruin");
			put("landuse:cemetery", "place_of_worship_unknown3");
			put("landuse:farmland", "landuse_grass");
			put("landuse:farm", "landuse_grass");
			put("landuse:farmyard", "landuse_grass");
			put("landuse:forest", "landuse_coniferous");
			put("landuse:meadow", "landuse_grass");
			put("landuse:military", "poi_military_bunker");
			put("landuse:orchard", "landuse_grass");
			put("landuse:quarry", "poi_mine");
			put("landuse:vineyard", "landuse_grass");
			put("leisure:golf_course", "sport_golf");
			put("leisure:marina", "transport_marina");
			put("leisure:pitch", "sport_leisure_centre");
			put("leisure:playground", "amenity_playground");
			put("leisure:recreation_ground", "sport_leisure_centre");
			put("leisure:slipway", "transport_slipway");
			put("leisure:sports_centre", "sport_leisure_centre");
			put("leisure:stadium", "sport_stadium");
			put("leisure:track", "sport_leisure_centre");
			put("natural:wood", "landuse_coniferous");
			put("place:city", "poi_place_city");
			put("place:hamlet", "poi_place_hamlet");
			put("place:neighbourhood", "poi_place_suburb");
			put("place:suburb", "poi_place_suburb");
			put("place:town", "poi_place_town");
			put("place:village", "poi_place_village");
			put("power:pole", "power_tower_low");
			put("power:station", "power_substation");
			put("power:sub_station", "power_transformer");
			put("power:tower", "power_tower_high2");
			put("railway:station", "transport_train_station");
			put("railway:tram_stop", "transport_tram_stop");
			put("shop:alcohol", "shopping_alcohol");
			put("shop:bakery", "shopping_bakery");
			put("shop:bicycle", "shopping_bicycle");
			put("shop:books", "shopping_book");
			put("shop:butcher", "shopping_butcher");
			put("shop:car_repair", "shopping_car_repair");
			put("shop:car", "shopping_car");
			put("shop:clothes", "shopping_clothes");
			put("shop:confectionery", "shopping_confectionery");
			put("shop:convenience", "shopping_convenience");
			put("shop:department_store", "shopping_department_store");
			put("shop:doityourself", "shopping_diy");
			put("shop:fishmonger", "shopping_fish");
			put("shop:florist", "shopping_florist");
			put("shop:garden_centre", "shopping_garden_centre");
			put("shop:gift", "shopping_gift");
			put("shop:greengrocer", "shopping_greengrocer");
			put("shop:hairdresser", "shopping_hairdresser");
			put("shop:hifi", "shopping_hifi");
			put("shop:jewelry", "shopping_jewelry");
			put("shop:kiosk", "shopping_kiosk");
			put("shop:laundry", "shopping_laundrette");
			put("shop:motorcycle", "shopping_motorcycle");
			put("shop:music", "shopping_music");
			put("shop:supermarket", "shopping_supermarket");
			put("shop:supermarket", "shopping_supermarket");
			put("shop:toys", "shopping_toys");
			put("tourism:alpine_hut", "accommodation_alpinehut");
			put("tourism:artwork", "tourist_art_gallery2");
			put("tourism:attraction", "tourist_attraction");
			put("tourism:camp_site", "accommodation_camping");
			put("tourism:caravan_site", "accommodation_caravan_park");
			put("tourism:chalet", "accommodation_chalet");
			put("tourism:guest_house", "accommodation_bed_and_breakfast");
			put("tourism:hostel", "accommodation_youth_hostel");
			put("tourism:hotel", "accommodation_hotel");
			put("tourism:motel", "accommodation_motel");
			put("tourism:museum", "tourist_museum");
			put("tourism:picnic_site", "tourist_picnic");
			put("tourism:theme_park", "tourist_theme_park");
			put("tourism:viewpoint", "tourist_view_point");
			put("tourism:zoo", "tourist_zoo");
			put("traffic_calming:yes", "transport_speedbump");
		}
	};

	public static String getTypeFromTags(JSONObject tags) {
		// Transcribed to Java from https://github.com/prusnak/coinmap
		// Original author Prusnak

		String type = "bitcoin";
		for (Entry<String, String> entry : tagsToType.entrySet()) {
			try {
				String kv = entry.getKey();
				String[] tmp = kv.split(":");
				String k = tmp[0];
				String v = tmp[1];
				if (tags.has(k)) {
					String t = tags.getString(k).split(";")[0];
					if (t.equals(v)) {
						type = tagsToType.get(kv);
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return type;
	}

	public static Integer getResFromType(String type) {
		Integer res = R.drawable.bitcoin;

		if (typeToRes.containsKey(type)) {
			res = typeToRes.get(type);
		}

		return res;
	}

}
