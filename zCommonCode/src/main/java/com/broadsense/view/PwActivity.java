package com.broadsense.view;

import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by Kent_Lee on 2018/3/2.
 */

public class PwActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog() {
        final EditText ed = new EditText(this);
        MyDialog myDialog = new MyDialog.Builder(this)
                .setCustomView(ed)
                .setLeftBtnText("取消")
                .setRightBtnText("确定")
                .setLeftBtnClickListener(new MyDialog.OnDialogBtnClickListener() {
                    @Override
                    public void onClick(MyDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setRightBtnClickListener(new MyDialog.OnDialogBtnClickListener() {
                    @Override
                    public void onClick(MyDialog dialog) {
                        String input = ed.getText().toString();
                        TelephonyManager tm = (TelephonyManager) PwActivity.this.getSystemService(Service.TELEPHONY_SERVICE);
                        String imei = tm.getDeviceId();
                        String pw = getString(R.string.default_pw);
                        if (!TextUtils.isEmpty(imei)) {
                            pw = pw + imei.substring(imei.length() - 6, imei.length());
                        }
                        if (input.equals(pw)) {
                            dialog.dismiss();
                        } else {
                            ed.setText("");
                        }
                    }
                })
                .create();
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        myDialog.show();
    }
}
