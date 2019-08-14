package com.tao.xiaoyuanyuan.event;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/14 0014
 * Time: 上午 11:34
 */
public class BackGroundServiceEvent {
    private String mString;
    private int textSize;
    private int textType;
    private int mTextColor;

    public BackGroundServiceEvent() {
    }

    public BackGroundServiceEvent(String string, int textSize, int textType, int mTextColor) {
        mString = string;
        this.textSize = textSize;
        this.textType = textType;
        this.mTextColor = mTextColor;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }
}
