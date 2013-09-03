package org.notfunnynerd.opencoinmap.utils;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	public static final String TABLE_PLACES = "places";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_POPUP = "popup";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_LNG = "lng";

	private static final String DATABASE_NAME = "places.db";
	private static final int DATABASE_VERSION = 1;

	// "title": "Devonshire Arms", icon:
	// icon_food_pub}).bindPopup("<b>Devonshire Arms</b> <a
	// href=\"http://openstreetmap.org/browse/node/25285250\"
	// target=\"_blank\">*</a><hr/>Devonshire Road 1<br/>CB1 2BH
	// Cambridge<br/><hr/>website: <a
	// href=\"http://www.individualpubs.co.uk/devonshire/\"
	// target=\"_blank\">http://www.individualpubs.co.uk/devonshire/</a><br/>phone:
	// +44 1223 316610<br/>

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PLACES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TITLE
			+ " text not null, " + COLUMN_POPUP + " text not null, "
			+ COLUMN_TYPE + " integer not null, " + COLUMN_LAT
			+ " real not null, " + COLUMN_LNG + " real not null);";

	public DbHelper(Context context) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		super(context,
//				Environment.getExternalStorageDirectory() + File.separator
//						+ "/.openCM/" + File.separator + DATABASE_NAME, null,
//				DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
		onCreate(db);
	}

}
