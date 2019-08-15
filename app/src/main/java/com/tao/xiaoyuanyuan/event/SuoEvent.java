package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class SuoEvent {
    private boolean issuo;

    public SuoEvent(boolean issuo) {
        this.issuo = issuo;
    }

    public boolean isIssuo() {
        return issuo;
    }

    public void setIssuo(boolean issuo) {
        this.issuo = issuo;
    }
}
