package com.broadsense.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * * Created by kent on 18-9-11.
 */
public class CommonDialogDemo {

    public void demo1(Context context) {
        String hintText = context.getResources().getString(R.string.tf_format_real_hint);
        CommonDialog firstHintDialog = new CommonDialog.Builder(context)
                .setTitle("注意")
                .setBodyText(hintText)
                .setLeftBtnClickDismiss()//点击左边按钮取消弹窗
                .setRightBtnText("去格式化")
                .setRightBtnClickListener(new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(CommonDialog dialog) {
                        dialog.dismiss();
                    }
                }).create();
        //设置文字边距，-1表示不修改
        firstHintDialog.setViewMargin(firstHintDialog.getBodyTextView(), 40, 30, 40, -1);
        //默认标题32px，bodyText30px，建议按需求自行修改
        firstHintDialog.getBodyTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX,26);
        firstHintDialog.show();
    }

    public void demo2(Context context) {
        CommonDialog formatResultDialog = new CommonDialog.Builder(context).setBodyText("格式化失败，是否重试")
                .setLeftBtnClickListener(new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(CommonDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightBtnText("重试")
                .setRightBtnClickListener(new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(CommonDialog dialog) {
                        dialog.dismiss();
                        //其他逻辑
                    }
                })
                .createNoTitle();
        formatResultDialog.setCanceledOnTouchOutside(false);
        formatResultDialog.show();
    }

    public void demo3(Context context) {
        int hintRes = R.string.tf_format_simple_hint;
        String hintText = context.getResources().getString(hintRes);
        CommonDialog firstHintDialog = new CommonDialog.Builder(context)
                .setTitle("注意")
                .setBodyText(hintText)
                .setLeftBtnClickDismiss()
                .setRightBtnText("去格式化")
                .setRightBtnClickListener(new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(CommonDialog dialog) {
                        dialog.dismiss();

                    }
                }).create();
        firstHintDialog.setViewMargin(firstHintDialog.getBodyTextView(), 0, 60, 0, -1);
        firstHintDialog.setTextViewGravity(firstHintDialog.getBodyTextView(), Gravity.CENTER_HORIZONTAL);
        //firstHintDialog.getBodyTextView().setGravity(Gravity.CENTER_HORIZONTAL);
        //firstHintDialog.getTitleTextView().setVisibility(View.GONE);
        firstHintDialog.show();
    }

    public void showConfirmDialog(final Context context) {
        CommonDialog confirmDialog = new CommonDialog.Builder(context)
                .setCustomViewRes(R.layout.dialog_tf_whetherformat)//可设置自定义View
                //.setCustomView(customView)
                .setLeftBtnClickDismiss()
                .setRightBtnClickListener(new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(CommonDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .createNoTitle();
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();
    }

    public void showLoadingDialog(Context context) {
        LoadingViewBig loadingView = new LoadingViewBig(context);
        loadingView.setLoadingText("正在格式化");
        loadingView.showTvVisibility(View.VISIBLE);
        //
        CommonDialog loadingDialog = new CommonDialog.Builder(context)
                .setCustomView(loadingView)
                .createEmptyDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        loadingDialog.show();
    }


}
