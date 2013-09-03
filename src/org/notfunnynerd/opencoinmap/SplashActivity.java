package org.notfunnynerd.opencoinmap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.notfunnynerd.opencoinmap.models.Place;
import org.notfunnynerd.opencoinmap.models.PlacesDataSource;
import org.notfunnynerd.opencoinmap.utils.DataParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity implements OnClickListener {

	private ProgressDialog mProgressDialog;
	private PlacesDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		((Button) findViewById(R.id.button1)).setOnClickListener(this);
		datasource = new PlacesDataSource(this);
		datasource.open();
		datasource.deleteAllPlaces();
		updateContent();

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			startActivity(new Intent(SplashActivity.this, MapActivity.class));
			break;
		default:
			break;
		}
	}

	private void updateContent() {
		mProgressDialog = new ProgressDialog(SplashActivity.this);
		mProgressDialog.setMessage(getResources().getString(
				R.string.downloading));
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		RefreshDbTask refreshDb = new RefreshDbTask();
		refreshDb.execute(getResources().getString(R.string.data_url));
	}

	public class RefreshDbTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			String content = null;

			try {
				URL url = new URL(urls[0]);
				URLConnection connection = url.openConnection();
				connection.connect();

				int fileLength = connection.getContentLength();

				// download the file
				InputStream input = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					publishProgress((int) (total * 100 / fileLength));
					baos.write(data, 0, count);
				}

				baos.flush();
				baos.close();

				content = baos.toString("UTF-8");

				Log.i("Download", content);
				input.close();
			} catch (Exception e) {
				Log.e("Download", e.getMessage());
			}
			return content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String lineSep = System.getProperty("line.separator");
			String[] data = result.split(lineSep);
			for (String raw : data) {
				Place place = DataParser.getPlaceFromRaw(raw);
				if (place != null) {
					datasource.createPlace(place);
				}
			}

			mProgressDialog.cancel();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			mProgressDialog.setProgress(progress[0]);
		}
	}

}
