package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/15 0015
 * Time: 上午 10:13
 */
public class ShowEvent {
    boolean isShow ;

    public ShowEvent() {
    }

    public ShowEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
