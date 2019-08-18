package com.android.utils;

import android.os.CountDownTimer;

/**
 * @ClassName: DateCountTimeUtils
 * @Description: java类作用描述
 * @Author: 刘涛
 * @Date: 2019/4/22 0022 上午 9:02
 */

public class DateCountTimeUtils {
    public DateCountTimeUtils() {
    }

    public void setCountDownTime(long timeStemp, final OnTimerListener listener) {

        CountDownTimer timer = new CountDownTimer(timeStemp, 1000) {
            @Override
            public void onTick(long l) {
                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒
                if (day == 0) {
                    if (hour == 0) {
                        if (minute == 0) {
                            listener.onTick(second + "秒");
                        } else {
                            listener.onTick(minute + "分钟" + second + "秒");
                        }
                    } else {
                        listener.onTick(hour + "小时" + minute + "分钟" + second + "秒");
                    }
                } else {
                    listener.onTick(day + "天" + hour + "小时" + minute + "分钟" + second + "秒");
                }
            }

            @Override
            public void onFinish() {
                //倒计时为0时执行此方法

            }
        };
        timer.start();
    }

    public interface OnTimerListener {
        void onTick(String d);

        void onFinish();

    }


}
