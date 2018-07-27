package com.broadsense.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * packageName：com.broadsense.newpine.set.ui.custom.dialog
 * ProjectName：5_Set
 * Description：自定义DIALOG,方便统一使用
 * Author：zhouxian
 * Date：2016/10/18 12:31
 */
public class MyDialog extends Dialog {


    public static final int STYLE_DEFAULT = 0;

    public static final int STYLE_MD = 1;

    public static final int STYLE_DARK = 2;

    private static int dialogStyle = STYLE_DEFAULT;

    public static void setDialogStyle(int style) {
        dialogStyle = style;
    }

    private static final String HOME_PRESSED = "com.broadsense.common.centercontrol.action.KEY_HOME_CODE_MENU_UP";

    private BroadcastReceiver mReceiver;

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public interface OnDialogBtnClickListener {
        void onClick(MyDialog dialog);
    }

    public static class Builder {
        private int bgColor = -1;
        private int bgRes = -1;
        private int customViewRes = -1;
        private View customView;

        private OnDialogBtnClickListener leftListener;
        private OnDialogBtnClickListener rightListener;
        private Context mContext;
        private String mTitleText;
        private String mBodyText;
        private String rightBtnText;
        private String leftBtnText;
        private int dialogWidth = 456;
        private int dialogHeight = 264;
        private int titleGravity = Gravity.CENTER;
        private boolean homeDismiss;

