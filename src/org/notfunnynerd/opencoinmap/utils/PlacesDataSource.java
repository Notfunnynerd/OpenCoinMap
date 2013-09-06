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

import java.util.ArrayList;
import java.util.List;

import org.notfunnynerd.opencoinmap.models.Place;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PlacesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = { DbHelper.COLUMN_ID, DbHelper.COLUMN_TITLE,
			DbHelper.COLUMN_POPUP, DbHelper.COLUMN_TYPE, DbHelper.COLUMN_LAT,
			DbHelper.COLUMN_LNG };
	private static Context context;

	public PlacesDataSource(Context context) {
		PlacesDataSource.context = context;
		dbHelper = new DbHelper(context);
	}

	public void openWritable() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void openReadable() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Place createPlace(String title, String popup, double lat,
			double lng, String type) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_TITLE, title);
		values.put(DbHelper.COLUMN_POPUP, popup);
		values.put(DbHelper.COLUMN_TYPE, type);
		values.put(DbHelper.COLUMN_LAT, lat * 1E6);
		values.put(DbHelper.COLUMN_LNG, lng * 1E6);

		long insertId = database.insert(DbHelper.TABLE_PLACES, null, values);
		Cursor cursor = database.query(DbHelper.TABLE_PLACES, allColumns,
				DbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Place place = cursorToPlace(cursor);
		cursor.close();
		return place;
	}

	public Place createPlace(Place place) {
		return createPlace(place.getTitle(), place.getPopup(), place.getLat(),
				place.getLng(), place.getType());
	}

	private Place cursorToPlace(Cursor cursor) {
		Place place = new Place();
		place.setId(cursor.getLong(0));
		place.setTitle(cursor.getString(1));
		place.setPopup(cursor.getString(2));
		place.setType(cursor.getString(3));
		place.setLat(cursor.getLong(4) / 1E6);
		place.setLng(cursor.getLong(5) / 1E6);
		return place;
	}

	public List<String> getTypes() {
		List<String> types = new ArrayList<String>();

		Cursor cursor = database.query(DbHelper.TABLE_PLACES,
				new String[] { DbHelper.COLUMN_TYPE }, null, null,
				DbHelper.COLUMN_TYPE, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			types.add(cursor.getString(0));
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return types;
	}

	public int getPlacesCount() {
		Cursor cursor = database.rawQuery("select count(*) from "
				+ DbHelper.TABLE_PLACES, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		return count;
	}

	public List<Place> getAllPlaces() {
		List<Place> places = new ArrayList<Place>();

		Cursor cursor = database.query(DbHelper.TABLE_PLACES, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Place place = cursorToPlace(cursor);
			places.add(place);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return places;
	}

	public List<Place> getFilteredPlaces(String[] categories) {
		List<Place> places = new ArrayList<Place>();

		Cursor cursor = database.query(DbHelper.TABLE_PLACES, allColumns,
				DbHelper.COLUMN_TYPE + " IN ("
						+ makePlaceholders(categories.length) + ")",
				categories, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Place place = cursorToPlace(cursor);
			places.add(place);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return places;
	}

	public void deletePlace(Place place) {
		long id = place.getId();
		System.out.println("Place deleted with id: " + id);
		database.delete(DbHelper.TABLE_PLACES, DbHelper.COLUMN_ID + " = " + id,
				null);
	}

	public void deleteAllPlaces() {
		try {
			database.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_PLACES);
		} catch (SQLException e) {
			Log.i("DB", "Table already deleted");
		}

		dbHelper = new DbHelper(context);
		dbHelper.onCreate(database);

	}

	private static String makePlaceholders(int len) {
		// http://stackoverflow.com/questions/7418849/android-sqlite-in-clause-and-placeholders/7419062#7419062
		if (len < 1) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder(len * 2 - 1);
			sb.append("?");
			for (int i = 1; i < len; i++) {
				sb.append(",?");
			}
			return sb.toString();
		}
	}

}
