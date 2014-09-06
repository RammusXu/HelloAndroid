package com.example.hellorammus;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AboutActivity extends Activity {
	protected static final String TAG = "AboutActivity";
	private Button btnHome, btnBMI, btnAbout, btnChangeImage;
	private ImageView _iv;

	private AnimationSet animationSet = new AnimationSet(true);

	private ProgressBar pb;
	private int intCounter = 0;

	private ProgressDialog pd;
	private String strImrSrc1 = "http://goo.gl/dyTjN7";
	private String strImrSrc2 = "http://goo.gl/hTiAH0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		pb = (ProgressBar) findViewById(R.id.activity_about_progressBar1);
		pb.setProgress(0);
		pb.setVisibility(View.GONE);

		_iv = (ImageView) findViewById(R.id.activity_about_imageView1);
		action();

		initButtonChangeImage();
		initNavigation();
	}

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			intCounter++;
			if (intCounter <= 10) {
				loadingImageProgressBar(); // 變更進度條的進度 (參考下步驟 6)
				handler.postDelayed(runnable, 200); // 當counter小於等於10，自己把自己加入排程
			} else {
				handler.removeCallbacks(runnable); // 移除Runnable
			}
		}
	};

	private int intFlag = 1;

	@SuppressWarnings("deprecation")
	private void loadingImageProgressBar() {
		pb.setProgress(intCounter * 10); // 更新進度條
		// Log.i(TAG, "pb.getProgress()="+pb.getProgress());

		_iv.setAlpha(255 - intCounter * 25);

		if (pb.getProgress() >= 100) // 已達ProgressBar的終點
		{
			handler.removeCallbacks(runnable);
			pb.setVisibility(View.GONE);
			btnChangeImage.setEnabled(true);

			switch (intFlag) {
			case 1:
				_iv.setImageResource(R.drawable.kimi2);
				intFlag = 2;
				break;
			case 2:
				_iv.setImageResource(R.drawable.kimi1);
				intFlag = 1;
				break;
			}
			_iv.setAlpha(255);
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pd != null)
				if (pd.isShowing())
					pd.dismiss();
			btnChangeImage.setEnabled(true);
			switch (msg.what) {
			case 123:
				if (msg.obj != null) {
					Drawable d = (Drawable) msg.obj;
					_iv.setImageDrawable(d);
				}
				break;
			case 456:
				// fetch error
				break;
			}
			super.handleMessage(msg);
		}
	};

	private int intThreadType = 3;
	private void initButtonChangeImage() {
		btnChangeImage = (Button) findViewById(R.id.activity_about_button1);
		btnChangeImage.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnChangeImage.setEnabled(false);
				switch (intThreadType) {
				case 1:
					intCounter = 0;
					pb.setVisibility(View.VISIBLE);
					handler.postDelayed(runnable, 200);
					break;
				case 2:
					showPD();

					new Thread(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							Drawable drawable = null;
							try {
								switch (intFlag) {
								case 1:
									drawable = loadImageFromURL(strImrSrc1,
											strImrSrc1);
									intFlag = 2;
									break;
								case 2:
									drawable = loadImageFromURL(strImrSrc2,
											strImrSrc2);
									intFlag = 1;
									break;
								}
							} catch (Exception e) {
							}

							if (drawable != null) {
								msg.what = 123;
								msg.obj = drawable;
							} else {
								msg.what = 456;
								msg.obj = "Loading Image Error";
							}
							mHandler.sendMessage(msg);
						}
					}).start();
					break;

				case 3:
					showPD();
					switch (intFlag) {
					case 1:
						new MyAsyncTask(_iv).execute(strImrSrc1);
						intFlag = 2;
						break;
					case 2:
						new MyAsyncTask(_iv).execute(strImrSrc2);
						intFlag = 1;
						break;
					}
					break;
				}
			}
		});

	}

	protected void showPD() {
		if (pd != null)
			if (pd.isShowing())
				pd.dismiss();
		pd = new ProgressDialog(AboutActivity.this);
		pd.setTitle(getString(R.string.activity_about_pd_title));
		pd.setMessage(getString(R.string.activity_about_pd_message));
		pd.show();
	}

	public Drawable loadImageFromURL(String url, String name) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, name);
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, Drawable> {
		private ImageView imageView;

		public MyAsyncTask(ImageView iv) {
			this.imageView = iv;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			// params[0]
			Drawable d = loadImageFromURL(params[0], params[0]);
			return d;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			if (pd != null)
				if (pd.isShowing())
					pd.dismiss();
			btnChangeImage.setEnabled(true);
			this.imageView.setImageDrawable(result);
			super.onPostExecute(result);
		}

	}

	private void initNavigation() {
		btnHome = (Button) findViewById(R.id.navigation_buttonHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(getApplicationContext(),
							HelloRammus.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});

		btnBMI = (Button) findViewById(R.id.navigation_buttonBMI);
		btnBMI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(getApplicationContext(),
							HelloBMI.class);
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
					Intent intent = new Intent(getApplicationContext(),
							AboutActivity.class);
					startActivity(intent);
					finish();

				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});
		btnAbout.setEnabled(false);

	}

	protected void action() {
		int intAnimation = new Random().nextInt(3);
		Log.d("action", "intAnimation = " + intAnimation);
		switch (intAnimation) {
		case 0:
			AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
			alphaAnimation.setDuration(1000);
			alphaAnimation.setStartOffset(500); // 1 秒後開始

			animationSet.addAnimation(alphaAnimation);
			animationSet.setFillBefore(false);
			animationSet.setFillAfter(true);
			break;
		case 1:
			ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.5f, 1,
					0.5f, Animation.RELATIVE_TO_SELF, 1f,
					Animation.RELATIVE_TO_SELF, 1f);

			animationSet.addAnimation(scaleAnimation);
			animationSet.setDuration(1000);
			animationSet.setFillBefore(false);
			animationSet.setFillAfter(true);
			break;

		case 2:
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
					Animation.RELATIVE_TO_PARENT, 0.5f,
					Animation.RELATIVE_TO_PARENT, 0.5f);
			rotateAnimation.setDuration(1000);

			animationSet.addAnimation(rotateAnimation);
			break;

		case 3:
			TranslateAnimation translateAnimation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					1.0f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 1.0f);
			translateAnimation.setDuration(1000);

			animationSet.addAnimation(translateAnimation);
			break;

		}
		_iv.startAnimation(animationSet);
	}

	@Override
	protected void onPause() {
		handler.removeCallbacks(runnable);
		super.onPause();
	}

}
