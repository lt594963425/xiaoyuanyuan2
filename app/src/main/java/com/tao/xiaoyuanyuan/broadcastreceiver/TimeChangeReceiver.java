package com.tao.xiaoyuanyuan.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.tao.xiaoyuanyuan.utils.LogUtils;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona: 时间坚监听器
 * Date: 2019/8/16 0016
 * Time: 上午 9:00
 */
public class TimeChangeReceiver extends BroadcastReceiver {
    private  IntentFilter mIntentFilter;
    public  TimeChangeReceiver timeChangeReceiver;

    public TimeChangeReceiver() {
    }

    public  void startTimeChangeReceiver(Context context) {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
        mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        context.registerReceiver(timeChangeReceiver, mIntentFilter);
    }

    public  void unregisterReceiver(Context context) {
        if (timeChangeReceiver!=null){
            context.unregisterReceiver(timeChangeReceiver);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("时间", "--------------变化--------");
        switch (intent.getAction()) {
            case Intent.ACTION_TIME_TICK:
                //每过一分钟 触发
                LogUtils.e("时间", "--------------每过一分钟 触发--------");
                break;
            case Intent.ACTION_TIME_CHANGED:
                //设置了系统时间
                LogUtils.e("时间", "--------------system time changed--------");
                break;
            case Intent.ACTION_TIMEZONE_CHANGED:
                //设置了系统时区的action
                LogUtils.e("时间", "--------------ystem time zone changed--------");
                break;
        }
    }
}
