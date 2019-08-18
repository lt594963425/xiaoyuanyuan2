package com.tao.xiaoyuanyuan.db.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona: 在线时间
 * Date: 2019/8/16 0016
 * Time: 上午 10:37
 */
public class OnLineTimeBean extends RealmObject {
    /**
     * 日期作为ID 2019-8-16
     */
    @PrimaryKey
    public String id;
    /**
     * 累计在线时间
     */
    public Long onLinetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOnLinetime() {
        return onLinetime;
    }

    public void setOnLinetime(Long onLinetime) {
        this.onLinetime = onLinetime;
    }

}
