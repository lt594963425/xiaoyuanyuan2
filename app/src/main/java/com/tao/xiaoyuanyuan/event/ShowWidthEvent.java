package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/15 0015
 * Time: 上午 10:13
 */
public class ShowWidthEvent {
    int width ;

    public ShowWidthEvent() {
    }

    public ShowWidthEvent(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
