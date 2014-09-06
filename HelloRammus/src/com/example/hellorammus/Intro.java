package com.example.hellorammus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Intro extends Activity {
	private ImageView iv;
	private AnimationDrawable anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.intro);

		iv = (ImageView) findViewById(R.id.intro_imageView1);
		anim = (AnimationDrawable) getResources()
				.getDrawable(R.anim.animation1);
		iv.setImageDrawable(anim);
		iv.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intro.this, HelloRammus.class);
				startActivity(intent);
				finish();
			}

		});

		startAnimation();
	}

	private void startAnimation() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (anim.isRunning()) {
					anim.stop();
					anim.start();
				} else {
					anim.start();
				}
			}
		}, 200);
	}

}
