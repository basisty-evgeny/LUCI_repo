package com.luci.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.luci.R;
import com.luci.model.StationModel;

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

        edt_name = findViewById(R.id.edt_name);
        spn_protocol = findViewById(R.id.spn_protocol);
        edt_destination = findViewById(R.id.edt_destination);
        edt_jitter = findViewById(R.id.edt_jitter);
        edt_dynamic = findViewById(R.id.edt_dynamic);
        spn_format = findViewById(R.id.spn_format);
        chk_stereo = findViewById(R.id.chk_stereo);
        spn_bitrate = findViewById(R.id.spn_bitrate);
        spn_samplerate = findViewById(R.id.spn_samplerate);
        chk_talk_mode = findViewById(R.id.chk_talk_mode);

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

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok: {
                if (isValid()) {
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

    }
}
