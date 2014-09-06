package com.example.hellorammus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginOK extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_ok);

		Intent intent = this.getIntent();

		TextView tv = (TextView) findViewById(R.id.login_ok_textView1);
		tv.setText(getString(R.string.login_ok_welcome) + " "
				+ intent.getStringExtra("KEY_ID"));

		Button btn = (Button) findViewById(R.id.bmi_button1);
		btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences(HelloRammus.PREF,
						Context.MODE_PRIVATE);
				SharedPreferences.Editor se = sp.edit();
				se.remove("KEY_ID");
				se.remove("KEY_IF_LOGIN");
				se.commit();
				try {
					Intent intent = new Intent(LoginOK.this, HelloRammus.class);
					startActivity(intent);
				} catch (Exception e) {
				}
				finish();

			}

		});
	}

}
