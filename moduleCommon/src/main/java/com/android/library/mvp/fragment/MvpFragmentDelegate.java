package com.android.library.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.library.mvp.MvpPresenter;
import com.android.library.mvp.MvpView;


/**
 * $activityName
 * 写框架(重写Framework层)
 * @author LiuTao
 * @date 2018/7/9/009
 */

public interface MvpFragmentDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    //修改为Fragment生命周期方法
    public void onAttach(Context context);

    public void onCreate(Bundle savedInstanceState);

    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState);

    public void onStart();

    public void onPause();

    public void onResume();

    public void onStop();

    public void onDestroy();

    public void onDestroyView();

    public void onDetach();


}