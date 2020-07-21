package com.android.library.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.library.mvp.MvpCallback;
import com.android.library.mvp.MvpPresenter;
import com.android.library.mvp.MvpView;
import com.android.library.mvp.ProxyMvpCallback;


/**
 * $activityName
 * 第二重代理->目标对象->针对的是Fragment生命周期
 *
 * @author LiuTao
 * @date 2018/7/9/009
 */
public class MvpFragmentDelegatelmpl<V extends MvpView, P extends MvpPresenter<V>>
        implements MvpFragmentDelegate<V, P> {

    private String TAG = "MvpActivity";
    private ProxyMvpCallback<V, P> proxyMvpCallback;
    private MvpCallback<V, P> mvpCallback;

    public MvpFragmentDelegatelmpl(MvpCallback<V, P> callback) {
        this.mvpCallback = callback;
        if (mvpCallback == null) {
            throw new NullPointerException("不能为空");
        }
        //
        this.proxyMvpCallback = new ProxyMvpCallback<V, P>(mvpCallback);
    }


    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //绑定处理
        this.proxyMvpCallback.createPresenter();
        this.proxyMvpCallback.createView();
        this.proxyMvpCallback.attachView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");


    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");

    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        this.proxyMvpCallback.detachView();
    }

    @Override
    public void onDestroyView() {

    }
}
