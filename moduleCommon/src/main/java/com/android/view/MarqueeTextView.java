package com.android.view;

import android.content.Context;
import android.util.AttributeSet;

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
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
