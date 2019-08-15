package com.tao.xiaoyuanyuan.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Android Studio.
 * User: Administrator
 * Name: RobRedPackage
 * functiona:TextView跑马灯效果
 * Date: 2019/5/5 0005
 * Time: 下午 14:39
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        //设置单行
        setSingleLine();
        //设置Ellipsize
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //获取焦点
        setFocusable(true);
        //走马灯的重复次数，-1代表无限重复
        setMarqueeRepeatLimit(-1);
        //强制获得焦点
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

//    @Override
//    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
//        if (focused) {
//            super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        }
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        if (hasWindowFocus)
//            super.onWindowFocusChanged(hasWindowFocus);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
