package com.example.hellorammus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HelloSQLite extends Activity {
	// DB
	private SQLiteDatabase db;
	private String DBNAME = "mydb1";
	private String TABLENAME = "t_meal";
	private String FIELD01_NAME = "f_id";
	private String FIELD02_NAME = "f_name";
	private String CREATE_SQL = "create table if not exists " + TABLENAME
			+ " (" + FIELD01_NAME + " integer primary key autoincrement, "
			+ FIELD02_NAME + " varchar not null);";

	// UI
	private Button btn1, btn2, btn3;
	private TextView tv1;
	private EditText ed1;
	private ListView lv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_sqlite);

		findView();

		init();
		queryDB();
	}

	private void init() {
		db = HelloSQLite.this.openOrCreateDatabase(DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL(CREATE_SQL);
	}

	private void findView() {

		btn1 = (Button) findViewById(R.id.hello_sqlite_button1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertDB();
				hideSoftKeyboard();
				queryDB();
			}
		});
		btn2 = (Button) findViewById(R.id.hello_sqlite_button2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				queryDB();
			}
		});
		btn3 = (Button) findViewById(R.id.hello_sqlite_button3);
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resetDB();
				queryDB();
			}
		});

		tv1 = (TextView) findViewById(R.id.hello_sqlite_textView1);
		ed1 = (EditText) findViewById(R.id.hello_sqlite_editText1);
		lv1 = (ListView) findViewById(R.id.hello_sqlite_listView1);
	}

	private void insertDB() {
		if (ed1.getText().length() > 0) {
			if (!queryIfDataExist(ed1.getText().toString())) {
				db.execSQL("INSERT INTO " + TABLENAME + "('" + FIELD02_NAME
						+ "') values ('" + ed1.getText().toString() + "');");
			} else {
				Toast.makeText(getApplicationContext(), "already exist",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean queryIfDataExist(String strMealName) {
		String sql = "SELECT f_id,f_name FROM " + TABLENAME + " WHERE f_name='"
				+ strMealName + "'; ";
		Cursor c = db.rawQuery(sql, null);
		if (c.getCount() > 0) {
			return true;
		}
		return false;
	}

	ArrayList<String> lstId;
	ArrayList<String> lstName;

	private void queryDB() {
		String sql = "SELECT f_id,f_name FROM " + TABLENAME + " ;";
		Cursor c = db.rawQuery(sql, null);
		lstId = new ArrayList<String>();
		lstName = new ArrayList<String>();
		if (c != null) {
			if (c.getCount() > 0) {

				c.moveToFirst();
				do {
					lstId.add(c.getString(c.getColumnIndex("f_id")));
					lstName.add(c.getString(c.getColumnIndex("f_name")));
				} while (c.moveToNext());
			}
			updateListView();
		}
	}

	private void resetDB() {
		String sql = "DELETE FROM " + TABLENAME + " ;";
		db.execSQL(sql);

	}

	private void updateListView() {
		lv1.setAdapter(new ArrayAdapter<String>(HelloSQLite.this,
				android.R.layout.simple_list_item_1, lstName));
		lv1.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), lstId.get(position),
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void hideSoftKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		if (ed1.getText().length() > 0) {
			inputManager.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
		}
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

}
