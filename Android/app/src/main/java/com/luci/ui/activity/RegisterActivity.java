package com.luci.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;

import com.luci.R;
import com.luci.util.CommonUtil;

public class RegisterActivity extends BaseActivity implements
        View.OnClickListener {
    public static RegisterActivity instance;

    TextInputLayout til_name;
    TextInputEditText edt_name;
    TextInputLayout til_organization;
    TextInputEditText edt_organization;
    TextInputLayout til_email;
    TextInputEditText edt_email;
    TextInputLayout til_activation_code;
    TextInputEditText edt_activation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_register);

        til_name = findViewById(R.id.til_name);
        edt_name = findViewById(R.id.edt_name);
        til_organization = findViewById(R.id.til_organization);
        edt_organization = findViewById(R.id.edt_organization);
        til_email = findViewById(R.id.til_email);
        edt_email = findViewById(R.id.edt_email);
        til_activation_code = findViewById(R.id.til_activation_code);
        edt_activation_code = findViewById(R.id.edt_activation_code);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register: {
                if (isValid()) {
                    register();
                }
            }
            break;

            case R.id.btn_demo: {
                if (SplashActivity.instance != null)
                    SplashActivity.instance.finish();

                finish();

                Intent intent = new Intent(instance, MainActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        if (SplashActivity.instance != null)
            SplashActivity.instance.finish();
        finish();
    }

    private boolean isValid() {
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            til_name.setError(getString(R.string.invalid_name_empty));
            edt_name.requestFocus();
            return false;
        } else {
            til_name.setError(null);
        }

        String organization = edt_organization.getText().toString().trim();
        if (TextUtils.isEmpty(organization)) {
            til_organization.setError(getString(R.string.invalid_organization_empty));
            edt_organization.requestFocus();
            return false;
        } else {
            til_organization.setError(null);
        }

        String email = edt_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            til_email.setError(getString(R.string.invalid_email_empty));
            edt_email.requestFocus();
            return false;
        } else {
            til_email.setError(null);
        }
        if (!CommonUtil.isValidEmail(email)) {
            til_email.setError(getString(R.string.invalid_email));
            edt_email.requestFocus();
            return false;
        } else {
            til_email.setError(null);
        }

        String activation_code = edt_activation_code.getText().toString().trim();
        if (TextUtils.isEmpty(activation_code)) {
            til_activation_code.setError(getString(R.string.invalid_activation_code_empty));
            edt_activation_code.requestFocus();
            return false;
        } else {
            til_activation_code.setError(null);
        }

        return true;
    }

    private void register() {

    }
}
