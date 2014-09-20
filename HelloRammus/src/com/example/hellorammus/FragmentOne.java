package com.example.hellorammus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FragmentOne extends Fragment {
	public String TAG = "HIPPO_DEBUG";
	private FragmentActivity parentActivity;
	private View rootView;
	private ListView mListView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> lst = new ArrayList<String>();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.parentActivity = (FragmentActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater
					.inflate(R.layout.fragment_one, container, false);
		}
		ViewGroup p = (ViewGroup) rootView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}

		Button btn = (Button) this.parentActivity
				.findViewById(R.id.hello_fragment_button1);
		btn.setText("Fragmen 1 here");

		mListView = (ListView) rootView
				.findViewById(R.id.fragment_one_listView1);
		lst = new ArrayList<String>();
		lst.add("Listen Now");
		lst.add("My Library");
		lst.add("PlayList");
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, lst);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (HelloFragment.intScreenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
					FragmentTwo f2 = (FragmentTwo) getFragmentManager()
							.findFragmentById(R.id.hello_fragment_linearLayout2);
					if (f2 != null) {
						f2.setText("from Fragment One:"
								+ mListView.getItemAtPosition(position)
										.toString());
					}
				} else {
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					FragmentTwo f2 = new FragmentTwo();
					ft.replace(R.id.hello_fragment_linearLayout1, f2,
							"fragmentTwo");
					ft.addToBackStack("fragmentOne");
					ft.commit();
				}
			}
		});
		return rootView;
	}

}
