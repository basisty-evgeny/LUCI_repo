package com.luci.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luci.R;
import com.luci.ui.activity.BaseActionBarActivity;
import com.luci.ui.activity.OptionsActivity;

public class OptionDefaultFragment extends BaseFragment {
	public static OptionDefaultFragment instance;
	// Data
	BaseActionBarActivity mActivity;

	public static OptionDefaultFragment newInstance() {
		OptionDefaultFragment fragment = new OptionDefaultFragment();
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
		mView = inflater.inflate(R.layout.fragment_options_default, container, false);
		return mView;
	}
}
