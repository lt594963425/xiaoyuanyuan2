package com.tao.xiaoyuanyuan.base;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.support.multidex.MultiDex;


import com.android.utils.GPSUtils;
import com.tao.xiaoyuanyuan.db.RealmHelper;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class App extends TinkerApplication {
    private static App INSTANCE;
    public static RealmHelper realmHelper;
    public  long mOnlineTime;

    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.tao.xiaoyuanyuan.base.AppLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        INSTANCE = this;
        CrashHandler.getInstance().init(this);
        //初始化数据库
        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
        realmHelper = new RealmHelper();
        initNotificationManager();
        LogUtils.d("App", "------------------------初始化-----------------------------------");


    }



    public static App getInstance() {
        return INSTANCE;
    }


    public static Context getContext() {
        return INSTANCE.getApplicationContext();
    }


    private void initNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "updateApp";
            String channelName = "升级程序";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static RealmHelper getRealmHelper() {
        return realmHelper;
    }

    public static void setRealmHelper(RealmHelper realmHelper) {

        App.realmHelper = realmHelper;
    }

    public long getOnlineTime() {
        return mOnlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        mOnlineTime = onlineTime;
    }
}
