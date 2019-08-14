package com.tao.xiaoyuanyuan.server;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.event.BackGroundServiceEvent;
import com.tao.xiaoyuanyuan.event.SuoServerEvent;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.rxbus2.Subscribe;
import com.tao.xiaoyuanyuan.rxbus2.ThreadMode;
import com.tao.xiaoyuanyuan.utils.ToastUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;

public class BackGroundService extends Service {
    public static boolean isStarted = false;
    public static final String TAG = "LocationGroundService";
    private boolean isRun = false;
    public TextView mSuspensionTextShow;
    //文字内容
    public String mTextString = "";
    //字体类型
    public int mTextType = Typeface.NORMAL;
    //字体颜色
    public int mTextColor = Color.BLACK;
    //字体大小
    private int mTextSize = 15;
    private boolean isSuo = false;

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }

        isStarted = true;
        Log.d(TAG, "onCreate");
        isRun = false;

        setWindowView();
    }

    //服务执行的操作
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        mTextString = intent.getStringExtra("mTextString");
        mTextType = intent.getIntExtra("mTextString", Typeface.NORMAL);
        mTextColor = intent.getIntExtra("mTextColor", Color.BLACK);
        mTextSize = intent.getIntExtra("mTextSize", 15);

        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    public void setWindowView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = UIUtils.dip2Px(385);
        layoutParams.x = 300;
        layoutParams.y = 300;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_window_menu, null);
            mSuspensionTextShow = view.findViewById(R.id.suspensionText_show);
            ImageView suoIv = view.findViewById(R.id.suo_iv);
            mSuspensionTextShow.setText(mTextString);
            mSuspensionTextShow.setTypeface(Typeface.DEFAULT, mTextType);
            mSuspensionTextShow.setTextColor(mTextColor);
            mSuspensionTextShow.setTextSize(mTextSize);
            windowManager.addView(view, layoutParams);
            suoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSuo = !isSuo;
                    if (isSuo){

                    }else {
                        ToastUtils.showToast("已锁定！");
                    }
                }
            });
            view.setOnTouchListener(new FloatingOnTouchListener());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(BackGroundServiceEvent backGroundServiceEvent) {
        mTextString = backGroundServiceEvent.getString();
        mTextType = backGroundServiceEvent.getTextType();
        mTextColor = backGroundServiceEvent.getTextColor();
        mTextSize = backGroundServiceEvent.getTextSize();
        if (mSuspensionTextShow != null) {
            mSuspensionTextShow.setText(mTextString);
            mSuspensionTextShow.setTypeface(Typeface.DEFAULT, mTextType);
            mSuspensionTextShow.setTextColor(mTextColor);
            mSuspensionTextShow.setTextSize(mTextSize);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SuoServerEvent suoServerEvent) {
        isSuo = suoServerEvent.isIssuo();

    }
    //销毁服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }

    }

    //bindService()启动Service时才会调用onBind()方法
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (isSuo) {
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public boolean isSuo() {
        return isSuo;
    }

    public void setSuo(boolean suo) {
        isSuo = suo;
    }
}
