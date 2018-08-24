package com.luci.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.luci.R;
import com.luci.ui.activity.BaseActionBarActivity;
import com.luci.ui.activity.OptionsActivity;

public class OptionGeneralFragment extends BaseFragment {
	public static OptionGeneralFragment instance;
	// Data
	BaseActionBarActivity mActivity;

	public static OptionGeneralFragment newInstance() {
		OptionGeneralFragment fragment = new OptionGeneralFragment();
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
		mView = inflater.inflate(R.layout.fragment_options_general, container, false);

		Spinner spn_preset = (Spinner) mView.findViewById(R.id.spn_preset);
		String[] presets = getResources().getStringArray(R.array.audio_input_category);
		ArrayAdapter protocolAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, presets);
		spn_preset.setAdapter(protocolAdapter);
		spn_preset.setSelection(0);

		return mView;
	}
}
