package org.notfunnynerd.opencoinmap.models;

import java.util.ArrayList;
import java.util.List;

import org.notfunnynerd.opencoinmap.utils.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlacesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = { DbHelper.COLUMN_ID, DbHelper.COLUMN_TITLE,
			DbHelper.COLUMN_POPUP, DbHelper.COLUMN_TYPE, DbHelper.COLUMN_LAT,
			DbHelper.COLUMN_LNG };

	public PlacesDataSource(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Place creatPlace(String title, String popup, double lat, double lng,
			int type) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_TITLE, title);
		values.put(DbHelper.COLUMN_POPUP, popup);
		values.put(DbHelper.COLUMN_TYPE, type);
		values.put(DbHelper.COLUMN_LAT, lat);
		values.put(DbHelper.COLUMN_LNG, lng);

		long insertId = database.insert(DbHelper.TABLE_PLACES, null, values);
		Cursor cursor = database.query(DbHelper.TABLE_PLACES, allColumns,
				DbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Place place = cursorToPlace(cursor);
		cursor.close();
		return place;
	}

	public Place createPlace(Place place) {
		return creatPlace(place.getTitle(), place.getPopup(), place.getLat(),
				place.getLng(), place.getType());
	}

	public void deletePlace(Place place) {
		long id = place.getId();
		System.out.println("Place deleted with id: " + id);
		database.delete(DbHelper.TABLE_PLACES, DbHelper.COLUMN_ID + " = " + id,
				null);
	}

	public int getPlacesCount() {
		Cursor cursor = database.rawQuery("select count(*) from "
				+ DbHelper.TABLE_PLACES, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		return count;
	}

	public void deleteAllPlaces() {
		database.delete(DbHelper.TABLE_PLACES, null, null);
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

	private Place cursorToPlace(Cursor cursor) {
		Place place = new Place();
		place.setId(cursor.getLong(0));
		place.setTitle(cursor.getString(1));
		place.setPopup(cursor.getString(2));
		place.setType(cursor.getInt(3));
		place.setLat(cursor.getLong(4));
		place.setLng(cursor.getLong(5));
		return place;
	}

}