        public Builder(Context context) {
            this.mContext = context;
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

        public Builder setBgColor(int color) {
            bgColor = color;
            return this;
        }

        public Builder setBgRes(int ResId) {
            bgRes = ResId;
            return this;
        }

        public Builder setTitleGravity(int gravity) {
            titleGravity = gravity;
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

        public Builder setLeftBtnClickListener(OnDialogBtnClickListener leftListener) {
            this.leftListener = leftListener;
            return this;
        }

        public Builder setRightBtnClickListener(OnDialogBtnClickListener rightListener) {
            this.rightListener = rightListener;
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

        public MyDialog create() {
            int layoutId = R.layout.dialog_layout;
            if (dialogStyle == STYLE_MD) {
                layoutId = R.layout.dialog_layout_md;
            }
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MyDialog dialog = new MyDialog(mContext, R.style.Dialog_My);
            View view = inflater.inflate(layoutId, null);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            lp.height = dialogHeight;
            lp.width = dialogWidth;
            dialog.addContentView(view, lp);

            View rootView = view.findViewById(R.id.root_container);
            TextView leftBtn = (TextView) view.findViewById(R.id.dialog_cancel_btn);
            TextView rightBtn = (TextView) view.findViewById(R.id.dialog_ok_btn);
            TextView centerBtn = (TextView) view.findViewById(R.id.dialog_center_btn);
            TextView titleView = (TextView) view.findViewById(R.id.dialog_title_tv);
            TextView bodyView = (TextView) view.findViewById(R.id.dialog_body_tv);
            bodyView.setMovementMethod(ScrollingMovementMethod.getInstance());
            FrameLayout customViewContainer = (FrameLayout) view.findViewById(R.id.dialog_custom_view_container);

            if (customViewRes != -1) {
                inflater.inflate(customViewRes, customViewContainer, true);
            }
            if (customView != null) {
                customViewContainer.addView(customView);
            }
            if (bgColor != -1) rootView.setBackgroundColor(bgColor);
            if (bgRes != -1) rootView.setBackgroundResource(bgRes);
            if (mTitleText != null) titleView.setText(mTitleText);
            if (mBodyText != null) bodyView.setText(mBodyText);
            if (leftBtnText != null) leftBtn.setText(leftBtnText);
            if (rightBtnText != null) rightBtn.setText(rightBtnText);
            if (leftListener != null) {
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftListener.onClick(dialog);
                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightListener.onClick(dialog);
                    }
                });
            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
                centerBtn.setVisibility(View.VISIBLE);
                centerBtn.setText(rightBtnText);
                if (rightListener != null)
                    centerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rightListener.onClick(dialog);
                        }
                    });
            }
            boolean doubleBtn = leftListener != null;
            if (dialogStyle == STYLE_MD) {
                if (!doubleBtn) {
                    rootView.findViewById(R.id.dialog_md_line_v).setVisibility(View.GONE);
                }
            }
            dialog.setDialogProperty(this);
            return dialog;
        }

        public MyDialog createEmptyDialog() {
            int layoutId = R.layout.dialog_empty;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MyDialog dialog = new MyDialog(mContext, R.style.Dialog_My);
            View view = inflater.inflate(layoutId, null);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            lp.height = dialogHeight;
            lp.width = dialogWidth;
            dialog.addContentView(view, lp);

            ViewGroup rootView = (ViewGroup) view.findViewById(R.id.root_container);

            if (customViewRes != -1) {
                inflater.inflate(customViewRes, rootView, true);
            }
            if (customView != null) {
                rootView.addView(customView);
            }
            if (bgColor != -1) rootView.setBackgroundColor(bgColor);
            if (bgRes != -1) rootView.setBackgroundResource(bgRes);
            dialog.setDialogProperty(this);
            return dialog;
        }

        public MyDialog createNoTitle() {
            int layoutId = R.layout.dialog_no_title;
            if (dialogStyle == STYLE_MD) {
                layoutId = R.layout.dialog_no_title_md;
            }
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MyDialog dialog = new MyDialog(mContext, R.style.Dialog_My);
            View view = inflater.inflate(layoutId, null);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            lp.height = dialogHeight;
            lp.width = dialogWidth;
            dialog.addContentView(view, lp);

            View rootView = view.findViewById(R.id.root_container);
            FrameLayout customViewContainer = (FrameLayout) view.findViewById(R.id.dialog_custom_view_container);
            TextView leftBtn = (TextView) view.findViewById(R.id.dialog_cancel_btn);
            TextView rightBtn = (TextView) view.findViewById(R.id.dialog_ok_btn);
            TextView centerBtn = (TextView) view.findViewById(R.id.dialog_center_btn);
            TextView titleView = (TextView) view.findViewById(R.id.dialog_title_tv);
            titleView.setMovementMethod(ScrollingMovementMethod.getInstance());
            titleView.setGravity(titleGravity);

            if (customViewRes != -1) {
                inflater.inflate(customViewRes, customViewContainer, true);
            }
            if (customView != null) {
                customViewContainer.addView(customView);
            }
            if (bgColor != -1) rootView.setBackgroundColor(bgColor);
            if (bgRes != -1) rootView.setBackgroundResource(bgRes);
            if (mTitleText != null) titleView.setText(mTitleText);
            if (leftBtnText != null) leftBtn.setText(leftBtnText);
            if (rightBtnText != null) rightBtn.setText(rightBtnText);
            if (leftListener != null) {
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftListener.onClick(dialog);
                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightListener.onClick(dialog);
                    }
                });
            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
                centerBtn.setVisibility(View.VISIBLE);
                if (rightListener != null)
                    centerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rightListener.onClick(dialog);
                        }
                    });
            }
            boolean doubleBtn = leftListener != null;
            if (dialogStyle == STYLE_MD) {
                if (!doubleBtn) {
                    rootView.findViewById(R.id.dialog_md_line_v).setVisibility(View.GONE);
                }
            }
            dialog.setDialogProperty(this);
            return dialog;
        }

        public MyDialog createDiscStyle() {
            int layoutId = R.layout.dialog_layout_style;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MyDialog dialog = new MyDialog(mContext, R.style.Dialog_My);
            View view = inflater.inflate(layoutId, null);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            lp.height = dialogHeight;
            lp.width = dialogWidth;
            dialog.addContentView(view, lp);

            View rootView = view.findViewById(R.id.root_container);
            FrameLayout customViewContainer = (FrameLayout) view.findViewById(R.id.dialog_custom_view_container);
            TextView leftBtn = (TextView) view.findViewById(R.id.dialog_cancel_btn);
            TextView rightBtn = (TextView) view.findViewById(R.id.dialog_ok_btn);
            TextView centerBtn = (TextView) view.findViewById(R.id.dialog_center_btn);
            TextView titleView = (TextView) view.findViewById(R.id.dialog_title_tv);
            TextView bodyView = (TextView) view.findViewById(R.id.dialog_body_tv);
            titleView.setGravity(titleGravity);

            if (customViewRes != -1) {
                inflater.inflate(customViewRes, customViewContainer, true);
            }
            if (customView != null) {
                customViewContainer.addView(customView);
            }
            if (bgColor != -1) rootView.setBackgroundColor(bgColor);
            if (bgRes != -1) rootView.setBackgroundResource(bgRes);
            if (mTitleText != null) titleView.setText(mTitleText);
            if (mBodyText != null) bodyView.setText(mBodyText);
            if (leftBtnText != null) leftBtn.setText(leftBtnText);
            if (rightBtnText != null) rightBtn.setText(rightBtnText);
            if (leftListener != null) {
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftListener.onClick(dialog);
                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightListener.onClick(dialog);
                    }
                });
            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
                centerBtn.setVisibility(View.VISIBLE);
                if (rightListener != null)
                    centerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rightListener.onClick(dialog);
                        }
                    });
            }
            dialog.setDialogProperty(this);
            return dialog;
        }
    }

    private void setDialogProperty(Builder builder) {
        if (builder.homeDismiss) {
            registerReceiver();
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

    @Override
    public void dismiss() {
        super.dismiss();
        if (mReceiver != null) {
            getContext().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }
}
