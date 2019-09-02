package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/15 0015
 * Time: 上午 10:13
 */
public class ImageShowEvent {
    boolean isShowpicture;

    public ImageShowEvent() {
    }

    public ImageShowEvent(boolean isShowpicture) {
        this.isShowpicture = isShowpicture;
    }

    public boolean isShowpicture() {
        return isShowpicture;
    }

    public void setShowpicture(boolean showpicture) {
        isShowpicture = showpicture;
    }
}
