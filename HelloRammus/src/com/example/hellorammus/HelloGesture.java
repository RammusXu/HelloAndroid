package com.example.hellorammus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HelloGesture extends Activity {
	private TextView tv;
	private GestureDetector mGestureDetector01;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 50;

	private ViewFlipper mViewFlipper01;
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;

	private HelloPagerAdapter mPagerAdapter01;
	private LayoutInflater mLayoutInflater01;
	private List<View> mListViews01;
	private ViewPager mViewPager01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_gesture);

		init();
		initViewPager();
	}

	private void init() {
		mViewFlipper01 = (ViewFlipper) findViewById(R.id.hello_gesture_viewFlipper1);
		LinearLayout.LayoutParams mLinearLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		LinearLayout.LayoutParams mImageViewLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		LinearLayout linearLayout = new LinearLayout(HelloGesture.this);
		linearLayout.setLayoutParams(mLinearLayoutParams);
		ImageView iv = new ImageView(HelloGesture.this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.sbar_5);
		iv.setLayoutParams(mImageViewLayoutParams);
		linearLayout.addView(iv);
		mViewFlipper01.addView(linearLayout);

		linearLayout = new LinearLayout(HelloGesture.this);
		linearLayout.setLayoutParams(mLinearLayoutParams);
		iv = new ImageView(HelloGesture.this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.sbar_7);
		iv.setLayoutParams(mImageViewLayoutParams);
		linearLayout.addView(iv);
		mViewFlipper01.addView(linearLayout);

		mGestureDetector01 = new GestureDetector(getApplicationContext(),
				new MyGestureListener());
		tv = (TextView) findViewById(R.id.hello_gesture_textView1);
		tv.setOnTouchListener(new TextView.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector01.onTouchEvent(event);
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector01.onTouchEvent(event);
	}

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				tv.setText("Right to left");
				mViewFlipper01.setInAnimation(slideLeftIn);
				mViewFlipper01.setOutAnimation(slideLeftOut);
				mViewFlipper01.showPrevious();
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				tv.setText("Left to right");
				mViewFlipper01.setInAnimation(slideRightIn);
				mViewFlipper01.setOutAnimation(slideRightOut);
				mViewFlipper01.showNext();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	private void initViewPager() {
		mViewPager01 = (ViewPager) findViewById(R.id.hello_gesture_viewPager1);
		mListViews01 = new ArrayList<View>();
		mLayoutInflater01 = HelloGesture.this.getLayoutInflater();
		mListViews01
				.add(mLayoutInflater01.inflate(R.layout.view_pager_1, null)); // 0
		mListViews01
				.add(mLayoutInflater01.inflate(R.layout.view_pager_2, null));
		mListViews01
				.add(mLayoutInflater01.inflate(R.layout.view_pager_3, null));
		mPagerAdapter01 = new HelloPagerAdapter(HelloGesture.this, mListViews01);
		mViewPager01.setAdapter(mPagerAdapter01);
		mViewPager01.setCurrentItem(0);

		mViewPager01
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						tv.setText("Page:" + position);
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}
				});

	}
}
