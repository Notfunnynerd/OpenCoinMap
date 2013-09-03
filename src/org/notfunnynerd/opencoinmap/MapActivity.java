package org.notfunnynerd.opencoinmap;

import java.util.ArrayList;
import java.util.HashMap;

import org.notfunnynerd.opencoinmap.models.Place;
import org.notfunnynerd.opencoinmap.models.PlacesDataSource;
import org.notfunnynerd.opencoinmap.utils.Type;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {

	private GoogleMap mMap;
	private PlacesDataSource datasource;
	private HashMap<Marker, Place> markerMap = new HashMap<Marker, Place>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		setUpMapIfNeeded();
		datasource = new PlacesDataSource(this);
		datasource.open();
		Log.i("Map", datasource.getPlacesCount() + " places !");
		// semble etre necessaire pour que la map s'initialise avant de lui
		// rajouter des choses...
		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}

		if (mMap != null) {
			for (Place place : datasource.getAllPlaces()) {

				// If the marker isn't already being displayed
				if (!markerMap.containsValue(place)) {
					// Add the Marker to the Map and keep track of it
					markerMap.put(mMap.addMarker(new MarkerOptions().position(
							new LatLng(place.getLat(), place.getLng())).icon(
							BitmapDescriptorFactory.fromResource(Type
									.getResourceFromId(place.getType())))),
							place);
				}

				// mMap.addMarker(new MarkerOptions()
				// .position(new LatLng(place.getLat(), place.getLng()))
				// .title(place.getTitle())
				// .snippet(place.getPopup())
				// .icon(BitmapDescriptorFactory
				// .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
			}
		}

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker marker) {

				// Getting view from the layout file info_window_layout
				View v = getLayoutInflater()
						.inflate(R.layout.info_window, null);

				// Getting the position from the marker
				Place place = markerMap.get(marker);

				// Getting reference to the TextView to set latitude
				TextView title = (TextView) v.findViewById(R.id.title);

				// Getting reference to the TextView to set longitude
				TextView popup = (TextView) v.findViewById(R.id.popup);

				// Setting the latitude
				title.setText(place.getTitle());

				// Setting the longitude
				// popup.setText(place.getPopup());

				popup.setText(Html.fromHtml(place.getPopup()),
						BufferType.SPANNABLE);
				// Returning the view containing InfoWindow contents
				return v;

			}
		});
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.filter_item:
			showFilterDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showFilterDialog() {
		final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("");

		LayoutInflater inflater = getLayoutInflater();
		final View PopupLayout = inflater
				.inflate(R.layout.filters_dialog, null);
		helpBuilder.setView(PopupLayout);

		final AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();

		ListView filterList = (ListView) PopupLayout
				.findViewById(R.id.listFilters);

		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		for (int i = 0; i < Type.TYPE_NAMES.length; i++) {
			map = new HashMap<String, String>();
			map.put("name", Type.TYPE_NAMES[i]);
			mylist.add(map);
		}
		SimpleAdapter sd = new SimpleAdapter(MapActivity.this, mylist,
				R.layout.filter_item, new String[] { "name" },
				new int[] { R.id.caption });
		filterList.setAdapter(sd);

	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.

			} else {
				Toast.makeText(
						this,
						"Il semble y avoir un problème avec l'affichage de la carte. Avez-vous bien installé Google Maps ?",
						Toast.LENGTH_LONG).show();
			}
		}
	}

}
