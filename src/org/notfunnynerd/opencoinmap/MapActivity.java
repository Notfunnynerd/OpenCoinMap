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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.notfunnynerd.opencoinmap.models.Filter;
import org.notfunnynerd.opencoinmap.models.Place;
import org.notfunnynerd.opencoinmap.utils.FilterListAdapter;
import org.notfunnynerd.opencoinmap.utils.PlacesDataSource;
import org.notfunnynerd.opencoinmap.utils.Type;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements LocationListener {

	private GoogleMap mMap;
	private PlacesDataSource datasource;
	private HashMap<Marker, Place> markerMap;
	private ArrayList<Filter> filterList;
	private LocationManager locationManager;
	private String provider;
	private Marker locMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		setUpMapIfNeeded();

		datasource = new PlacesDataSource(this);
		datasource.openReadable();

		populateFilterList();

		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}

		populateMarkers();
		initLocation();

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getLayoutInflater()
						.inflate(R.layout.info_window, null);

				Place place = markerMap.get(marker);
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView popup = (TextView) v.findViewById(R.id.popup);

				title.setText(place.getTitle());
				popup.setText(place.getPopup());

				return v;
			}
		});

		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				final Place place = markerMap.get(marker);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MapActivity.this);

				alertDialogBuilder
						.setMessage(R.string.ask_directions)
						.setCancelable(false)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										Intent intent = new Intent(
												android.content.Intent.ACTION_VIEW,
												Uri.parse("google.navigation:q="
														+ place.getLat()
														+ ","
														+ place.getLng()));

										startActivity(intent);
									}
								})
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});
	}

	private void initLocation() {
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		boolean enabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!enabled) {
			Toast.makeText(this, getString(R.string.enable_location_service),
					Toast.LENGTH_LONG).show();
		}

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
	}

	private void getLocation() {
		locationManager.requestLocationUpdates(provider, 1000, 1, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		double lat = (location.getLatitude());
		double lng = (location.getLongitude());
		Place place = new Place();
		place.setTitle(getString(R.string.yourself));
		place.setPopup("");
		place.setLat(lat);
		place.setLng(lng);

		locMarker = mMap.addMarker(new MarkerOptions().position(
				new LatLng(lat, lng)).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
		markerMap.put(locMarker, place);
		locationManager.removeUpdates(this);

	}

	private void populateMarkers() {
		if (markerMap != null) {
			markerMap.clear();
		} else {
			markerMap = new HashMap<Marker, Place>();
		}
		mMap.clear();
		if (mMap != null) {
			for (Place place : datasource.getAllPlaces()) {

				// If the marker isn't already being displayed
				if (!markerMap.containsValue(place)) {
					// Add the Marker to the Map and keep track of it
					markerMap.put(mMap.addMarker(new MarkerOptions().position(
							new LatLng(place.getLat(), place.getLng())).icon(
							BitmapDescriptorFactory.fromResource(Type
									.getResFromType(place.getType())))), place);
				}
			}
		}
	}

	private void updateMarkers() {
		if (markerMap != null) {
			markerMap.clear();
		} else {
			markerMap = new HashMap<Marker, Place>();
		}
		mMap.clear();
		if (mMap != null) {
			for (Place place : datasource.getFilteredPlaces(getCategories())) {

				// If the marker isn't already being displayed
				if (!markerMap.containsValue(place)) {
					// Add the Marker to the Map and keep track of it
					markerMap.put(mMap.addMarker(new MarkerOptions().position(
							new LatLng(place.getLat(), place.getLng())).icon(
							BitmapDescriptorFactory.fromResource(Type
									.getResFromType(place.getType())))), place);
				}
			}
		}
	}

	private String[] getCategories() {
		ArrayList<String> tmp = new ArrayList<String>();
		for (Filter filter : filterList) {
			if (filter.isSelected()) {
				tmp.add(filter.getName());
			}
		}

		return tmp.toArray(new String[tmp.size()]);
	}

	private void populateFilterList() {
		filterList = new ArrayList<Filter>();
		for (String type : datasource.getTypes()) {
			filterList.add(new Filter(type, true));
		}
	}

	@Override
	protected void onResume() {
		datasource.openReadable();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.filter_item:
			showFilterDialog();
			return true;
		case R.id.loc_item:
			getLocation();
			return true;
		case R.id.near_item:
			showRadiusDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showFilterDialog() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(R.string.filter);

		LayoutInflater inflater = getLayoutInflater();
		final View PopupLayout = inflater
				.inflate(R.layout.filters_dialog, null);
		dialogBuilder.setView(PopupLayout);

		final AlertDialog dialog = dialogBuilder.create();
		dialog.show();

		ListView listView = (ListView) PopupLayout
				.findViewById(R.id.listFilters);

		final FilterListAdapter filterListAdapter = new FilterListAdapter(
				getLayoutInflater(), filterList);

		listView.setAdapter(filterListAdapter);

		((Button) PopupLayout.findViewById(R.id.okButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						updateMarkers();
						dialog.dismiss();
					}
				});
		((Button) PopupLayout.findViewById(R.id.selectAllButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						for (Filter filter : filterList) {
							filter.setSelected(true);
						}
						filterListAdapter.notifyDataSetChanged();
					}
				});

		((Button) PopupLayout.findViewById(R.id.deselectAllButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						for (Filter filter : filterList) {
							filter.setSelected(false);
						}
						filterListAdapter.notifyDataSetChanged();

					}
				});

	}

	private ArrayList<Place> getPlacesWithinRadius(int radius) {
		ArrayList<Place> places = new ArrayList<Place>();

		if (locMarker != null) {
			double lat = locMarker.getPosition().latitude;
			double lng = locMarker.getPosition().longitude;
			float[] distance = new float[2];

			for (Entry<Marker, Place> entry : markerMap.entrySet()) {
				if (entry.getKey() == locMarker)
					continue;

				Place place = entry.getValue();
				Location.distanceBetween(place.getLat(), place.getLng(), lat,
						lng, distance);
				if (distance[0] <= radius) {
					places.add(place);
				}
			}
		}
		return places;
	}

	private void showRadiusDialog() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(R.string.near_you);

		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.radius_dialog, null);
		dialogBuilder.setView(layout);

		final AlertDialog dialog = dialogBuilder.create();
		dialog.show();

		final TextView radiusValue = (TextView) layout
				.findViewById(R.id.radiusValue);

		ListView listView = (ListView) layout.findViewById(R.id.listPlaces);

		final ArrayList<Place> localPlaces = new ArrayList<Place>();
		final ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this,
				android.R.layout.simple_list_item_1, localPlaces);

		listView.setAdapter(adapter);

		final SeekBar seekRadius = (SeekBar) layout
				.findViewById(R.id.seekRadius);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				dialog.dismiss();
				Marker marker = getMarkerFromPlace(localPlaces.get(position));
				mMap.animateCamera(CameraUpdateFactory.newLatLng(marker
						.getPosition()));
				marker.showInfoWindow();
			}
		});
		seekRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int current = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				current = progress;
				radiusValue.setText("" + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				localPlaces.clear();
				for (Place place : getPlacesWithinRadius(current)) {
					localPlaces.add(place);
				}
				adapter.notifyDataSetChanged();

			}
		});

		((Button) layout.findViewById(R.id.okButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

	}

	private Marker getMarkerFromPlace(Place place) {
		Marker marker = null;
		for (Entry<Marker, Place> entry : markerMap.entrySet()) {
			if (entry.getValue() == place) {
				marker = entry.getKey();
			}
		}
		return marker;
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
				Toast.makeText(this, getString(R.string.error_map),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
