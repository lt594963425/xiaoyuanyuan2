package com.tao.xiaoyuanyuan.base;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.StatusBarUtils;
import com.tao.xiaoyuanyuan.view.BaseLoadingDialog;
import com.tao.xiaoyuanyuan.view.XToolbar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * $activityName
 *
 * @author LiuTao
 * @date 2018/7/17/017
 */


public class BaseActivity extends AppCompatActivity {
    private XToolbar mToolbar;

    protected XToolbar getToolbar() {
        return mToolbar;
    }

    public void initToolbar() {
        mToolbar = (XToolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    public void setToolbarTitle(CharSequence title) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.setTitle(title);
    }

    public void setInflateMenu(@MenuRes int resId) {
        mToolbar.inflateMenu(resId);
    }

    public void setInflateMenu(@MenuRes int resId, Toolbar.OnMenuItemClickListener itemClickListener) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.inflateMenu(resId);
        mToolbar.setOnMenuItemClickListener(itemClickListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        compositeDisposable = new CompositeDisposable();
        LogUtils.i("BaseActivity", "------------------onCreate()方法--------------");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        LogUtils.i("BaseActivity", "------------------onDestroy()方法--------------");
    }

    /**
     * 隐藏状态栏的高度
     */
    public void setStatusBarTrans() {
        //StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        //侵入状态栏
        StatusBarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0), true);
    }

    /**
     * 是否隐藏状态栏的高度
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
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT);

    }

    /**
     * 保留状态栏高度,状态栏设置颜色
     */
    public void setStatusBarShowHeight( int id) {
        StatusBarUtils.setStatusBarColor(this, id);
    }

    /**
     * 状态栏黑白模式
     * true 白色 false 黑
     */
    public void setStatusBarLightMode(boolean isLightMode) {
        StatusBarUtils.setStatusBarLightMode(this, isLightMode);
    }

    private BaseLoadingDialog dialog;

    public void showPDLoading(String s) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new BaseLoadingDialog(this);
        dialog.show();
    }

    public void dismissLoading() {
        if (!isFinishing()) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {

        if (compositeDisposable != null) compositeDisposable.dispose();
    }
    public void setAdapterStatusBar(View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = com.android.library.utils.StatusBarUtils.getStatusBarHeight();
            view.setLayoutParams(layoutParams);
        } else if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = com.android.library.utils.StatusBarUtils.getStatusBarHeight();
            view.setLayoutParams(layoutParams);
        } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = com.android.library.utils.StatusBarUtils.getStatusBarHeight();
            view.setLayoutParams(layoutParams);
        }
    }

}
