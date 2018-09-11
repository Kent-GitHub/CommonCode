package com.broadsense.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Method;

import static com.broadsense.view.CommonDialog.Builder.TYPE_EMPTY;

public class CommonDialog extends Dialog {

    public static final int STYLE_DEFAULT = 0;

    public static final int STYLE_MD = 1;

    public static final int STYLE_DARK = 2;

    private static int dialogStyle = STYLE_DEFAULT;

    public static void setDialogStyle(int style) {
        dialogStyle = style;
    }

    public static int getDialogStyle() {
        return dialogStyle;
    }

    private static final String HOME_PRESSED = "com.broadsense.common.centercontrol.action.KEY_HOME_CODE_MENU_UP";

    private BroadcastReceiver mReceiver;

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public interface OnClickListener {
        void onClick(CommonDialog dialog);
    }

    private Builder builder;

    public enum Size {
        SMALL, BIG
    }

    public static class Builder {
        private int builderType;
        private int layoutId;
        public static final int TYPE_NORMAL = 0x11;
        public static final int TYPE_NO_TITLE = 0x12;
        public static final int TYPE_EMPTY = 0x13;

        private int bgRes = -1;
        private Drawable bgDrawable;
        private int customViewRes = -1;
        private View customView;

        private OnClickListener leftListener;
        private OnClickListener rightListener;
        private OnClickListener centerListener;
        private OnCancelListener mCancelListener;
        private Context mContext;
        private String mTitleText;
        private String mBodyText;
        private String rightBtnText;
        private String leftBtnText;
        private String centerBtnText;
        private int dialogWidth;
        private int dialogHeight;
        private boolean homeDismiss;
        private boolean reverseBtnBg;

        public Builder(Context context, Size size) {
            this.mContext = context;
            int wRes = R.dimen.dialog_w_small;
            int hRes = R.dimen.dialog_h_small;
            switch (size) {
                case SMALL:
                    wRes = R.dimen.dialog_w_small;
                    hRes = R.dimen.dialog_h_small;
                    break;
                case BIG:
                    wRes = R.dimen.dialog_w_big;
                    hRes = R.dimen.dialog_h_big;
                    break;
            }
            dialogWidth = context.getResources().getDimensionPixelSize(wRes);
            dialogHeight = context.getResources().getDimensionPixelSize(hRes);
        }

        public Builder(Context context) {
            this(context, Size.SMALL);
        }

        public Builder(Context context, int width, int height) {
            this.mContext = context;
            this.dialogWidth = width;
            this.dialogHeight = height;
        }

        public Builder setTitle(String title) {
            this.mTitleText = title;
            return this;
        }

        public Builder setBgRes(int ResId) {
            bgRes = ResId;
            return this;
        }

        public Builder setBgDrawable(Drawable bgDrawable) {
            this.bgDrawable = bgDrawable;
            return this;
        }

        public Builder setBodyText(String body) {
            this.mBodyText = body;
            return this;
        }

        public Builder setRightBtnText(String rightBtnText) {
            this.rightBtnText = rightBtnText;
            return this;
        }

        public Builder setLeftBtnText(String leftBtnText) {
            this.leftBtnText = leftBtnText;
            return this;
        }

        public Builder setCenterBtnText(String centerBtnText) {
            this.centerBtnText = centerBtnText;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener cancelListener) {
            this.mCancelListener = cancelListener;
            return this;
        }

        public Builder setLeftBtnClickListener(OnClickListener leftListener) {
            this.leftListener = leftListener;
            return this;
        }

        public Builder setLeftBtnClickDismiss() {
            this.leftListener = new OnClickListener() {
                @Override
                public void onClick(CommonDialog dialog) {
                    dialog.dismiss();
                }
            };
            return this;
        }

        public Builder setRightBtnClickListener(OnClickListener rightListener) {
            this.rightListener = rightListener;
            return this;
        }

        public Builder setRightBtnClickDismiss() {
            this.rightListener = new OnClickListener() {
                @Override
                public void onClick(CommonDialog dialog) {
                    dialog.dismiss();
                }
            };
            return this;
        }

        public Builder setCenterBtnClickListener(OnClickListener centerListener) {
            this.centerListener = centerListener;
            return this;
        }

