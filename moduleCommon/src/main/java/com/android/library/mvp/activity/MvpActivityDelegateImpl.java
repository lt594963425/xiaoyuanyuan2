package com.android.library.mvp.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.library.mvp.MvpCallback;
import com.android.library.mvp.MvpPresenter;
import com.android.library.mvp.MvpView;
import com.android.library.mvp.ProxyMvpCallback;
import com.android.library.utils.LogUtils;

/**
 * $activityName
 * 第二重代理->目标对象->针对的是Activity生命周期
 *
 * @author LiuTao
 * @date 2018/7/9/009
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 */

public class MvpActivityDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements MvpActivityDelegate<V, P> {
    private String TAG = "MvpActivityDelegateImpl";
    private ProxyMvpCallback<V, P> proxyMvpCallback;
    private MvpCallback<V, P> mvpCallback;

    public MvpActivityDelegateImpl(MvpCallback<V, P> callback) {
        this.mvpCallback = callback;
        if (mvpCallback == null) {
            throw new NullPointerException("mvpCallback不能为空");
        }
        //初始化代理对象
        this.proxyMvpCallback = new ProxyMvpCallback<>(mvpCallback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "----------onCreate----------");
        //绑定处理
        this.proxyMvpCallback.createPresenter();
        this.proxyMvpCallback.createView();
        this.proxyMvpCallback.attachView();
    }

    @Override
    public void onStart() {
        LogUtils.e(TAG, "----------onStart----------");

    }

    @Override
    public void onPause() {
        LogUtils.e(TAG, "----------onPause----------");
    }

    @Override
    public void onResume() {
        LogUtils.e(TAG, "----------onResume----------");
    }

    @Override
    public void onRestart() {
        LogUtils.e(TAG, "----------onRestart----------");
    }

    @Override
    public void onStop() {
        LogUtils.e(TAG, "----------onStop----------");
    }

    @Override
    public void onDestroy() {
        LogUtils.e(TAG, "----------onDestroy----------");
        this.proxyMvpCallback.detachView();
    }

}
