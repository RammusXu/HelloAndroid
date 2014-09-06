package com.example.hellorammus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoginCheck extends Activity {
	private static final int LOGIN_CHECK_RETURN = 222;
	private static final String LOGIN_SUCCESS = "111";
	private static final String LOGIN_FAIL = "999";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle b = LoginCheck.this.getIntent().getExtras();

		Intent intent = new Intent();
		Bundle returnBundle = new Bundle();

		if (b != null) {
			if (b.getString("KEY_ID") != null) {
				if (b.getString("KEY_ID").equalsIgnoreCase("Rammus")
						&& b.getString("KEY_PWD").equalsIgnoreCase("1234")) {
					returnBundle.putString("KEY_RESULT", LOGIN_SUCCESS);
				} else {
					returnBundle.putString("KEY_RESULT", LOGIN_FAIL);
				}
				intent.putExtras(returnBundle);
				setResult(LOGIN_CHECK_RETURN, intent);
				finish();
			}
		}
	}
}
