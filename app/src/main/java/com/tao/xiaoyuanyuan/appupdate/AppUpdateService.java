package com.example.modulebase.base.appupdate;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.utils.AndroidUtil;
import com.android.utils.LogUtil;
import com.android.utils.StringUtils;
import com.android.utils.ToastUtils;
import com.android.utils.UIUtils;
import com.android.view.IOSDialog;
import com.android.view.IOSDialogUtils;

import com.example.modulebase.R;
import com.example.modulebase.base.base.App;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;

import java.io.File;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * $activityName
 * APP版本更新
 *
 * @author LiuTao
 * @date 2019/3/4/004
 */


public class AppUpdateService {
    private NotificationManager notificationManager;
    private Notification notification; //下载通知进度提示
    private NotificationCompat.Builder builder;
    private boolean flag = false; //进度框消失标示 之后发送通知
    public static boolean isUpdate = false; //是否正在更新
    private int appTotalLength;
    private int appdownLength;

    private static AppUpdateService mAppUpdateService;
    private TextView mStartUpdate;
    private TextView mCancleUpdate;
    private Dialog mDialog;
    private TextView mAppUpdateTitle;
    private TextView mAppUpdateInfo;
    private TextView mAppUpdateFeature;
    private TextView mAppUpdateTime;


    public static AppUpdateService getInstance() {
        if (mAppUpdateService == null) {
            mAppUpdateService = new AppUpdateService();

        }
        return mAppUpdateService;
    }

    private AppUpdateService() {

    }


