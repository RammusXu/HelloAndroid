package com.example.hellorammus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTwo extends Fragment {
	public String TAG = "HIPPO_DEBUG";
	private View rootView;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater
					.inflate(R.layout.fragment_two, container, false);
		}
		tv = (TextView) rootView.findViewById(R.id.fragment_two_textView1);

		return rootView;
	}

	// 新建立的method for FragmentTwo被外部物件呼叫使用
	public void setText(String str) {
		if (tv != null) {
			tv.setText(str);
		}
	}

}
