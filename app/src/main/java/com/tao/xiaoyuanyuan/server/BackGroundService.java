package com.tao.xiaoyuanyuan.server;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.LogUtil;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.App;
import com.tao.xiaoyuanyuan.broad.BootCompleteReceive;
import com.tao.xiaoyuanyuan.db.RealmHelper;
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean;
import com.tao.xiaoyuanyuan.db.entity.RealmLikeBean;
import com.tao.xiaoyuanyuan.event.BackGroundServiceEvent;
import com.tao.xiaoyuanyuan.event.ShowEvent;
import com.tao.xiaoyuanyuan.event.ShowWidthEvent;
import com.tao.xiaoyuanyuan.event.SuoEvent;
import com.tao.xiaoyuanyuan.event.SuoServerEvent;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.rxbus2.Subscribe;
import com.tao.xiaoyuanyuan.rxbus2.ThreadMode;
import com.tao.xiaoyuanyuan.utils.DateUitl;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.RxTimerUtil;
import com.tao.xiaoyuanyuan.utils.ToastUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;

import java.util.Date;

public class BackGroundService extends Service {
    public static boolean isStarted = false;
    public static final String TAG = "LocationGroundService";
    public TextView mSuspensionTextShow;
    //文字内容
    public String mTextString = "";
    //字体类型
    public int mTextType = Typeface.NORMAL;
    //字体颜色
    public int mTextColor = Color.BLACK;
    //字体大小
    private int mTextSize = 15;
    private int width = 385;
    private boolean isSuo = false;
    public View mView;
    public LinearLayout mContentTextLly;
    public ImageView mSuoIv;
    public TextView mTotalTimeTv;

    public RxTimerUtil mRxTimerUtil;

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }
        isStarted = true;
        Log.d(TAG, "onCreate");
        setWindowView();
        BootCompleteReceive screenBroadcastReceiver = new BootCompleteReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        getApplicationContext().registerReceiver(screenBroadcastReceiver, filter);
    }

    //服务执行的操作
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        mTextString = intent.getStringExtra("mTextString");
        mTextType = intent.getIntExtra("mTextType", Typeface.NORMAL);
        mTextColor = intent.getIntExtra("mTextColor", Color.BLACK);
        mTextSize = intent.getIntExtra("mTextSize", 15);
        width = intent.getIntExtra("mWidth", width);
        showFloatingWindow();
        startTime();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTime() {

        if (mRxTimerUtil == null) {
            mRxTimerUtil = new RxTimerUtil();
        }
        mRxTimerUtil.startTimer(App.getInstance().getOnlineTime(), new RxTimerUtil.RxTimerNextListener() {
            @Override
            public void onTimerNext(long number) {
                if (mTotalTimeTv != null) {
                    App.getInstance().setOnlineTime(number);
                    mTotalTimeTv.setText("在线:" + DateUitl.formatTime(number)[0]);
                    OnLineTimeBean onLineTimeBean = new OnLineTimeBean();
                    onLineTimeBean.setId(DateUitl.getformatCurrentTime(System.currentTimeMillis()));
                    onLineTimeBean.setOnLinetime(App.getInstance().getOnlineTime());
                    App.getRealmHelper().insertOnlineTimeBean(onLineTimeBean);
                }
            }
        });
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
        layoutParams.gravity = Gravity.CENTER | Gravity.TOP;
        // 可在全屏幕布局, 不受状态栏影响 // 最初不可获取焦点, 这样不影响底层应用接收触摸事件
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 300;
        layoutParams.y = 300;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setEnableDrag(boolean isDrag) {
        if (layoutParams != null) {
            if (isDrag) {
                // 可在全屏幕布局, 不受状态栏影响 // 最初不可获取焦点, 这样不影响底层应用接收触摸事件
                layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            } else {
                // 可在全屏幕布局, 不受状态栏影响 // 最初不可获取焦点, 这样不影响底层应用接收触摸事件
                layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//是否能透传点击事件
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            }
        } else {
            setWindowView();
        }
        if (mView != null) {
            windowManager.removeView(mView);
            windowManager.addView(mView, layoutParams);
        } else {
            showFloatingWindow();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            mView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_window_menu, null);
            mContentTextLly = mView.findViewById(R.id.content_text_lly);
            //滚动文字
            mSuspensionTextShow = mView.findViewById(R.id.suspensionText_show);
            //锁定
            mSuoIv = mView.findViewById(R.id.suo_iv);
            //在线时间
            mTotalTimeTv = mView.findViewById(R.id.total_time_tv);
            mTotalTimeTv.setText("在线:" + DateUitl.formatTime(App.getInstance().getOnlineTime())[0]);
            mSuspensionTextShow.setText(mTextString);
            mSuspensionTextShow.setTypeface(Typeface.DEFAULT, mTextType);
            mSuspensionTextShow.setTextColor(mTextColor);
            mSuspensionTextShow.setTextSize(mTextSize);
            mContentTextLly.getLayoutParams().width = UIUtils.dip2Px(width);
            mContentTextLly.requestLayout();
            windowManager.addView(mView, layoutParams);
            //下排

            mSuoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSuo = !isSuo;
                    if (isSuo) {
                        //锁定 不能滑动
                        ToastUtils.showToast("已锁定🔒");
                        mSuoIv.setVisibility(View.GONE);
                        setEnableDrag(false);
                    } else {
                        ToastUtils.showToast("已解锁🔒");
                        mSuoIv.setVisibility(View.VISIBLE);
                    }
                    RxBus.getDefault().post(new SuoEvent(isSuo));
                }
            });
            mView.setOnTouchListener(new FloatingOnTouchListener());
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

    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SuoServerEvent suoServerEvent) {
        isSuo = suoServerEvent.isIssuo();
        setEnableDrag(!isSuo);
        if (isSuo) {//锁定
            ToastUtils.showToast("已锁定🔒");
            if (mSuoIv != null) {
                mSuoIv.setVisibility(View.GONE);
            }
        } else {//解锁
            ToastUtils.showToast("已解锁🔒");
            if (mSuoIv != null) {
                mSuoIv.setVisibility(View.VISIBLE);
            }

        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ShowWidthEvent showWidthEvent) {
        width = showWidthEvent.getWidth();
        if (mContentTextLly != null) {
            mContentTextLly.getLayoutParams().width = UIUtils.dip2Px(width);
            mContentTextLly.requestLayout();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ShowEvent showEvent) {
        if (showEvent.isShow()) {
            showFloatView();
            startTime();
        } else {
            if (mRxTimerUtil != null) {
                mRxTimerUtil.clearTimer();
            }
            OnLineTimeBean onLineTimeBean = new OnLineTimeBean();
            onLineTimeBean.setId(DateUitl.getformatCurrentTime(System.currentTimeMillis()));
            onLineTimeBean.setOnLinetime(App.getInstance().getOnlineTime());
            App.getRealmHelper().insertOnlineTimeBean(onLineTimeBean);
            hideFloatView();
        }
    }

    //销毁服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "------------------onDestroy----------------------");
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
        mRxTimerUtil.clearTimer();

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


    /**
     * 隐藏悬浮View
     */
    public void hideFloatView() {
        if (windowManager != null && mView != null && mView.isShown()) {
            mView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示悬浮View
     */
    public void showFloatView() {
        if (windowManager != null && mView != null && !mView.isShown()) {
            mView.setVisibility(View.VISIBLE);
        }
    }
}