        public Builder setCenterClickDismiss() {
            this.centerListener = new OnClickListener() {
                @Override
                public void onClick(CommonDialog dialog) {
                    dialog.dismiss();
                }
            };
            return this;
        }

        public Builder setCustomViewRes(int viewRes) {
            customViewRes = viewRes;
            return this;
        }

        public Builder setCustomView(View view) {
            customView = view;
            return this;
        }

        public Builder setHomeDismiss(boolean dismiss) {
            homeDismiss = dismiss;
            return this;
        }

        public Builder setReverseBtnBg(boolean reverseBtnBg) {
            this.reverseBtnBg = reverseBtnBg;
            return this;
        }

        public CommonDialog create() {
            layoutId = R.layout.common_dialog_layout;
            if (dialogStyle == STYLE_MD) {
                layoutId = R.layout.common_dialog_layout_md;
            } else if (dialogStyle == STYLE_DARK) {
                layoutId = R.layout.common_dialog_layout_dark;
            }
            builderType = TYPE_NORMAL;
            CommonDialog dialog = new CommonDialog(mContext, R.style.Dialog_My);
            dialog.initDialogWithBuilder(this);
            return dialog;
        }

        public CommonDialog createEmptyDialog() {
            layoutId = R.layout.common_dialog_empty;
            if (dialogStyle == STYLE_DARK) {
                layoutId = R.layout.common_dialog_empty_dark;
            }
            builderType = TYPE_EMPTY;
            CommonDialog dialog = new CommonDialog(mContext, R.style.Dialog_My);
            dialog.initDialogWithBuilder(this);
            return dialog;
        }

        public CommonDialog createNoTitle() {
            layoutId = R.layout.common_dialog_no_title;
            if (dialogStyle == STYLE_MD) {
                layoutId = R.layout.common_dialog_no_title_md;
            } else if (dialogStyle == STYLE_DARK) {
                layoutId = R.layout.common_dialog_no_title_dark;
            }
            builderType = TYPE_NO_TITLE;
            CommonDialog dialog = new CommonDialog(mContext, R.style.Dialog_My);
            dialog.initDialogWithBuilder(this);
            return dialog;
        }
    }

    private TextView leftBtn, rightBtn, centerBtn, titleTextView, bodyTextView;
    private ViewGroup customViewContainer, rootView;

