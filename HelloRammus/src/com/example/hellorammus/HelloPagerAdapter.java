package com.example.hellorammus;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class HelloPagerAdapter extends PagerAdapter {
	private Context mContext;
	private List<View> mListViews;

	public HelloPagerAdapter(Context cx, List<View> lv) {
		super();
		// 1
		mContext = cx.getApplicationContext();
		this.mListViews = lv;
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 2
		container.addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
