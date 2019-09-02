package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class ImageTypeEvent {

    private boolean isCircle;

    public ImageTypeEvent() {

    }

    public ImageTypeEvent(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }
}
