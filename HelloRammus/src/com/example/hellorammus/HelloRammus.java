package com.example.hellorammus;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HelloRammus extends Activity {
	private static final String TAG = "HelloRammus";

	private static final int LOGIN_CHECK = 1;
	private static final int LOGIN_CHECK_RETURN = 222;
	private static final String LOGIN_SUCCESS = "111";

	public static final String PREF = "pref";

	private static final String SEND_BOARDCAST = "SEND_BOARDCAST";

	private TextView tv;
	private EditText et1, et2;
	private Button btn, btnHome, btnBMI, btnAbout, btnSend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		init();

	}
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.d(TAG, "mBroadcastReceiver onReceive");
//			Intent newintent = new Intent(HelloRammus.this, HelloBMI.class);
//			startActivity(newintent);
		}
		
	};

	private void init() {
		setContentView(R.layout.login);

		tv = (TextView) findViewById(R.id.login_textView1);
		et1 = (EditText) findViewById(R.id.login_editText1);
		et2 = (EditText) findViewById(R.id.login_editText2);
		btn = (Button) findViewById(R.id.login_button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setButtonLogin();
			}
		});

		btnHome = (Button) findViewById(R.id.navigation_buttonHome);
		btnHome.setEnabled(false);

		btnBMI = (Button) findViewById(R.id.navigation_buttonBMI);
		btnBMI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setClass(HelloRammus.this, HelloBMI.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});

		btnAbout = (Button) findViewById(R.id.navigation_buttonAbout);
		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setClass(HelloRammus.this, AboutActivity.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});
		

		btnSend = (Button) findViewById(R.id.login_btn_send);
		btnSend.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "btnSend onClick");
				try {
					Intent intent = new Intent(SEND_BOARDCAST);
					HelloRammus.this.sendBroadcast(intent);
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});

		tv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				et1.setText("rammus");
				et2.setText("1234");
				return false;
			}
		});
	}

	private void setButtonLogin() {
		Intent intent = new Intent();
		intent.setClass(HelloRammus.this, LoginCheck.class);
		// intent.setClassName("com.example.logincheck",
		// "com.example.logincheck.LoginCheck");

		Bundle b = new Bundle();
		b.putString("KEY_ID", et1.getText().toString());
		b.putString("KEY_PWD", et2.getText().toString());
		intent.putExtras(b);
		try {
			startActivityForResult(intent, LOGIN_CHECK);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		SharedPreferences sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor se = sp.edit();
		
		if (requestCode == LOGIN_CHECK) {
			if (resultCode == LOGIN_CHECK_RETURN) {
				if (data != null) {
					if (data.getExtras().getString("KEY_RESULT")
							.equalsIgnoreCase(LOGIN_SUCCESS)) {
						se.putBoolean("KEY_IF_LOGIN", true);
						
						tv.setText(getResources().getString(
								R.string.hello_rammus_login_ok));
						tv.setTextColor(getResources().getColor(
								R.color.col_blue));

					} else {
						se.putBoolean("KEY_IF_LOGIN", false);

						tv.setText(getString(R.string.hello_rammus_login_fail));
						tv.setTextColor(getResources()
								.getColor(R.color.col_red));
					}
				}
			}
		}
		se.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.hello_rammus, menu);

		// MenuItem mi = menu.findItem(R.id.menu_about);
		// Intent intent = new Intent(HelloRammus.this, AboutActivity.class);
		// mi.setIntent(intent);
		// mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
		// {
		// @Override
		// public boolean onMenuItemClick(MenuItem item) {
		// finish();
		// return false;
		// }
		// });

		initDeputy();

		return super.onCreateOptionsMenu(menu);
	}

	private ArrayList<String> deputiesList = new ArrayList<String>();
	private String[] deputies;
	private boolean[] checkedItems = null;

	private void initDeputy() {
		deputiesList.add("David");
		deputiesList.add("Rammus");
		deputiesList.add("Ken");
		deputiesList.add("Mary");

		deputies = deputiesList.toArray(new String[deputiesList.size()]);
		checkedItems = new boolean[deputiesList.size()];
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_deputy:
			new AlertDialog.Builder(HelloRammus.this)
					.setTitle(R.string.dialog_deputy)
					.setMultiChoiceItems(deputies, checkedItems,
							new DialogInterface.OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									checkedItems[which] = isChecked;
								}
							})
					.setPositiveButton(R.string.dialog_pos,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									StringBuilder sb = new StringBuilder();
									for (int i = 0; i < deputiesList.size(); i++) {
										if (checkedItems[i]) {
											sb.append(deputiesList.get(i)
													+ "\n");
										}
									}

									tv.setText(sb.toString());

								}
							})
					.setNegativeButton(R.string.dialog_neg,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();

			break;
		case R.id.menu_about:
			LayoutInflater li = LayoutInflater.from(HelloRammus.this);
			View v = li.inflate(R.layout.about_dialog, null);

			final Dialog aboutDialog = new Dialog(HelloRammus.this);
			aboutDialog.setTitle("Version");
			aboutDialog.setContentView(v);

			TextView textView = (TextView) v
					.findViewById(R.id.about_dialog_textView1);
			try {
				String s = getPackageManager().getPackageInfo(getPackageName(),
						0).versionName;
				textView.setText(s);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

			Button button = (Button) v.findViewById(R.id.about_dialog_button1);
			button.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					aboutDialog.dismiss();
				}
			});

			aboutDialog.show();

			break;
		case R.id.menu_order:
			Intent intent = new Intent(HelloRammus.this, OrderActivity.class);
			startActivity(intent);

			break;
		case R.id.menu_setting:

			break;
		case R.id.menu_exit:
			new AlertDialog.Builder(HelloRammus.this)
					.setTitle(R.string.dialog_title)
					.setMessage(R.string.dialog_message)
					.setPositiveButton(R.string.dialog_pos,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();

								}
							})
					.setNegativeButton(R.string.dialog_neg,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
		SharedPreferences sp = HelloRammus.this.getSharedPreferences(PREF, Context.MODE_PRIVATE);
		et1.setText(sp.getString("KEY_ID", ""));
		
		IntentFilter filter = new IntentFilter(SEND_BOARDCAST);
		HelloRammus.this.registerReceiver(mBroadcastReceiver, filter);
		
	}

	@Override
	protected void onPause() {
		Log.v(TAG, "onPause");
		SharedPreferences sp = HelloRammus.this.getSharedPreferences(PREF,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor se = sp.edit();
		se.putString("KEY_ID", et1.getText().toString());
		se.commit();
		
		HelloRammus.this.unregisterReceiver(mBroadcastReceiver);
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}
}
