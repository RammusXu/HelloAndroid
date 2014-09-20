package com.example.hellorammus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HelloGameActivity extends Activity {

	private RelativeLayout _relataivelayout;
	private HelloGameView gameView;
	private ImageView iv;

	public static String DAVID_AD_START = "DAVID_AD_START";
	public static String DAVID_AD_END = "DAVID_AD_END";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(new HelloGameView(HelloGameActivity.this));
		setContentView(R.layout.hello_game);

		init();
		initAd();

		iv.setVisibility(View.VISIBLE);
		iv.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				iv.setVisibility(View.GONE);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://hami.emome.net"));
				try {
					startActivity(intent);
				} catch (Exception e) {
				}
			}
		});

	}

	private void initAd() {
		Intent intent = new Intent(HelloGameActivity.this, MyAdService.class);
		startService(intent);

	}

	private void init() {
		_relataivelayout = (RelativeLayout) findViewById(R.id.hello_game_relativeLayout);

		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);

		gameView = new HelloGameView(HelloGameActivity.this);
		_relataivelayout.addView(gameView, relativeParams);

		iv = new ImageView(HelloGameActivity.this);
		iv.setImageResource(R.drawable.banner);// TODO change it

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		iv.setLayoutParams(lp);

		relativeParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);
		relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		_relataivelayout.addView(iv, relativeParams);

	}

	private BroadcastReceiver recevier = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DAVID_AD_START)) {
				iv.setVisibility(View.VISIBLE);
			} else if (intent.getAction().equals(DAVID_AD_END)) {
				iv.setVisibility(View.GONE);
			}

		}

	};

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(DAVID_AD_START);
		filter.addAction(DAVID_AD_END);
		registerReceiver(recevier, filter);

		super.onResume();
	}

	@Override
	protected void onPause() {

		Intent intent = new Intent(HelloGameActivity.this, MyAdService.class);
		stopService(intent);
		super.onPause();
	}

}
