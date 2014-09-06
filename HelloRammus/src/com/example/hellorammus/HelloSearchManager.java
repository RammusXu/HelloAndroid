package com.example.hellorammus;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloSearchManager extends Activity {

	private static final String TAG = null;
	private static final String DATA = "DATA";

	// UI
	Button btn1;
	TextView tv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_search_manager);

		tv1 = (TextView) findViewById(R.id.hello_search_manager_textView1);
		
		btn1 = (Button) findViewById(R.id.hello_search_manager_button1);
		btn1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				onSearchRequested();
			}
		});
	}

	@Override
	public boolean onSearchRequested() {
		String data = "Rammus";
		Bundle bundle = new Bundle();
		bundle.putString(DATA, data);
		startSearch(data, false, bundle, false);
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) { if(intent.getAction().toString().equalsIgnoreCase(Intent.ACTION_SEARCH))
    {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
	      if(bundle!=null)
	      {
	        tv1.setText(bundle.getString("data"));
	      }
	      else
	      {
	        tv1.setText("no data");
	      }
	      Log.i(TAG, "from search dialog, query text= "+query);
	      //findContact(query); //註解的這一段即可實作真實查詢的結果
	    }

		super.onNewIntent(intent);
	}

}
