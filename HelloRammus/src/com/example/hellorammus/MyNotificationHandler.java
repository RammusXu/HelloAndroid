package com.example.hellorammus;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

public class MyNotificationHandler extends Activity {
	private NotificationManager nm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			try {
				nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.cancel(b.getInt("KEY_NOTI_ID"));
			} catch (Exception e) {
				
			}
		}
	}

}
