package com.tao.xiaoyuanyuan.db.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona: 常用语
 * Date: 2019/8/20 0020
 * Time: 上午 10:22
 */
public class NormalTextBean extends RealmObject {
    @PrimaryKey
    public String text ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
