package com.android.library.mvp;

/**
 * $activityName
 * 高度抽象P层接口
 *
 * @author LiuTao
 * @date 2018/7/9/009
 */

public interface MvpPresenter<V extends MvpView> {
    void attachView(V view);

    void detachView();
}
