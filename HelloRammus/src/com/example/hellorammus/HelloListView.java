package com.example.hellorammus;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class HelloListView extends Activity {
	protected static final String TAG = "HelloListView";
	private ListView lv;
	private ArrayList<String> lst1;
	private ArrayList<HashMap<String, String>> lst2 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, Object>> lst3 = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_listview);

		init();
		initSpinner();
	}

	private void init() {
		lv = (ListView) findViewById(R.id.hello_listview_listView1);

		// SET lst1
		lst1 = new ArrayList<String>();
		lst1.add("David");
		lst1.add("Rick");
		lst1.add("Jay");

		// SET lst2
		for (int i = 0; i < lst1.size(); i++) {
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("item1", lst1.get(i)); // key, value (上方文字)
			item.put("item2", lst1.get(i) + "(small text)"); // key, value
																// (下方文字)
			lst2.add(item);
		}

		// SET lst3
		for (int i = 0; i < lst1.size(); i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("pic1", R.drawable.itune);
			item.put("item1", lst1.get(i));
			item.put("item2", lst1.get(i));
			lst3.add(item);
		}

		lv.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// lst1.get(position) = David, Rick or Jay，可搭配AlertDialog顯示之
				// 關於 Toast 可參考p.89
				Toast.makeText(HelloListView.this, lst1.get(position),
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private SimpleAdapter simpleAdapter;

	private void initSpinner() {
		ArrayList<String> lstSpinner = new ArrayList<String>();
		lstSpinner.add("ListView Type 1");
		lstSpinner.add("ListView Type 2");
		lstSpinner.add("ListView Type 3");
		lstSpinner.add("ListView Type 4");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				HelloListView.this, android.R.layout.simple_spinner_item,
				lstSpinner);

		Spinner sp1 = (Spinner) findViewById(R.id.hello_listview_spinner1);
		sp1.setAdapter(adapter);
		sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							HelloListView.this,
							android.R.layout.simple_list_item_1, lst1 // (*註1)
					);

					lv.setAdapter(adapter);

					Log.d(TAG, "position = " + position);
					break;
				case 1:
					simpleAdapter = new SimpleAdapter(
							HelloListView.this,
							lst2,
							android.R.layout.simple_list_item_2,
							new String[] { "item1", "item2" }, // key的名字
							new int[] { android.R.id.text1, android.R.id.text2 } // show在哪個textView
																					// id
					);
					lv.setAdapter(simpleAdapter);

					Log.d(TAG, "position = " + position);
					break;
				case 2:
					simpleAdapter = new SimpleAdapter(HelloListView.this, lst2,
							R.layout.simple_list_item_2, new String[] {
									"item1", "item2" }, // key的名字
							new int[] { R.id.simple_list_item_2_textView1,
									R.id.simple_list_item_2_textView2 });
					lv.setAdapter(simpleAdapter);

					Log.d(TAG, "position = " + position);
					break;
				case 3:
					simpleAdapter = new SimpleAdapter(HelloListView.this, lst3,
							R.layout.simple_list_item_3, new String[] { "pic1",
									"item1", "item2" }, // key的名字
							new int[] { R.id.simple_list_item_3_imageView1,
									R.id.simple_list_item_3_textView1,
									R.id.simple_list_item_3_textView2 });
					lv.setAdapter(simpleAdapter);

					Log.d(TAG, "position = " + position);
					break;

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}
}
