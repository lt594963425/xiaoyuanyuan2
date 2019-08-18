package com.android.library.mvp;

/**
 * $activityName
 * 高度抽象V层接口
 *
 * @author LiuTao
 * @date 2018/7/9/009
 */


public interface MvpView {
    /*************加载状态配合 LoadingTip使用**************/
    void showLoading(String title);

    void stopLoading();

    void showErrorTip(String msg);

}
