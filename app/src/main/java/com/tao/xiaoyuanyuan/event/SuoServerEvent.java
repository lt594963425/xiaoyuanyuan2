package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class SuoServerEvent {
    private boolean issuo;

    public SuoServerEvent(boolean issuo) {
        this.issuo = issuo;
    }

    public SuoServerEvent() {
    }

    public boolean isIssuo() {
        return issuo;
    }

    public void setIssuo(boolean issuo) {
        this.issuo = issuo;
    }
}
