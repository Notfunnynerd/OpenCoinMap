package org.notfunnynerd.opencoinmap;

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

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.notfunnynerd.opencoinmap.models.Filter;
import org.notfunnynerd.opencoinmap.utils.DbHelper;
import org.notfunnynerd.opencoinmap.utils.JSONParser;
import org.notfunnynerd.opencoinmap.utils.PlacesDataSource;
import org.notfunnynerd.opencoinmap.utils.Type;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class SplashActivity extends Activity implements OnClickListener {

	private PlacesDataSource datasource;
	private Button refreshButton, showButton;
	private ProgressBar progressBar;

	private DownloadTask downloadTask = null;
	private LoadTask loadTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		showButton = ((Button) findViewById(R.id.showButton));
		refreshButton = ((Button) findViewById(R.id.refreshButton));
		progressBar = ((ProgressBar) findViewById(R.id.progressBar));
		showButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);
		showButton.setEnabled(false);

		if (isDatabaseDownloaded()) {
			showButton.setEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.showButton:
			if (isDatabaseDownloaded()) {
				startActivity(new Intent(SplashActivity.this, MapActivity.class));
			}
			break;
		case R.id.refreshButton:
			updateContent();
		default:
			break;
		}
	}

	private boolean isDatabaseDownloaded() {
		File database = getApplicationContext().getDatabasePath(
				DbHelper.DATABASE_NAME);

		return database.exists();

	}

	private void showProgress(boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	private void updateContent() {
		showButton.setEnabled(false);
		datasource = new PlacesDataSource(this);
		datasource.openWritable();
		datasource.deleteAllPlaces();
		// silly...
		datasource = new PlacesDataSource(this);
		datasource.openWritable();

		if (downloadTask != null) {
			downloadTask.cancel(true);
		} else {
			downloadTask = new DownloadTask();
			downloadTask.execute(getResources().getString(R.string.data_url));
		}
	}

	private class DownloadTask extends AsyncTask<String, Integer, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... urls) {
			JSONObject jObj = null;

			try {
				jObj = JSONParser.getJSONfromUrl(urls[0]);
			} catch (Exception e) {
				Log.e("Download", e.getMessage());
			}
			return jObj;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress(true);
			refreshButton.setText(R.string.downloading);
		}

		@Override
		protected void onPostExecute(JSONObject jObj) {
			super.onPostExecute(jObj);
			showProgress(false);
			if (jObj != null) {
				if (loadTask != null) {
					loadTask.cancel(true);
				}
				loadTask = new LoadTask();
				loadTask.execute(jObj);
			}
		}

	}

	private void loadPlace(JSONObject jPlace) {
		String type = "bitcoin";
		String popup = "";
		try {
			if (jPlace.has("lat") && jPlace.has("lon") && jPlace.has("type")) {
				if (jPlace.getString("type").equals("node")) {
					String name = jPlace.getString("type") + " "
							+ jPlace.getInt("id");

					if (jPlace.has("tags")) {
						JSONObject jTags = jPlace.getJSONObject("tags");
						if (jTags.has("payment:bitcoin")) {
							type = Type.getTypeFromTags(jTags);
							name = (jTags.has("name") ? jTags.getString("name")
									: name);
							popup += "Category: " + Filter.beautifyName(type)
									+ "\n\n";
							if (jTags.has("addr:street")) {
								popup += jTags.getString("addr:street")
										+ (jTags.has("addr:housenumber") ? " "
												+ jTags.getString("addr:housenumber")
												: "") + "\n";
							}
							if (jTags.has("addr:city")) {
								popup += (jTags.has("postcode") ? jTags
										.getString("postcode") : "")
										+ jTags.getString("addr:city") + "\n";
							}
							if (jTags.has("addr:country")) {
								popup += jTags.getString("addr:country") + "\n";
							}
							if (jTags.has("contact:website")) {
								popup += jTags.getString("contact:website")
										+ "\n";
							} else if (jTags.has("website")) {
								popup += jTags.getString("website") + "\n";
							}

							if (jTags.has("contact:email")) {
								popup += jTags.getString("contact:email")
										+ "\n";
							} else if (jTags.has("email")) {
								popup += jTags.getString("email") + "\n";
							}
							if (jTags.has("contact:phone")) {
								popup += jTags.getString("contact:phone")
										+ "\n";
							} else if (jTags.has("phone")) {
								popup += jTags.getString("phone") + "\n";
							}

							datasource.createPlace(name, popup,
									jPlace.getDouble("lat"),
									jPlace.getDouble("lon"), type);
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class LoadTask extends AsyncTask<JSONObject, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(JSONObject... jObjs) {
			Boolean loaded = false;
			JSONObject jObj = jObjs[0];

			JSONArray jArray;
			try {

				jArray = jObj.getJSONArray("elements");

				for (int i = 0; i < jArray.length(); i++) {
					loadPlace(jArray.getJSONObject(i));
				}

				loaded = true;
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return loaded;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress(true);

			refreshButton.setText(R.string.loading_db);
		}

		@Override
		protected void onPostExecute(Boolean success) {
			super.onPostExecute(success);
			if (success) {
				refreshButton.setText(R.string.done);
				refreshButton.setEnabled(false);
				showButton.setEnabled(true);
			}
			showProgress(false);

		}
	}
}