    //初始化通知
    private void initNotification(Activity activity) {
        try {
            notificationManager = (NotificationManager) App.getContext().getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = notificationManager.getNotificationChannel("updateApp");
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, UIUtils.getPackageName());
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    App.getInstance().startActivity(intent);
                    ToastUtils.showToast("请手动将通知打开");
                }
            }
            builder = new NotificationCompat.Builder(App.getInstance(), "updateApp");
            builder.setContentTitle("派无界正在下载...") //设置通知标题
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(App.getInstance().getResources(), R.mipmap.ic_launcher_round)) //设置通知的大图标
                    .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                    .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                    .setChannelId("updateApp")
                    .setAutoCancel(false);//设置通知被点击一次是否自动取消;
            notification = builder.build();

            notificationManager.notify(1, notification);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("数据异常");
        }


    }

    @SuppressLint("SetTextI18n")
    public void showDownloadAppView(final Activity activity) {
        mDialog = new Dialog(activity, R.style.UpdateDialogActivity);
        View myView = LayoutInflater.from(activity).inflate(R.layout.app_upgrade_dialog_custom, null);
        mCancleUpdate = myView.findViewById(R.id.cancel);
        mStartUpdate = myView.findViewById(R.id.start);
        mAppUpdateTitle = myView.findViewById(R.id.app_update_title);
        mAppUpdateInfo = myView.findViewById(R.id.app_update_info);
        mAppUpdateFeature = myView.findViewById(R.id.app_update_feature);
        mAppUpdateTime = myView.findViewById(R.id.app_update_time);
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        mAppUpdateTitle.setText("发现新版本");
        mAppUpdateFeature.setText(upgradeInfo.newFeature + "");
        String stringBufferinfo = "版本：" + upgradeInfo.versionName +
                "   " +
                "包大小：" + StringUtils.FormetFileSize(upgradeInfo.fileSize);
        mAppUpdateInfo.setText(stringBufferinfo);
        mAppUpdateTime.setText("更新时间：" + StringUtils.strToDate(upgradeInfo.publishTime));

        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowParams = dialogWindow.getAttributes();
        windowParams.dimAmount = 0.0f;
        dialogWindow.setAttributes(windowParams);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int mWindowWidth = (int) (displayMetrics.widthPixels * 0.7);
        mDialog.setContentView(myView, new ViewGroup.LayoutParams(mWindowWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.show();
        updateBtn(Beta.getStrategyTask());

        mStartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 是否开启通知栏权限
                if (!NotificationManagerCompat.from(App.getContext()).areNotificationsEnabled()) {
                    openNotification(activity);
                    return;
                }
                if (mDialog != null) {
                    mDialog.dismiss();
                }

                switch (Beta.getStrategyTask().getStatus()) {
                    case DownloadTask.INIT:
                    case DownloadTask.DELETED:
                    case DownloadTask.PAUSED:
                    case DownloadTask.FAILED:
                        downLoadApp(activity);
                        break;
                    case DownloadTask.COMPLETE: {
                        Beta.installApk(Beta.getStrategyTask().getSaveFile());
                    }
                    break;
                    case DownloadTask.DOWNLOADING: {
                        ToastUtils.showToast("正在下载");
                        downLoadApp(activity);
                    }
                    break;
                    default:
                        break;
                }

            }
        });
        mCancleUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                Beta.unregisterDownloadListener();

            }
        });
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                appTotalLength = (int) task.getTotalLength();
                appdownLength = (int) task.getSavedLength();
                int per = (int) (((appdownLength * 1.0) / (appTotalLength * 1.0)) * 100);
                LogUtil.e("下载进度", "下载进度:" + appdownLength + "====" + appTotalLength);
                builder.setProgress(appTotalLength, appdownLength, false);
                builder.setContentText("下载进度:" + per + "%");
                builder.setChannelId("updateApp");
                notification = builder.build();
                notificationManager.notify(1, notification);
            }

            @Override
            public void onCompleted(DownloadTask task) {
                appTotalLength = (int) task.getTotalLength();
                appdownLength = (int) task.getSavedLength();
                int per = (int) (((appdownLength * 1.0) / (appTotalLength * 1.0)) * 100);
                builder.setProgress(appTotalLength, appdownLength, false);
                LogUtil.e("下载进度", "下载进度:" + appdownLength + "====" + appTotalLength);
                builder.setContentText("下载进度:" + per + "%");
                builder.setChannelId("updateApp");
                notification = builder.build();
                notificationManager.notify(1, notification);
                deleteNotification();
                //installApk(task.getSaveFile());
                Beta.installApk(task.getSaveFile());
                Beta.unregisterDownloadListener();

            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                appTotalLength = (int) task.getTotalLength();
                appdownLength = (int) task.getSavedLength();
                int per = (int) (((appdownLength * 1.0) / (appTotalLength * 1.0)) * 100);
                builder.setProgress(appTotalLength, appdownLength, false);
                builder.setContentText("下载进度:" + per + "%");
                builder.setChannelId("updateApp");
                notification = builder.build();
                notificationManager.notify(1, notification);


            }
        });

    }

    private void openNotification(final Activity activity) {
        IOSDialogUtils iosDialogUtils = IOSDialogUtils.getInstance();
        iosDialogUtils.clickDialogIos(activity, "消息通知未打开，请手动打开", "取消", "去打开",
                new IOSDialogUtils.OnButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(IOSDialog dialog) {
                        dialog.dismiss();
                        AndroidUtil.openAppSetting(activity);
                    }

                    @Override
                    public void onCancelButtonClick(IOSDialog dialog) {
                        dialog.dismiss();
                    }
                });
    }

    private void downLoadApp(Activity activity) {
        ToastUtils.showToast("加入到后台下载");
        initNotification(activity);
        Beta.startDownload();
        //StatusBarUtils.setNotificationBarVisibility(activity, true);
        mDialog.dismiss();
    }

    public void deleteNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //关闭通知通道
            notificationManager.deleteNotificationChannel("updateApp");
        } else {
            notificationManager.cancel(1);
        }
    }

    /**
     * @param apkFile
     * @return
     * @Description 安装apk
     */
    private void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(App.getInstance(), "com.mvp.lt.airlineview.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        App.getInstance().startActivity(intent);
    }

    /**
     * 获取本地版本号
     *
     * @return
     */
    public int getVersionCode() {
        PackageManager packageManager = App.getInstance().getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public void updateBtn(DownloadTask task) {
        switch (task.getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED: {
                mStartUpdate.setText("立即更新");
            }
            break;
            case DownloadTask.COMPLETE: {
                mStartUpdate.setText("安装");
            }
            break;
            case DownloadTask.DOWNLOADING: {
                mStartUpdate.setText("暂停");
            }
            break;
            case DownloadTask.PAUSED: {
                mStartUpdate.setText("继续下载");
            }
            break;
            default:
                break;
        }
    }
}
