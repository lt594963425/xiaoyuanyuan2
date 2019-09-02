package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class ImageServiceEvent {

    private String path;

    public ImageServiceEvent(String path) {
        this.path = path;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
