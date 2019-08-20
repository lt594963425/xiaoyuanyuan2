package com.android.utils.listener;

import android.app.Activity;

/**
 * @ClassName: FinishActivityListener
 * @Description: java类作用描述
 * @Author: 刘涛
 * @Date: 2019/4/16 0016 下午 12:07
 */

public interface ActivityListener {
    void openRightDrawer();

    void openLeftDrawer();

    Activity getActivity();
}
