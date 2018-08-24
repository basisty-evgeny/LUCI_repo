package com.luci.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.luci.R;

public class PhonebookInternetDlg extends Dialog {
	// UI
	TextInputLayout til_url;
	TextInputEditText edt_url;

	// Data
	Context mContext;

	public PhonebookInternetDlg(@NonNull Context context) {
		super(context);
		init(context);
	}

	public PhonebookInternetDlg(@NonNull Context context, int themeResId) {
		super(context, themeResId);
		init(context);
	}

	protected PhonebookInternetDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_phonebook_internet);

		til_url = (TextInputLayout) findViewById(R.id.til_url);
		edt_url = (TextInputEditText) findViewById(R.id.edt_url);

		findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isValid()) {
					dismiss();
				}
			}
		});

		findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
			}
		});
	}

	private void init(Context context) {
		mContext = context;
	}

	private boolean isValid() {
		String url = edt_url.getText().toString().trim();
		if (TextUtils.isEmpty(url)) {
			til_url.setError(mContext.getString(R.string.invalid_url_empty));
			edt_url.requestFocus();
			return false;
		} else {
			til_url.setError(null);
		}

		return true;
	}
}
