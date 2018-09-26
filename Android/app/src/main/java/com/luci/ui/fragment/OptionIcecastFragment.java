package com.luci.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luci.R;
import com.luci.ui.activity.BaseActionBarActivity;
import com.luci.ui.activity.OptionsActivity;
import com.luci.util.Constant;

public class OptionIcecastFragment extends BaseFragment {
    public static OptionIcecastFragment instance;
    // Data
    BaseActionBarActivity mActivity;

    TextInputEditText host;
    TextInputEditText port;
    TextInputEditText mountpoint;
    TextInputEditText user;
    TextInputEditText password;

    public static OptionIcecastFragment newInstance() {
        OptionIcecastFragment fragment = new OptionIcecastFragment();
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
        mView = inflater.inflate(R.layout.fragment_options_icecast, container, false);

        host = (TextInputEditText) mView.findViewById(R.id.edt_icehost);
        port = (TextInputEditText) mView.findViewById(R.id.edt_iceport);
        mountpoint = (TextInputEditText) mView.findViewById(R.id.edt_icemountpoint);
        user = (TextInputEditText) mView.findViewById(R.id.edt_iceuser);
        password = (TextInputEditText) mView.findViewById(R.id.edt_icepwd);

        host.setText(Constant.ICE_HOST);
        port.setText(Integer.toString(Constant.ICE_PORT));
        mountpoint.setText(Constant.ICE_MOUNT);
        user.setText(Constant.ICE_USER);
        password.setText(Constant.ICE_PASS);

        return mView;
    }

    public void saveConfig()
    {
        Constant.ICE_HOST =  host.getText().toString();
        Constant.ICE_PORT = Integer.valueOf(port.getText().toString());
        Constant.ICE_MOUNT = mountpoint.getText().toString();
        Constant.ICE_USER = user.getText().toString();
        Constant.ICE_PASS = password.getText().toString();
    }
}
