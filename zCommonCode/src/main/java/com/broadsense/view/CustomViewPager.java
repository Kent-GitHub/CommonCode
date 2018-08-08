package com.broadsense.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = true; //是否可以切换页面
    private boolean isCanTouch = false; //是否可以触摸滑动

    private RadioGroup radioGroup;

    private View mIndicatorView;

    private int pageCount;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public void setScanTouch(boolean isCanScroll) {
        this.isCanTouch = isCanScroll;
    }

    public void setIndicatorView(View view, int page) {
        mIndicatorView = view;
        pageCount = page;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        setRadioGroup(radioGroup, null);
    }

    /**
     *
     * @param radioGroup
     * @param checkedChangeListener
     */
    public void setRadioGroup(RadioGroup radioGroup, final RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        this.radioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int visibleCount = 0;
                for (int i = 0; i < group.getChildCount(); i++) {
                    View childAt = group.getChildAt(i);
                    if (childAt.isShown()) {
                        visibleCount++;
                    }
                    if (childAt.getId() == checkedId) {
                        setCurrentItem(visibleCount - 1, false);
                    }
                }
                if (checkedChangeListener != null) {
                    checkedChangeListener.onCheckedChanged(group, checkedId);
                }
            }
        });
    }

    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mIndicatorView != null) {
                    int cellWidth = getWidth() / pageCount;
                    ViewGroup.LayoutParams lp = mIndicatorView.getLayoutParams();
                    lp.width = cellWidth;
                    mIndicatorView.setLayoutParams(lp);
                    int width = lp.width;
                    int xOffset = (int) (cellWidth * (position + positionOffset) + (cellWidth - width) / 2);
                    mIndicatorView.setX(xOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (radioGroup != null) {
                    int visibleChildCount = 0;
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        View childAt = radioGroup.getChildAt(i);
                        if (childAt.isShown()) {
                            visibleChildCount++;
                            if (visibleChildCount - 1 == position
                                    && childAt instanceof RadioButton) {
                                ((RadioButton) childAt).setChecked(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //return super.onTouchEvent(arg0);
        return isCanTouch && super.onTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //return super.onInterceptTouchEvent(arg0);
        return isCanTouch && super.onInterceptTouchEvent(arg0);
    }

}
