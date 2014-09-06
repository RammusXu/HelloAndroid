package com.example.hellorammus;

import android.app.Activity;
import android.os.Bundle;

public class HelloGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new HelloGameView(HelloGameActivity.this));
	}

}
