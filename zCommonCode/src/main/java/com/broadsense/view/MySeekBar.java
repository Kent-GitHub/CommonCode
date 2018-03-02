package com.broadsense.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * packageName：com.broadsense.newpine.set.ui.custom
 * ProjectName：5_Set
 * Description：
 * Author：zhouxian
 * Date：2016/10/18 10:53
 */

public class MySeekBar extends RelativeLayout implements View.OnClickListener {
    private SeekBar mSeekBar;
    private Button addBtn;
    private Button reduceBtn;
    private MySeekBarChangeListener mListener;
    private volatile boolean isUser = false;

    private int clickProgress = 1;

    @Override
    public void onClick(View v) {
        isUser = true;
        int i = v.getId();
        if (i == R.id.seekbar_btn_add) {
            mSeekBar.incrementProgressBy(clickProgress);
            //LogUtils.i("progress = " + mSeekBar.getProgress());

        } else if (i == R.id.seekbar_btn_minus) {
            mSeekBar.incrementProgressBy(-clickProgress);
            //LogUtils.i("progress = " + mSeekBar.getProgress());

        }
    }

    public interface MySeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
    }

    public MySeekBar(Context context) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setMySeekBarListener(MySeekBarChangeListener listener) {
        mListener = listener;
    }

    /**
     * @param isEnable 是否可以点击
     */
    public void setEnableClick(boolean isEnable) {
        mSeekBar.setEnabled(isEnable);
        addBtn.setEnabled(isEnable);
        reduceBtn.setEnabled(isEnable);
        if (mSeekBar.getProgress() == 0) {
            mSeekBar.setProgress(1);
            mSeekBar.setProgress(0);
//            LogUtils.i("seekbar getProgress == " + 0);
//            BaseApplication.getHdler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    LogUtils.i("seekbar delayed 50 ms setProgress 0");
//                    mSeekBar.setProgress(1);
//                    mSeekBar.setProgress(0);
//                }
//            }, 50);
        }
    }

    /**
     * 点击 + - 改变的进度
     */
    public void setClickProgress(int clickProgress) {
        this.clickProgress = clickProgress;
    }

    /**
     * @param max 最大进度
     */
    public void setMaxProgress(int max) {
        mSeekBar.setMax(max);
    }

    /**
     * @param progress 当前
     */
    public void setCurProgress(int progress) {
        mSeekBar.setProgress(progress);
    }

    private void initView(Context context) {
        @SuppressLint("InflateParams") View view = View.inflate(context, R.layout.seekbar, this);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar_adjust);
        addBtn = (Button) view.findViewById(R.id.seekbar_btn_add);
        reduceBtn = (Button) view.findViewById(R.id.seekbar_btn_minus);
        addBtn.setOnClickListener(this);
        reduceBtn.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
    }

    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mListener != null) {
                mListener.onProgressChanged(seekBar, progress, fromUser || isUser);
                isUser = false;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


}
