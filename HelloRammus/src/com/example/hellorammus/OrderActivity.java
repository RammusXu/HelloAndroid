package com.example.hellorammus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OrderActivity extends Activity {
	private TextView tv;
	private CheckBox cb1, cb2;
	private RadioGroup rg;
	private RadioButton rb1, rb2;
	private Button btn;
	private ImageView iv1, iv2;

	private NotificationManager nm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_activity);

		init();
	}

	private String cheese, vegetable, rbText;

	private void init() {
		nm = (NotificationManager) OrderActivity.this.getApplication()
				.getSystemService(Service.NOTIFICATION_SERVICE);
		
		

		tv = (TextView) findViewById(R.id.order_textView1);
		cb1 = (CheckBox) findViewById(R.id.order_checkBox1);
		cb2 = (CheckBox) findViewById(R.id.order_checkBox2);
		rg = (RadioGroup) findViewById(R.id.order_radioGroup1);
		rb1 = (RadioButton) findViewById(R.id.order_radio0);
		rb2 = (RadioButton) findViewById(R.id.order_radio1);
		btn = (Button) findViewById(R.id.order_button1);
		iv1 = (ImageView) findViewById(R.id.order_imageView4);
		iv2 = (ImageView) findViewById(R.id.order_imageView2);

		cheese = getString(R.string.order_cheese);
		vegetable = getString(R.string.order_vegetable);

		cb1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				iv1.setVisibility(isChecked ? 1 : 0);
				if (isChecked) {
					cheese = getString(R.string.order_cheese);
					iv1.setVisibility(View.VISIBLE);
				} else {
					cheese = "No " + getString(R.string.order_cheese);
					iv1.setVisibility(View.GONE);
				}
				tv.setText(cheese + "," + vegetable);
			}
		});

		cb2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				iv2.setVisibility(isChecked ? 1 : 0);
				if (isChecked) {
					vegetable = getString(R.string.order_vegetable);
					iv2.setVisibility(View.VISIBLE);
				} else {
					vegetable = "No " + getString(R.string.order_vegetable);
					iv2.setVisibility(View.GONE);
				}
				tv.setText(cheese + "," + vegetable);

			}
		});

		rbText = getString(R.string.order_stay_in);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == rb1.getId()) {
					rbText = rb1.getText().toString();
				} else if (checkedId == rb2.getId()) {
					rbText = rb2.getText().toString();
				}
			}

		});

		btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(OrderActivity.this)
						.setTitle(R.string.order_title)
						.setMessage(tv.getText().toString() + "\n" + rbText)
						.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								int intNotificationId = 123;
								
								//	Intent, Bundle, PendingIntent
								Intent intent = new Intent(OrderActivity.this,
										MyNotificationHandler.class);
								Bundle extras = new Bundle();
								extras.putInt("KEY_NOTI_ID", intNotificationId);
								intent.putExtras(extras);
								PendingIntent pi = PendingIntent.getActivity(OrderActivity.this, 1,
										intent, PendingIntent.FLAG_CANCEL_CURRENT);

								//	new Notification()
								NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderActivity.this);
								builder.setSmallIcon(R.drawable.itune);
								builder.setTicker("Your order is received");
								builder.setContentTitle("Your order");
								builder.setContentText("Order No. is 123");
								builder.setContentIntent(pi);
								
								nm.notify(intNotificationId, builder.build());
							}
						}).show();
			}
		});
	}
}
