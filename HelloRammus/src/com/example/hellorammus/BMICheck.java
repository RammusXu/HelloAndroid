package com.example.hellorammus;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BMICheck extends Activity {
	private static final int BMI_RETURN = 111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		Double h = intent.getExtras().getDouble("KEY_HEIGHT");
		Double w = intent.getExtras().getDouble("KEY_WEIGHT");
		DecimalFormat df = new DecimalFormat("0.00");
		Double bmi = Double.parseDouble(df.format(w / (h * h))) ;

		Bundle returnBundle = new Bundle();

		returnBundle.putString("KEY_RESULT_BMI", bmi.toString());
		if (bmi > 25) {
			returnBundle.putString("KEY_RESULT", "too heavy");
		} else if (bmi > 20){
			returnBundle.putString("KEY_RESULT", "nice body");
		} else {
			returnBundle.putString("KEY_RESULT", "too thin");
		}
		intent.putExtras(returnBundle);
		setResult(BMI_RETURN, intent);
		
		finish();
	}

}
