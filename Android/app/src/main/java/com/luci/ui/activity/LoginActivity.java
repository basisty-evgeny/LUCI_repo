package com.luci.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static LoginActivity instance;

    TextInputLayout til_email;
    TextInputEditText edt_email;
    TextInputLayout til_password;
    TextInputEditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_login);

        til_email = (TextInputLayout) findViewById(R.id.til_email);
        edt_email = (TextInputEditText) findViewById(R.id.edt_email);
        til_password = (TextInputLayout)findViewById(R.id.til_password);
        edt_password = (TextInputEditText)findViewById(R.id.edt_password);

        SharedPreferences sp = getSharedPreferences(Constant.USERINFO_ID, Context.MODE_PRIVATE );
        String email = sp.getString("email", "" );
        edt_email.setText(email);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {
                if (isValid()) {
                    Login();
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

    private boolean isValid() {
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
        } else if (TextUtils.getTrimmedLength(password) < 4)  {
            til_password.setError(getString(R.string.invalid_password_length));
            edt_password.requestFocus();
            return false;
        } else {
            til_password.setError(null);
        }

        return true;
    }

    private void Login() {
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        RequestParams params = new RequestParams();
        params.add("email", email);
        params.add("password", password);

        LUCIHttpClient.post(this, Constant.URL_SIGNIN, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {
                if (object != null) {
                    try {
                        Boolean status = object.getBoolean("success");
                        if (status) {
                            Toasty.success(instance, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

                            if (SplashActivity.instance != null)
                                SplashActivity.instance.finish();

                            finish();

                            Intent intent = new Intent(instance, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // TODO: Catch reason_code, and alert appropriate toast.
                            Toasty.normal(instance, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        if (SplashActivity.instance != null)
            SplashActivity.instance.finish();
        finish();
    }
}
