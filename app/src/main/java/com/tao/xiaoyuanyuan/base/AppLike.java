package com.tao.xiaoyuanyuan.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.meituan.android.walle.WalleChannelReader;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.appupdate.AppUpdateService;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.ToastUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.tinker.entry.DefaultApplicationLike;

import java.util.Locale;


public class AppLike extends DefaultApplicationLike {
    public static final String APP_ID = "d8f4d9438a";
    public String APP_CHANNEL = "官方"; // TODO 自定义渠道
    private static  String TAG = "AppLike";

    public AppLike(Application application, int tinkerFlags,
                   boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                   long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        // TODO: 安装tinker
        Beta.installTinker(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buglyInit();
    }

    @SuppressLint("NewApi")
    private void buglyInit() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 30 * 1000;
        Beta.initDelay = 5 * 1000;
        Beta.largeIconId = R.mipmap.ic_launcher;
        Beta.smallIconId = R.mipmap.ic_launcher;
        Beta.defaultBannerId = R.mipmap.ic_launcher;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.showInterruptedStrategy = true;
        Beta.upgradeDialogLayoutId = R.layout.app_upgrade_dialog;
        Beta.tipsDialogLayoutId = R.layout.app_update_tips_dialog;
        Beta.enableNotification = true;
        Beta.autoDownloadOnWifi = true;
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "onCreate");
                // 注：可通过这个回调方式获取布局的控件，如果设置了id，可通过findViewById方式获取，如果设置了tag，可以通过findViewWithTag，具体参考下面例子:

                // 通过id方式获取控件，并更改imageview图片
                ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                imageView.setImageResource(R.mipmap.ic_launcher);

                // 通过tag方式获取控件，并更改布局内容
                TextView textView = (TextView) view.findViewWithTag("textview");
                textView.setText("my custom text");

                // 更多的操作：比如设置控件的点击事件
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplication(), OtherActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "---onStart---");
            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "onResume");
            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "onPause");
            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "onStop");
            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
                LogUtils.e(TAG, "onDestory");
            }
        };

        /*在application中初始化时设置监听，监听策略的收取*/
        Beta.upgradeListener = new UpgradeListener() {
            /**
             * 接收到更新策略
             * @param ret  0:正常 －1:请求失败
             * @param strategy 更新策略
             * @param isManual true:手动请求 false:自动请求
             * @param isSilence true:不弹窗 false:弹窗
             * @return 是否放弃SDK处理此策略，true:SDK将不会弹窗，策略交由app自己处理
             */
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                LogUtils.e(TAG, "---onUpgrade---");
                if (strategy != null) {
                    Beta.getUpgradeInfo();
                    AppUpdateService.getInstance().showDownloadAppView(AppManager.getAppManager().currentActivity());
                } else {
                    UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
                    if (upgradeInfo != null) {
                        LogUtils.e(TAG, "---onUpgrade---本地：" + upgradeInfo.versionName);
                    }
                }
            }
        };

        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                if (isManual) {

                    LogUtils.e(TAG, "检测到新版本");
                }
                //EventBus.getDefault().post(new AppUpdateEvent());
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                if (isManual) {
                    ToastUtils.showToast("网络连接失败");
                }

            }

            @Override
            public void onUpgrading(boolean isManual) {
                if (isManual) {
                    ToastUtils.showToast(getApplication(), "正在检测更新...", 500);
                }
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                LogUtils.e(TAG, "下载完成" + b);
            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                if (isManual) {
                    ToastUtils.showToast("已经是最新版本");
                }
            }
        };
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                LogUtils.e(TAG, "补丁下载地址:" + patchFile);

            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                LogUtils.e(TAG, "补丁下载中:" + String.format(Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));

            }

            @Override
            public void onDownloadSuccess(String msg) {
                LogUtils.e(TAG, "补丁下载成功");
                // Toast.makeText(getApplication(), "补丁下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailure(String msg) {
                LogUtils.e(TAG, "补丁下载失败:" + msg);
                // Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onApplySuccess(String msg) {
                LogUtils.e(TAG, "补丁应用成功");
                // Toast.makeText(getApplication(), "补丁应用成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                LogUtils.e(TAG, "补丁应用失败:" + msg);
                // Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {

            }
        };
        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        //  todo 多渠道需求塞入
        APP_CHANNEL = WalleChannelReader.getChannel(getApplication());
//        Bugly.setAppChannel(getApplication(), channel);
        Bugly.setAppChannel(getApplication(), APP_CHANNEL);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId 1638ccad67
        //App渠道
        Bugly.init(getApplication(), APP_ID, false);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(
            Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }
}
