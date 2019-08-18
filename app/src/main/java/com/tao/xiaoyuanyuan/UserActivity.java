package com.tao.xiaoyuanyuan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.utils.LogUtil;
import com.android.utils.StringUtils;
import com.tao.xiaoyuanyuan.base.BaseActivity;
import com.tao.xiaoyuanyuan.broadcastreceiver.TimeChangeReceiver;
import com.tao.xiaoyuanyuan.server.BackGroundService;
import com.tao.xiaoyuanyuan.utils.AndroidUtil;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;
import com.tao.xiaoyuanyuan.view.XToolbar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


/**
 * 独编译运行时调用
 */
public class UserActivity extends BaseActivity {
    @BindView(R.id.text_type_0)
    RadioButton textType0;
    @BindView(R.id.text_type_1)
    RadioButton textType1;
    @BindView(R.id.text_type_2)
    RadioButton textType2;
    @BindView(R.id.text_type_3)
    RadioButton textType3;
    @BindView(R.id.text_type_4)
    RadioButton textType4;
    @BindView(R.id.rg_Orientation)
    RadioGroup rgOrientation;
    @BindView(R.id.contain_right_llt)
    LinearLayout containRightLlt;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.version)
    TextView version;
    private XToolbar mXToolbar;
    private RxPermissions mRxPermissions;
    private String TAG = "UserActivity";
    public TimeChangeReceiver mTimeChangeReceiver;
    public Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "------------------onCreate()方法--------------");
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        mXToolbar = findViewById(R.id.toolbar);
        mXToolbar.setTitle(UIUtils.getResources().getString(R.string.app_name));
        mXToolbar.setNavigationIcon(null);
        requestPermissions();
        version.setText("版本：" + AndroidUtil.getLocalVersionName());
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade(true, false);
            }
        });
        mTimeChangeReceiver = new TimeChangeReceiver();
        mTimeChangeReceiver.startTimeChangeReceiver(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        currentFragment = new UserFragment();
        beginTransaction.replace(R.id.user_fragment, currentFragment);
        //最后一步 记得commit
        beginTransaction.commit();

        textType0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new UserFragment());
            }
        });
        textType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new OnLineHistoryFragment());
            }
        });
//        switchFragment(new UserFragment());
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtils.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(TAG, "------------------onStop()方法--------------");

    }

    @Override
    protected void onDestroy() {
        if (mTimeChangeReceiver != null) {
            mTimeChangeReceiver.unregisterReceiver(this);
        }
        Intent intent = new Intent(this, BackGroundService.class);
        stopService(intent);
        LogUtils.i(TAG, "------------------onDestroy()方法--------------");
        super.onDestroy();
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.user_fragment, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
            System.out.println("添加了( ⊙o⊙ )哇");
        }
        currentFragment = targetFragment;
    }

    /**
     * 按返回键,增加退出提示
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer((GravityCompat.START));
            } else {
                if (currentFragment instanceof UserFragment) {
                    finish();
                } else {
                    switchFragment(new UserFragment());
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
