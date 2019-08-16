package com.example.modulebase.base.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.utils.LogUtil;
import com.example.modulebase.BuildConfig;
import com.example.modulebase.base.CrashHandler;
import com.example.modulebase.base.IComponentApplication;
import com.example.modulebase.base.TInterceptor;
import com.example.modulebase.data.constant.NetUrls;
import com.example.modulebase.data.source.db.RealmHelper;
import com.example.modulebase.data.source.helper.DBManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class App extends TinkerApplication {
    private static App INSTANCE;
    public static RealmHelper realmHelper;


    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.modulebase.base.base.AppLike",
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
        INSTANCE = this;
        CrashHandler.getInstance().init(this);
        DBManager.getInstance().onCreate();
        //初始化数据库
        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
        realmHelper = new RealmHelper();
        initNotificationManager();
        initHttpOkgo(this);
        initRouter(this);
        LogUtil.d("App", "------------------------初始化-----------------------------------");

    }


    private void initRouter(Application myApplication) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(myApplication);
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

    private void modulesApplicationInit() {
        for (String moduleImpl : ModuleConfig.MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication) {
                    ((IComponentApplication) obj).onCreate(App.getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void initHttpOkgo(Application application) {
        TInterceptor interceptor = new TInterceptor();
        //初始化网络框架
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        //网络拦截器

        //全局的读取超时时间
        builder.readTimeout(1000, TimeUnit.SECONDS);
        //全局的写入超时时间
        builder.writeTimeout(1000, TimeUnit.SECONDS);
        //全局的连接超时时间
        builder.connectTimeout(7, TimeUnit.SECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
        OkGo.getInstance().init(application)//必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)    //全局统一缓存模式，默认不使用缓存，可以不传
                .setRetryCount(0)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);   //全局统一缓存时间，默认永不过期，可以不传

    }

    public HttpParams addHttpParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", "");
        httpParams.put("client", "");
        return httpParams;
    }

    public ObservableTransformer transformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    };

    public static RealmHelper getRealmHelper() {
        return realmHelper;
    }

    public static void setRealmHelper(RealmHelper realmHelper) {
        App.realmHelper = realmHelper;
    }
}
