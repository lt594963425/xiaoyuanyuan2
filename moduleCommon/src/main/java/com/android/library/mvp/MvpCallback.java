package com.android.library.mvp;

/**
 * $activityName
 * 第一重代理->目标接口->抽象解绑和绑定(MvpCallback)
 *
 * @author LiuTao
 * @date 2018/7/9/009
 */

public interface MvpCallback<V extends MvpView, P extends MvpPresenter<V>> {

    //创建Presenter
    P createPresenter();

    void setPresenter(P presenter);

    P getPresneter();

    //创建View
    V createView();

    void setMvpView(V view);

    V getMvpView();
}