    private void initDialogWithBuilder(final Builder builder) {
        this.builder = builder;
        Context context = getContext();
        //init dialog layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(builder.layoutId, null);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        lp.height = builder.dialogHeight;
        lp.width = builder.dialogWidth;
        addContentView(view, lp);
        //init views
        rootView = (ViewGroup) findViewById(R.id.root_container);
        leftBtn = (TextView) findViewById(R.id.dialog_cancel_btn);
        rightBtn = (TextView) findViewById(R.id.dialog_ok_btn);
        centerBtn = (TextView) findViewById(R.id.dialog_center_btn);
        titleTextView = (TextView) findViewById(R.id.dialog_title_tv);
        bodyTextView = (TextView) findViewById(R.id.dialog_body_tv);

        if (bodyTextView != null) {
            bodyTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        if (builder.builderType == TYPE_EMPTY) {
            customViewContainer = rootView;
        } else {
            customViewContainer = (ViewGroup) findViewById(R.id.dialog_custom_view_container);
        }

        //set custom view
        if (builder.customViewRes != -1)
            LayoutInflater.from(getContext()).inflate(builder.customViewRes, customViewContainer, true);
        if (builder.customView != null) customViewContainer.addView(builder.customView);
        //set bg res
        if (builder.bgRes != -1) rootView.setBackgroundResource(builder.bgRes);
        if (builder.bgDrawable != null) rootView.setBackground(builder.bgDrawable);
        //set text
        setText(titleTextView, builder.mTitleText);
        setText(bodyTextView, builder.mBodyText);
        setText(leftBtn, builder.leftBtnText);
        setText(rightBtn, builder.rightBtnText);
        setText(centerBtn, builder.centerBtnText);
        //set listener
        setClickListener(leftBtn, builder.leftListener);
        setClickListener(rightBtn, builder.rightListener);
        setClickListener(centerBtn, builder.centerListener);
        //dialog cancel listener
        if (builder.mCancelListener != null) setOnCancelListener(builder.mCancelListener);
        //set visibility
        boolean doubleBtn = builder.centerListener == null;
        setVisibility(leftBtn, doubleBtn ? View.VISIBLE : View.GONE);
        setVisibility(rightBtn, doubleBtn ? View.VISIBLE : View.GONE);
        setVisibility(centerBtn, !doubleBtn ? View.VISIBLE : View.GONE);
        //verticalLine exists only if dialogStyle == STYLE_MD
        View verticalLine = rootView.findViewById(R.id.dialog_md_line_v);
        if (!doubleBtn) {
            //verticalLine exists only if dialogStyle == STYLE_MD
            setVisibility(verticalLine, View.GONE);
        }
        if (builder.homeDismiss) {
            registerReceiver();
        }
        if (builder.reverseBtnBg) {
            reverseBtnBg();
        }
        if (isBigDialog(view)) {
            setTextSize(getBodyTextView(), 26);
            setBodyTextSpacing(16);
        } else {
            setBodyTextSpacing(18);
        }
    }

    public void setBodyTextSpacing(int spacing) {
        TextView bodyTextView = getBodyTextView();
        if (bodyTextView != null) {
            bodyTextView.setLineSpacing(spacing, 1);
        }
    }

    private boolean isBigDialog(View view) {
        ViewGroup.LayoutParams rootViewLp = view.getLayoutParams();
        Context context = view.getContext();
        int dialogWidth = context.getResources().getDimensionPixelSize(R.dimen.dialog_w_big);
        int dialogHeight = context.getResources().getDimensionPixelSize(R.dimen.dialog_h_big);
        return rootViewLp.width >= dialogWidth && rootViewLp.height >= dialogHeight;
    }

    private void reverseBtnBg() {
        if (leftBtn == null || rightBtn == null) return;
        leftBtn.setBackgroundResource(R.drawable.btn_dark_bg_left_rev);
        rightBtn.setBackgroundResource(R.drawable.btn_dark_bg_right_rev);
    }

    private void setText(TextView tv, String content) {
        if (tv != null && content != null) {
            tv.setText(content);
        }
    }

    private void setClickListener(View view, final OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(CommonDialog.this);
                }
            });
        }
    }

    private void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    private void registerReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) return;
                switch (intent.getAction()) {
                    case HOME_PRESSED:
                        dismiss();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HOME_PRESSED);
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    public TextView getLeftBtn() {
        return leftBtn;
    }

    public TextView getRightBtn() {
        return rightBtn;
    }

    public TextView getCenterBtn() {
        return centerBtn;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getBodyTextView() {
        return bodyTextView;
    }

    public ViewGroup getCustomViewContainer() {
        return customViewContainer;
    }

    public void setTextViewGravity(TextView view, int gravity) {
        if (view != null) {
            view.setGravity(gravity);
        }
    }

    public void setTextSize(TextView view, float sizeInPx) {
        setTextSize(view, TypedValue.COMPLEX_UNIT_PX, sizeInPx);
    }

    public void setTextSize(TextView view, int unit, float size) {
        if (view != null) {
            view.setTextSize(unit, size);
        }
    }

    /**
     * 改变View的Margin值，如果参数值为-1，表示保留原值
     *
     * @param view   需要修改Margin值的View
     * @param left   left
     * @param top    top
     * @param right  tight
     * @param bottom bottom
     */
    public void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            int oldLeft = ((ViewGroup.MarginLayoutParams) lp).leftMargin;
            int oldTop = ((ViewGroup.MarginLayoutParams) lp).topMargin;
            int oldRight = ((ViewGroup.MarginLayoutParams) lp).rightMargin;
            int oldBottom = ((ViewGroup.MarginLayoutParams) lp).bottomMargin;
            int newLeft = left == -1 ? oldLeft : left;
            int newTop = top == -1 ? oldTop : top;
            int newRight = right == -1 ? oldRight : right;
            int newBottom = bottom == -1 ? oldBottom : bottom;
            //((ViewGroup.MarginLayoutParams) lp).setMarginsRelative(newLeft, newTop, newRight, newBottom);
            try {
                Method setMarginsRelative = lp.getClass().getMethod("setMarginsRelative", int.class, int.class, int.class, int.class);
                setMarginsRelative.invoke(lp, newLeft, newTop, newRight, newBottom);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            view.setLayoutParams(lp);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mReceiver != null) {
            getContext().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }
}
