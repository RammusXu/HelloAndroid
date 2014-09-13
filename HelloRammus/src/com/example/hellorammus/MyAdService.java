package com.example.hellorammus;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyAdService extends Service {
	private static final String TAG = "MyAdService";
	private int _counter = 0;
	private boolean bIfShowAD = false;
	
	private Handler _handler = new Handler();
	private Runnable _runnable = new Runnable() {

		@Override
		public void run() {
			_counter++;
			Intent intent =  new Intent();
			
			if (_counter % 3 == 0) {
				if(!bIfShowAD) {
					Log.i(TAG, "3");
					intent = new Intent(HelloGameActivity.DAVID_AD_START);
					sendBroadcast(intent);
				}
				bIfShowAD = true;
			} else if (_counter % 7 == 0) {
				Log.i(TAG, "7");
				bIfShowAD = false;
				
				intent = new Intent(HelloGameActivity.DAVID_AD_END);
				sendBroadcast(intent);
			}
			_handler.postDelayed(_runnable, 1000);
		}
	};

	@Override
	public void onCreate() {
		_handler.postDelayed(_runnable, 1000);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		_handler.removeCallbacks(_runnable);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
