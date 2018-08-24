package com.luci.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;


public class WaitDialog extends Dialog {

    public WaitDialog(Context context) {
        super(context);
        initUI(context);
    }

    private void initUI(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar progress = new ProgressBar(context);
        this.setContentView(progress);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch(Exception e) {
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch(Exception e) {
        }
    }

    @Override
    public void cancel() {
        try {
            super.cancel();
        } catch(Exception e) {
        }
    }

}
