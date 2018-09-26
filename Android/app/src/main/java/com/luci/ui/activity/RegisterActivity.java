package com.luci.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.luci.R;
import com.luci.util.CommonUtil;
import com.luci.util.Constant;
import com.luci.util.LUCIHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends BaseActivity implements
        View.OnClickListener {
    public static RegisterActivity instance;

    TextInputLayout til_name;
    TextInputEditText edt_name;
    TextInputLayout til_organization;
    TextInputEditText edt_organization;
    TextInputLayout til_email;
    TextInputEditText edt_email;
    TextInputLayout til_password;
    TextInputEditText edt_password;
    TextInputLayout til_password_confirm;
    TextInputEditText edt_password_confirm;
    TextInputLayout til_activation_code;
    TextInputEditText edt_activation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_register);

        til_name = (TextInputLayout) findViewById(R.id.til_name);
        edt_name = (TextInputEditText) findViewById(R.id.edt_name);
        til_organization = (TextInputLayout) findViewById(R.id.til_organization);
        edt_organization = (TextInputEditText) findViewById(R.id.edt_organization);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        edt_email = (TextInputEditText) findViewById(R.id.edt_email);
        til_password = (TextInputLayout)findViewById(R.id.til_password);
        edt_password = (TextInputEditText)findViewById(R.id.edt_password);
        til_password_confirm = (TextInputLayout)findViewById(R.id.til_password_confirm);
        edt_password_confirm = (TextInputEditText)findViewById(R.id.edt_password_confirm);
        til_activation_code = (TextInputLayout) findViewById(R.id.til_activation_code);
        edt_activation_code = (TextInputEditText) findViewById(R.id.edt_activation_code);

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

        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            til_password.setError(getString(R.string.invalid_password_empty));
            edt_password.requestFocus();
            return false;
        } else if (TextUtils.getTrimmedLength(password) < 6)  {
            til_password.setError(getString(R.string.invalid_password_length));
            edt_password.requestFocus();
            return false;
        } else {
            til_password.setError(null);
        }

        String password_confirm = edt_password_confirm.getText().toString().trim();
        if (!password_confirm.equals(password)) {
            til_password_confirm.setError(getString(R.string.invalid_password));
            edt_password_confirm.requestFocus();
            return false;
        } else {
            til_password_confirm.setError(null);
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
        String name = edt_name.getText().toString().trim();
        String organization = edt_organization.getText().toString().trim();
        final String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String activation_code = edt_activation_code.getText().toString().trim();

        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("organization", organization);
        params.add("email", email);
        params.add("password", password);
        params.add("activation_code", activation_code);

        LUCIHttpClient.post(this, Constant.URL_SIGNUP, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {
                if (object != null) {
                    try {
                        Boolean status = object.getBoolean("success");
                        if (status) {
                            Toasty.success(instance, getString(R.string.register_success), Toast.LENGTH_SHORT).show();

                            SharedPreferences sp = getSharedPreferences(Constant.USERINFO_ID, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString( "email", email );
                            editor.putBoolean( "firstuse", false );
                            editor.commit();

//                            if (SplashActivity.instance != null)
//                                SplashActivity.instance.finish();

                            finish();

                            Intent intent = new Intent(instance, LoginActivity.class);
                            startActivity(intent);
                        }
//                        else {
//                            // TODO: Catch reason_code, and alert appropriate toast.
//                            Toasty.error(instance, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();
//                        }
                    } catch (Exception e) {
                        Toasty.error(instance, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
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
}
