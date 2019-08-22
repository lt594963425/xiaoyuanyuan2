package com.tao.xiaoyuanyuan.broad;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import com.android.utils.LogUtil;
import com.tao.xiaoyuanyuan.ui.UserActivity;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/22 0022
 * Time: 下午 17:55
 */
@SuppressLint("InvalidWakeLockTag")
public class BootCompleteReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.e("BootCompleteReceive", "广播Action = " + action);
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            LogUtil.e("BootCompleteReceive", "锁屏");
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            LogUtil.e("BootCompleteReceive", "解锁");
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            LogUtil.e("BootCompleteReceive", "开屏");
        }
        //屏幕唤醒
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");
        wl.acquire();
        //屏幕解锁
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock K1 = km.newKeyguardLock("StartupReceiver");
        Intent intent1 = new Intent(context, UserActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
