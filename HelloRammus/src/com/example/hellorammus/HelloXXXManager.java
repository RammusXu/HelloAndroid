package com.example.hellorammus;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HelloXXXManager extends Activity {
	private static final String TAG = "HelloXXXManager";

	private Vibrator mVibrator;
	private AudioManager am;

	// UI
	private ToggleButton mToggleButton;
	private Button btnUp, btnDown;
	private TextView tv1;

	private LocationManager lm;
	private boolean bIfGPSEnable = false;
	private boolean bIfNetworkEnable = false;

	// Create member variables for locations
	private boolean canGetLocation;
	private Location currentLocation;
	private double latitude; // latitude
	private double longitude; // longitude
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_xxx_manager);

		lm = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);


		mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
		mToggleButton = (ToggleButton) findViewById(R.id.hello_xxx_manager_toggleButton1);
		mToggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mVibrator.vibrate(new long[] { 100, 200, 300, 500, 50 }, 1);
				} else {
					mVibrator.cancel();
				}

			}
		});

		am = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);

		tv1 = (TextView) findViewById(R.id.hello_xxx_manager_textView1);

		btnUp = (Button) findViewById(R.id.hello_xxx_manager_buttonup);
		btnUp.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				tv1.setText("music volumn=" + am.getStreamVolume(AudioManager.STREAM_MUSIC));
			}
		});

		btnDown = (Button) findViewById(R.id.hello_xxx_manager_buttondown);
		btnDown.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				tv1.setText("music volumn=" + am.getStreamVolume(AudioManager.STREAM_MUSIC));
			}
		});

		//	INIT
		initLocation();
	}

	private void initLocation() {
		if (!bIfGPSEnable && !bIfNetworkEnable) {
			// no network provider is enabled
		} else {
			this.canGetLocation = true;
			// First get location from Network Provider
			if (bIfNetworkEnable) {
				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, locatinListener);

				if (lm != null) {
					currentLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (currentLocation != null) {
						latitude = currentLocation.getLatitude();
						longitude = currentLocation.getLongitude();
					}
				}
			}
			// if GPS Enabled get lat/long using GPS Services
			if (bIfGPSEnable) {
				if (currentLocation == null) {
					lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, locatinListener);

					if (lm != null) {
						currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (currentLocation != null) {
							latitude = currentLocation.getLatitude();
							longitude = currentLocation.getLongitude();
						}
					}
				}
			}
		}
		Log.i(TAG, "latitude=" + latitude);
		Log.i(TAG, "longitude=" + longitude);
		tv1.setText(latitude + "," + longitude);
	}

	private LocationListener locatinListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			currentLocation = location;
			latitude = currentLocation.getLatitude();
			longitude = currentLocation.getLongitude();
			Log.i(TAG, "latitude=" + latitude);
			Log.i(TAG, "longitude=" + longitude);
		}
	};

	public String getAddressByLocation(Context context, Location location) {
		String strAddress = "";
		try {
			if (location != null) {
				double dLat = location.getLatitude();
				double dLng = location.getLongitude();

				Geocoder gc = new Geocoder(context, Locale.getDefault());
				try {
					List<Address> lstAddress = gc.getFromLocation(dLat, dLng, 1);
					StringBuilder sb = new StringBuilder();

					if (lstAddress.size() > 0) {
						Address adsLocation = lstAddress.get(0);
						for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++) {
							// sb.append(adsLocation.getAddressLine(i)).append("\n");
						}
						// sb.append(adsLocation.getLocality()).append(" ");
						sb.append(adsLocation.getAddressLine(0)).append(" ");
						// sb.append(adsLocation.getPostalCode()).append(" ");
						// sb.append(adsLocation.getCountryName());
					}
					strAddress = sb.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return strAddress;
	}

}
