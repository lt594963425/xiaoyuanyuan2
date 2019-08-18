package com.tao.xiaoyuanyuan.utils;

import com.android.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/16 0016
 * Time: 上午 9:03
 */
public class DateUitl {
    /**
     * \
     * 日期
     *
     * @param time
     * @return
     */
    public static String getformatCurrentTime(long time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//如果hh为小写 那么就搜12小时制 如果为大写 那么就是24小时制
            LogUtil.d("当前时间", " " + df.format(new Date(time)));
            return df.format(new Date(time));
        } catch (Exception e) {
            return "0000,00,00";
        }
    }

    public static String[] formatTime(long time) {
        String[] strings = new String[2];
//        time = time / 1000;//总秒数
        int h = (int) (time / 3600);
        int m = (int) (time % 3600 / 60);
        int s = (int) (time % 60); //不足60的就是秒，够60就是分
        if (h > 0) {
            strings[0] = String.format("%2d时%2d分%2d秒", h, m, s);
            strings[1] = "1";
            return strings;
        } else if (m > 0) {
            strings[0] = String.format("%2d分%2d秒", m, s);
            if (m > 30) {
                strings[1] = "1";
            } else {
                strings[1] = "0";
            }
            return strings;
        } else if (s > 0) {
            strings[0] = String.format("%2d秒", s);
            strings[1] = "0";
            return strings;
        } else {
            strings[0] = String.format("%2d秒", 0);
            strings[1] = "0";
            return strings;
        }
    }
}
