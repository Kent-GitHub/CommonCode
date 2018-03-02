package com.broadsense.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * * Created by Kent_Lee on 2017/7/7.
 */

public class LoadingView extends RelativeLayout {
    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private ProgressBar pb;
    private TextView tv;

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_loading, this, true);
        pb = (ProgressBar) findViewById(R.id.pb_loading);
        tv = (TextView) findViewById(R.id.tv_loading);
    }

    public void showProgressVisibility(int visible) {
        pb.setVisibility(visible);
    }

    public void showTvVisibility(int visible) {
        tv.setVisibility(visible);
    }
}
