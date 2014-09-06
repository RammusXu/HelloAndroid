package com.example.hellorammus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HelloBMI extends Activity {
	protected static final String TAG = "HelloBMI";
	private static final int BMI_CALCULATE = 123;
	private static final int BMI_RETURN = 111;

	private TextView tv1, tv2;
	private EditText et1, et2;
	private Button btn, btnHome, btnBMI, btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmi);

		tv1 = (TextView) findViewById(R.id.bmi_textView3);
		tv2 = (TextView) findViewById(R.id.bmi_textView4);

		et1 = (EditText) findViewById(R.id.bmi_editText1);
		et2 = (EditText) findViewById(R.id.bmi_editText2);

		et1.setText("190");
		et2.setText("75");

		btn = (Button) findViewById(R.id.bmi_button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {

					Intent intent = new Intent();
					intent.setClass(HelloBMI.this, BMICheck.class);
					Bundle b = new Bundle();
					b.putDouble("KEY_HEIGHT",
							Double.valueOf(et1.getText().toString()) / 100);
					b.putDouble("KEY_WEIGHT",
							Double.valueOf(et2.getText().toString()));
					intent.putExtras(b);
					startActivityForResult(intent, 123);

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}

			}
		});

		btnHome = (Button) findViewById(R.id.navigation_buttonHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(getApplicationContext(),
							HelloRammus.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});

		btnBMI = (Button) findViewById(R.id.navigation_buttonBMI);
		btnBMI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(getApplicationContext(),
							HelloBMI.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});
		btnBMI.setEnabled(false);

		btnAbout = (Button) findViewById(R.id.navigation_buttonAbout);
		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(getApplicationContext(),
							AboutActivity.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case BMI_CALCULATE:
			switch (resultCode) {
			case BMI_RETURN:
				if (data != null) {
					tv1.setText(getString(R.string.label_bmi)
							+ data.getExtras().getString("KEY_RESULT_BMI"));
					tv2.setText(getString(R.string.label_BMIresult)
							+ data.getExtras().getString("KEY_RESULT"));
				}
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}
	}

}
