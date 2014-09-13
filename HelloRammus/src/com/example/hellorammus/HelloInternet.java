package com.example.hellorammus;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class HelloInternet extends Activity {
	protected static final String TAG = "HelloInternet";
	private TextView tv1;
	private EditText ed1;
	private Button btn1;
	private ListView lv;
	
	
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_internet);

		init();
	}

	private void init() {
		tv1 = (TextView) findViewById(R.id.hello_internet_textView2);
		ed1 = (EditText) findViewById(R.id.hello_internet_editText1);
		btn1 = (Button) findViewById(R.id.hello_internet_button1);
		lv = (ListView) findViewById(R.id.hello_internet_listView1);

		ed1.setText("2498");
		btn1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pd != null)
					if (pd.isShowing())
						pd.dismiss();
				pd = new ProgressDialog(HelloInternet.this);
				pd.setTitle(getString(R.string.str_loading));
				pd.setMessage(getString(R.string.str_plz_wait));
				pd.show();
				new Thread(fetchRunnable).start();
			}
		});
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (pd != null) {
				if (pd.isShowing()) {

					pd.dismiss();
				}
			}
			switch (msg.what) {
			case 111:
				if (msg.obj != null) {
					tv1.setText(msg.obj.toString());
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						if (ja != null && ja.length() > 0) {
							JSONObject jo = null;
							ArrayList<String> lst = new ArrayList<String>();
							tv1.setText("total:" + ja.length());
							for (int i = 0; i < ja.length(); i++) {
								jo = ja.getJSONObject(i);
								if (jo != null) {
									lst.add(jo.getString("name") + "\n"
											+ jo.getString("area") + "\n"
											+ jo.getString("address"));
								}
							}
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									HelloInternet.this,
									android.R.layout.simple_list_item_1, lst);
							lv.setAdapter(adapter);
						}
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}

				}

				break;

			case 999:
				if (msg.obj != null) {
					tv1.setText(msg.obj.toString());
				}
				break;

			}

			super.handleMessage(msg);
		}

	};

	private Runnable fetchRunnable = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			try {
				HippoWebService conn = new HippoWebService();

				String api = "http://finance.yahoo.com/d/quotes.csv?s="
						+ ed1.getText().toString() + ".TW&f=snd1l1c6";
				
				String api2 = "http://data.ntpc.gov.tw/NTPC/od/data/api/1080800356/?$format=json";

				String ret = conn.getMethod(api2, "utf-8");
				if (ret != null) {
					msg.what = 111;
					msg.obj = ret;
				} else {
					msg.what = 999;
					msg.obj = "Error";
				}
			} catch (Exception e) {
				msg.what = 999;
				msg.obj = e.toString();
			}
			handler.sendMessage(msg);
		}

	};

}
