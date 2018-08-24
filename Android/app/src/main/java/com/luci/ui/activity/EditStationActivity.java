package com.luci.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.RequestParams;
import com.luci.R;
import com.luci.model.StationModel;
import com.luci.util.Constant;
import com.luci.util.LUCIHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class EditStationActivity extends BaseActionBarActivity implements
        View.OnClickListener {
    public static EditStationActivity instance;

    TextInputEditText edt_name;
    Spinner spn_protocol;
    TextInputEditText edt_destination;
    TextInputEditText edt_jitter;
    TextInputEditText edt_dynamic;
    Spinner spn_format;
    CheckBox chk_stereo;
    Spinner spn_bitrate;
    Spinner spn_samplerate;
    CheckBox chk_talk_mode;

    // Data
    public static StationModel mStation;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_edit_station);

        initActionBar();
        if (mStation == null)
            setTitle(R.string.Add_station);
        else
            setTitle(R.string.Edit_station);

        edt_name = (TextInputEditText) findViewById(R.id.edt_name);
        spn_protocol = (Spinner) findViewById(R.id.spn_protocol);
        edt_destination = (TextInputEditText) findViewById(R.id.edt_destination);
        edt_jitter = (TextInputEditText) findViewById(R.id.edt_jitter);
        edt_dynamic = (TextInputEditText) findViewById(R.id.edt_dynamic);
        spn_format = (Spinner) findViewById(R.id.spn_format);
        chk_stereo = (CheckBox) findViewById(R.id.chk_stereo);
        spn_bitrate = (Spinner) findViewById(R.id.spn_bitrate);
        spn_samplerate = (Spinner) findViewById(R.id.spn_samplerate);
        chk_talk_mode = (CheckBox) findViewById(R.id.chk_talk_mode);

        String[] protocols = getResources().getStringArray(R.array.protocol_category);
        ArrayAdapter protocolAdapter = new ArrayAdapter<>(instance, android.R.layout.simple_spinner_item, protocols);
        spn_protocol.setAdapter(protocolAdapter);
        spn_protocol.setSelection(0);

        String[] formats = getResources().getStringArray(R.array.audio_format_category);
        ArrayAdapter formatAdapter = new ArrayAdapter<>(instance, android.R.layout.simple_spinner_item, formats);
        spn_format.setAdapter(formatAdapter);
        spn_format.setSelection(2);

        String[] bitrates = getResources().getStringArray(R.array.bitrate_category);
        ArrayAdapter bitrateAdapter = new ArrayAdapter<>(instance, android.R.layout.simple_spinner_item, bitrates);
        spn_bitrate.setAdapter(bitrateAdapter);
        spn_bitrate.setSelection(5);

        String[] samplerates = getResources().getStringArray(R.array.samplerate_category);
        ArrayAdapter samplerateAdapter = new ArrayAdapter<>(instance, android.R.layout.simple_spinner_item, samplerates);
        spn_samplerate.setAdapter(samplerateAdapter);
        spn_samplerate.setSelection(4);

        if (mStation != null) {
            edt_name.setText(mStation.name);

            for (int i = 0 ; i < protocols.length ; i ++) {
                if (mStation.protocol.equals(protocols[i]))
                    spn_protocol.setSelection(i);
            }

            edt_destination.setText(mStation.destination);
            edt_jitter.setText(Integer.toString(mStation.jitter_buffers));
            edt_dynamic.setText(Integer.toString(mStation.dynamic_jitter_buffers));

            for (int i = 0 ; i < formats.length ; i ++) {
                if (mStation.format.equals(formats[i]))
                    spn_format.setSelection(i);
            }

            for (int i = 0 ; i < bitrates.length ; i ++) {
                if (Integer.toString(mStation.bitrate).equals(bitrates[i]))
                    spn_bitrate.setSelection(i);
            }

            for (int i = 0 ; i < samplerates.length ; i ++) {
                if (Integer.toString(mStation.samplerate).equals(samplerates[i]))
                    spn_samplerate.setSelection(i);
            }

            chk_stereo.setChecked(mStation.stereo);
            chk_talk_mode.setChecked(mStation.talk_mode);

//            ArrayList<String> protocolList = new ArrayList<String>();
//            protocolList.addAll(Arrays.asList(protocols));
//            spn_protocol.setSelection(protocolList.indexOf(mStation.protocol));
        }

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok: {
                if (isValid()) {
                    if (mStation == null)
                        create();
                    else
                        edit();
                }
            }
            break;

            case R.id.btn_cancel: {
                onBackPressed();
            }
            break;
        }
    }

    private boolean isValid() {
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toasty.warning(instance, getString(R.string.invalid_name_empty), Toast.LENGTH_SHORT).show();
            edt_name.requestFocus();
            return false;
        }

        String destination = edt_destination.getText().toString().trim();
        if (TextUtils.isEmpty(destination)) {
            Toasty.warning(instance, getString(R.string.invalid_destination_empty), Toast.LENGTH_SHORT).show();
            edt_destination.requestFocus();
            return false;
        }

        String jitter = edt_jitter.getText().toString().trim();
        if (TextUtils.isEmpty(jitter)) {
            Toasty.warning(instance, getString(R.string.invalid_jitter_empty), Toast.LENGTH_SHORT).show();
            edt_jitter.requestFocus();
            return false;
        }

        String dynamic = edt_dynamic.getText().toString().trim();
        if (TextUtils.isEmpty(dynamic)) {
            Toasty.warning(instance, getString(R.string.invalid_dynamic_empty), Toast.LENGTH_SHORT).show();
            edt_dynamic.requestFocus();
            return false;
        }

        return true;
    }

    private void edit() {
        String name = edt_name.getText().toString().trim();
        String protocol = spn_protocol.getSelectedItem().toString().trim();
        String destination = edt_destination.getText().toString().trim();
        String jitter_buffers = edt_jitter.getText().toString().trim();
        String dynamic_jitter_buffers = edt_dynamic.getText().toString().trim();
        String format = spn_format.getSelectedItem().toString().trim();
        Boolean stereo = chk_stereo.isChecked();
        String bitrate = spn_bitrate.getSelectedItem().toString().trim();
        String samplerate = spn_samplerate.getSelectedItem().toString().trim();
        Boolean talk_mode = chk_talk_mode.isChecked();

        RequestParams params = new RequestParams();
        params.add("channel_name", name);
        params.add("protocol", protocol);
        params.add("destination", destination);
        params.add("jitter_buffers", jitter_buffers);
        params.add("dynamic_jitter_buffers", dynamic_jitter_buffers);
        params.add("format", format);
        params.add("stereo", stereo.toString());
        params.add("bitrate", bitrate);
        params.add("samplerate", samplerate);
        params.add("talk_mode", talk_mode.toString());

        params.add("channel_id", Integer.toString(mStation.id));

        LUCIHttpClient.post(this, Constant.URL_UPDATECHANNEL, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {
                if (object != null) {
                    try {
                        Boolean status = object.getBoolean("success");
                        if (status) {
                            Toasty.success(instance, getString(R.string.updatechannel_success), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(instance, ChooseStationActivity.class);
                            startActivity(intent);
                        }
//                        else {
//                            // TODO: Catch reason_code, and alert appropriate toast.
//                            Toasty.error(instance, getString(R.string.updatechannel_fail), Toast.LENGTH_SHORT).show();
//                        }
                    } catch (Exception e) {
                        Toasty.error(instance, getString(R.string.response_parse_error), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onSuccess(JSONArray array) {

            }

            @Override
            public void onFailure(int statusCode, Throwable throwable) {
            }
        });
    }

    private void create() {
        String name = edt_name.getText().toString().trim();
        String protocol = spn_protocol.getSelectedItem().toString().trim();
        String destination = edt_destination.getText().toString().trim();
        String jitter_buffers = edt_jitter.getText().toString().trim();
        String dynamic_jitter_buffers = edt_dynamic.getText().toString().trim();
        String format = spn_format.getSelectedItem().toString().trim();
        Boolean stereo = chk_stereo.isChecked();
        String bitrate = spn_bitrate.getSelectedItem().toString().trim();
        String samplerate = spn_samplerate.getSelectedItem().toString().trim();
        Boolean talk_mode = chk_talk_mode.isChecked();

        RequestParams params = new RequestParams();
        params.add("channel_name", name);
        params.add("protocol", protocol);
        params.add("destination", destination);
        params.add("jitter_buffers", jitter_buffers);
        params.add("dynamic_jitter_buffers", dynamic_jitter_buffers);
        params.add("format", format);
        params.add("stereo", stereo.toString());
        params.add("bitrate", bitrate);
        params.add("samplerate", samplerate);
        params.add("talk_mode", talk_mode.toString());

        LUCIHttpClient.post(this, Constant.URL_CREATECHANNEL, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {
                if (object != null) {
                    try {
                        Boolean status = object.getBoolean("success");
                        if (status) {
                            Toasty.success(instance, getString(R.string.newchannel_success), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(instance, ChooseStationActivity.class);
                            startActivity(intent);
                        } else {
                            // TODO: Catch reason_code, and alert appropriate toast.
                            Toasty.error(instance, getString(R.string.newchannel_fail), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toasty.error(instance, getString(R.string.response_parse_error), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onSuccess(JSONArray array) {

            }

            @Override
            public void onFailure(int statusCode, Throwable throwable) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "EditStation Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.luci.ui.activity/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "EditStation Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.luci.ui.activity/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
    }
}
