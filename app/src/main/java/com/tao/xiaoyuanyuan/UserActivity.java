package com.tao.xiaoyuanyuan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.xiaoyuanyuan.utils.StatusBarUtils;
import com.tao.xiaoyuanyuan.view.XToolbar;


/**
 * 独编译运行时调用
 */
public class UserActivity extends AppCompatActivity {
    private XToolbar mXToolbar;

    /**
     * 隐藏状态栏的高度
     */
    public void setStatusBarTrans() {
        //StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        //侵入状态栏
        StatusBarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0), true);
    }

    /**
     * 隐藏状态栏的高度
     */
    public void setStatusBarTrans(final boolean isDecor) {
        //StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        //侵入状态栏
        StatusBarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0), isDecor);
    }

    /**
     * 保留状态栏高度,状态栏设置为透明
     */
    public void setStatusBarShowHeight() {
        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
    }

    /**
     * 保留状态栏高度,状态栏设置为透明
     */
    public void setStatusBarShowHeight(@ColorRes int id) {
        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, id));
    }

    /**
     * 状态栏黑白模式
     * true 白色 false 黑
     */
    public void setStatusBarLightMode(boolean isLightMode) {
        StatusBarUtils.setStatusBarLightMode(this, isLightMode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mXToolbar = findViewById(R.id.toolbar);
        mXToolbar.setTitle("小圆圆专用");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
