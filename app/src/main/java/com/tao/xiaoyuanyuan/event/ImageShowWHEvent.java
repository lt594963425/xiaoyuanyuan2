package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/15 0015
 * Time: 上午 10:13
 */
public class ImageShowWHEvent {
    int widthH;

    public ImageShowWHEvent() {
    }

    public ImageShowWHEvent(int widthH) {
        this.widthH = widthH;
    }

    public int getWidthH() {
        return widthH;
    }

    public void setWidthH(int widthH) {
        this.widthH = widthH;
    }
}
