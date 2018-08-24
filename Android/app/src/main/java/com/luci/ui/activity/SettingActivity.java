package com.luci.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

//import com.aditya.filebrowser.Constants;
//import com.aditya.filebrowser.FileChooser;
import com.luci.R;
import com.luci.ui.dialog.PhonebookInternetDlg;

public class SettingActivity extends BaseActivity implements
        View.OnClickListener {
    public static SettingActivity instance;
    public static final int PICK_FILE_REQUEST = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_setting);

        findViewById(R.id.btn_setting0).setOnClickListener(this);
        findViewById(R.id.btn_setting1).setOnClickListener(this);
        findViewById(R.id.btn_setting2).setOnClickListener(this);
        findViewById(R.id.btn_setting3).setOnClickListener(this);
        findViewById(R.id.btn_setting4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting0: {
                finish();
                Intent intent = new Intent(instance, ChooseStationActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.btn_setting1: {
                PhonebookInternetDlg dlg = new PhonebookInternetDlg(instance);
                dlg.show();
            }
            break;

            case R.id.btn_setting2: {
//                Intent intent = new Intent(instance, FileChooser.class);
//                intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.SINGLE_SELECTION.ordinal());
//                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
            break;

            case R.id.btn_setting3: {
                finish();
                Intent intent = new Intent(instance, EditStationActivity.class);
                EditStationActivity.mStation = null;
                startActivity(intent);
            }
            break;

            case R.id.btn_setting4: {
                finish();
                Intent intent = new Intent(instance, OptionsActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && data != null) {
            if (resultCode == RESULT_OK) {
                Uri file = data.getData();
                if (file != null)
                    Log.e("Luci", file.getPath());

                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.out_down);
    }
}
