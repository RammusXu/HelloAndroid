package com.example.hellorammus;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HelloXXXManager extends Activity {

	private Vibrator mVibrator;
	private AudioManager am;

	// UI
	private ToggleButton mToggleButton;
	private Button btnUp, btnDown;
	private TextView tv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_xxx_manager);
		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);
		mToggleButton = (ToggleButton) findViewById(R.id.hello_xxx_manager_toggleButton1);
		mToggleButton
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							mVibrator.vibrate(new long[] { 100, 200, 300, 500,
									50 }, 1);
						} else {
							mVibrator.cancel();
						}

					}
				});

		am = (AudioManager) getApplication().getSystemService(
				Context.AUDIO_SERVICE);
		

		tv1 = (TextView) findViewById(R.id.hello_xxx_manager_textView1);

		btnUp = (Button) findViewById(R.id.hello_xxx_manager_buttonup);
		btnUp.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				tv1.setText("music volumn="+am.getStreamVolume(AudioManager.STREAM_MUSIC));
			}
		});

		btnDown = (Button) findViewById(R.id.hello_xxx_manager_buttondown);
		btnDown.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				tv1.setText("music volumn="+am.getStreamVolume(AudioManager.STREAM_MUSIC));
			}
		});

	}

}
