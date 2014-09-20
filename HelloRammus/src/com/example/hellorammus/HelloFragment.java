package com.example.hellorammus;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class HelloFragment extends FragmentActivity {
	public String TAG = "HIPPO_DEBUG";
	public static int intScreenOrientation = 0;
	private LinearLayout layout1, layout2;
	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_fragment);

		init();
	}

	private void init() {
		btn1 = (Button) findViewById(R.id.hello_fragment_button1);
		btn2 = (Button) findViewById(R.id.hello_fragment_button2);
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				getSupportFragmentManager()
//						.beginTransaction()
//						.add(R.id.hello_fragment_linearLayout1,
//								new FragmentOne()).commit();
			}
		});
		btn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				getSupportFragmentManager()
//						.beginTransaction()
//						.add(R.id.hello_fragment_linearLayout2,
//								new FragmentTwo()).commit();
			}
		});

		layout1 = (LinearLayout) findViewById(R.id.hello_fragment_linearLayout1);
		layout2 = (LinearLayout) findViewById(R.id.hello_fragment_linearLayout2);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.hello_fragment_linearLayout1, new FragmentOne(),
						"fragmentOne").commit();
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.hello_fragment_linearLayout2, new FragmentTwo(),
						"fragmentTwo").commit();

		switch (getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			intScreenOrientation = Configuration.ORIENTATION_PORTRAIT;
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			intScreenOrientation = Configuration.ORIENTATION_LANDSCAPE;
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			intScreenOrientation = Configuration.ORIENTATION_PORTRAIT;
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			intScreenOrientation = Configuration.ORIENTATION_LANDSCAPE;
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
		}
		super.onConfigurationChanged(newConfig);
	}

}
