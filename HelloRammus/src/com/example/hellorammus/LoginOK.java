package com.example.hellorammus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginOK extends Activity {
	private WebView wv;
	private ProgressBar pb;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_ok);

		// init();
		initWebView();
	}

	private void initWebView() {
		pb = (ProgressBar) findViewById(R.id.login_ok_progressBar1);
		wv = (WebView) findViewById(R.id.login_ok_webView1);
		
		WebSettings ws = wv.getSettings();
		ws.setJavaScriptEnabled(true);

		wv.loadUrl("http://jumpin.cc/Android");
		wv.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				pb.setVisibility(View.VISIBLE);
				if (pd != null)
					if (pd.isShowing())
						pd.dismiss();
				pd = new ProgressDialog(LoginOK.this);
				pd.setTitle(getString(R.string.str_loading));
				pd.setMessage(getString(R.string.str_plz_wait));
				pd.show();

				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				pb.setVisibility(View.GONE);
				if (pd != null)
					if (pd.isShowing())
						pd.dismiss();
				super.onPageFinished(view, url);
			}

		});

		wv.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				pb.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}

		});
	}

	private void init() {
		Intent intent = this.getIntent();

		TextView tv = (TextView) findViewById(R.id.login_ok_textView1);
		tv.setText(getString(R.string.login_ok_welcome) + " "
				+ intent.getStringExtra("KEY_ID"));

		Button btn = (Button) findViewById(R.id.bmi_button1);
		btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences(HelloRammus.PREF,
						Context.MODE_PRIVATE);
				SharedPreferences.Editor se = sp.edit();
				se.remove("KEY_ID");
				se.remove("KEY_IF_LOGIN");
				se.commit();
				try {
					Intent intent = new Intent(LoginOK.this, HelloRammus.class);
					startActivity(intent);
				} catch (Exception e) {
				}
				finish();

			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			if (wv.canGoBack()) {
				wv.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
