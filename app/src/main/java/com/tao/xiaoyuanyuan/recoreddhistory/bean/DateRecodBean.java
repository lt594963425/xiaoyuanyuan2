package com.tao.xiaoyuanyuan.recoreddhistory.bean;

/**
 * Created by LiuTao on 2019/11/18 0018 上午 11:24
 * functiona:
 */
public class DateRecodBean {

    String title="" ;
    String start_time="";
    String end_time="";
    String count="";
    String timeLong="";

    public DateRecodBean() {
    }



    public DateRecodBean(String title, String start_time, String end_time, String count, String timeLong) {
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.count = count;
        this.timeLong = timeLong;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }
}
