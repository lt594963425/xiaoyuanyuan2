package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class SelectUpdateTextEvent {
    private String seledtedText;

    public SelectUpdateTextEvent(String seledtedText) {
        this.seledtedText = seledtedText;
    }

    public String getSeledtedText() {
        return seledtedText;
    }

    public void setSeledtedText(String seledtedText) {
        this.seledtedText = seledtedText;
    }
}
