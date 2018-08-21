package com.luci.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luci.R;
import com.luci.ui.activity.BaseActionBarActivity;
import com.luci.ui.activity.OptionsActivity;

public class OptionSipFragment extends BaseFragment {
	public static OptionSipFragment instance;
	// UI
	SwipeRefreshLayout refresh_layout;

	// Data
	BaseActionBarActivity mActivity;

	public static OptionSipFragment newInstance() {
		OptionSipFragment fragment = new OptionSipFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		instance = this;
		mActivity = OptionsActivity.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_options_sip, container, false);
		return mView;
	}
}
