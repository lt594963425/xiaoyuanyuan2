package com.tao.xiaoyuanyuan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.StatusBarUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;
import com.tao.xiaoyuanyuan.view.XToolbar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 * 独编译运行时调用
 */
public class UserActivity extends AppCompatActivity {
    private XToolbar mXToolbar;
    private RxPermissions mRxPermissions;
    private String TAG = "UserActivity";

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
        mXToolbar.setTitle(UIUtils.getResources().getString(R.string.app_name));
        requestPermissions();
        CrashHandler.getInstance().init(this);
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtils.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
