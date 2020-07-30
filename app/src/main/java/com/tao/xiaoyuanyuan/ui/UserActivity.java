package com.tao.xiaoyuanyuan.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.utils.GPSUtils;
import com.android.utils.LogUtil;
import com.android.utils.listener.ActivityListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.adapter.AddNormalTextAdapter;
import com.tao.xiaoyuanyuan.base.App;
import com.tao.xiaoyuanyuan.base.AppLike;
import com.tao.xiaoyuanyuan.base.BaseActivity;
import com.tao.xiaoyuanyuan.broadcastreceiver.TimeChangeReceiver;
import com.tao.xiaoyuanyuan.db.entity.NormalTextBean;
import com.tao.xiaoyuanyuan.event.SelectUpdateTextEvent;
import com.tao.xiaoyuanyuan.recoreddhistory.ui.HistoryRcordActivity;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.server.BackGroundService;
import com.tao.xiaoyuanyuan.utils.AndroidUtil;
import com.tao.xiaoyuanyuan.utils.DateUtils;
import com.tao.xiaoyuanyuan.utils.DialogUtils;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;
import com.tao.xiaoyuanyuan.view.BaseDialog;
import com.tao.xiaoyuanyuan.view.XToolbar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


/**
 * 独编译运行时调用
 */
public class UserActivity extends BaseActivity implements ActivityListener {
    @BindView(R.id.text_type_0)
    RadioButton textType0;
    @BindView(R.id.text_type_1)
    RadioButton textType1;
    @BindView(R.id.text_type_2)
    TextView textType2;
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
    @BindView(R.id.text_recycler)
    RecyclerView textRecycler;
    @BindView(R.id.add_normal_text)
    LinearLayout addNormalText;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.progress_status_llt)
    LinearLayout progressStatusLlt;
    private XToolbar mXToolbar;
    private RxPermissions mRxPermissions;
    private String TAG = "UserActivity";
    public TimeChangeReceiver mTimeChangeReceiver;
    public Fragment currentFragment;
    public AddNormalTextAdapter mAddNormalTextAdapter;


    public NormalTextBean mNormalTextBean;
    public UserFragment mUserFragment;
    public KtOnLineHistoryFragment mKtOnLineHistoryFragment;
    private String uuid;

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

        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade(true, false);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        mTimeChangeReceiver = new TimeChangeReceiver();
        mTimeChangeReceiver.startTimeChangeReceiver(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        mUserFragment = new UserFragment();
        currentFragment = mUserFragment;
        beginTransaction.replace(R.id.user_fragment, currentFragment);
        //最后一步 记得commit
        beginTransaction.commit();

        textType0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                mXToolbar.setTitle(UIUtils.getResources().getString(R.string.app_name));
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
                }
                switchFragment(mUserFragment);

            }
        });
        textType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                mXToolbar.setTitle(textType1.getText());
                if (mKtOnLineHistoryFragment == null) {
                    mKtOnLineHistoryFragment = new KtOnLineHistoryFragment();
                }
                switchFragment(mKtOnLineHistoryFragment);

            }
        });
        textType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, HistoryRcordActivity.class));
            }
        });
        textType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXToolbar.setTitle(textType3.getText());
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        textType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXToolbar.setTitle(textType4.getText());
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

//        switchFragment(new UserFragment());

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                LogUtil.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
                if (mAddNormalTextAdapter != null) {
                    mNormalTextBean = mAddNormalTextAdapter.getData().get(pos);
                }
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                LogUtil.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                if (mAddNormalTextAdapter != null) {
//                    mAddNormalTextAdapter.getData().get(pos).deleteFromRealm();
//                }
//                holder.setTextColor(R.id.tv, Color.BLACK);

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                LogUtil.d(TAG, "View Swiped: " + pos);
                if (mNormalTextBean != null) {
                    App.getRealmHelper().deleteNormalTextBean(mNormalTextBean.getText());
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder,
                                          float dX, float dY, boolean isCurrentlyActive) {
//                canvas.drawColor(ContextCompat.getColor(UserActivity.this, R.color.color_light_blue));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };
        //常用语
        mAddNormalTextAdapter = new AddNormalTextAdapter(null);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAddNormalTextAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(textRecycler);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mAddNormalTextAdapter.enableSwipeItem();
        mAddNormalTextAdapter.setOnItemSwipeListener(onItemSwipeListener);
        textRecycler.setLayoutManager(new LinearLayoutManager(this));
        textRecycler.setAdapter(mAddNormalTextAdapter);
        statusTv.setVisibility(View.VISIBLE);
        mAddNormalTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAddNormalTextAdapter.setSelectedId(position);
                RxBus.getDefault().post(new SelectUpdateTextEvent(mAddNormalTextAdapter.getData().get(position).getText()));
            }
        });

        //查询数据哭
        addDisposable(RxView.clicks(addNormalText)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        DialogUtils.getInstance().showInputDialog(UserActivity.this,
                                new DialogUtils.OnButtonInputeClickListener() {
                                    @Override
                                    public void onPositiveButtonClick(BaseDialog dialog, String msg) {
                                        dialog.dismiss();
                                        statusTv.setVisibility(View.GONE);
                                        NormalTextBean normalTextBean = new NormalTextBean();
                                        normalTextBean.setText(msg);
                                        mAddNormalTextAdapter.addData(0, normalTextBean);
                                        textRecycler.scrollToPosition(0);
                                        App.getRealmHelper().insertNormalTextBean(normalTextBean);
                                    }

                                    @Override
                                    public void onCancelButtonClick(BaseDialog dialog) {
                                        dialog.dismiss();
                                    }
                                });
                    }
                }));

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                if (view.getId() == R.id.contain_right_llt) {
                    LogUtil.e("菜单", "----------右菜单-------");
                    mAddNormalTextAdapter.setSelectedId(-1);
                    progressStatusLlt.setVisibility(View.VISIBLE);
                    mAddNormalTextAdapter.setNewData(App.getRealmHelper().queryNormalTextBean());
                    if (mAddNormalTextAdapter.getData().size() > 0) {
                        statusTv.setVisibility(View.GONE);
                        progressStatusLlt.setVisibility(View.GONE);
                    } else {
                        progressStatusLlt.setVisibility(View.GONE);
                        statusTv.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                if (view.getId() == R.id.contain_right_llt) {
                    LogUtil.e("菜单", "----------右菜单-------");
                    mAddNormalTextAdapter.setSelectedId(-1);
                }

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        uuid = AndroidUtil.getUUID();
        version.setText("版本：" + AndroidUtil.getLocalVersionName() + "\n" + AppLike.APP_CHANNEL + "\n" + uuid);

        getTime();
    }

    /**
     * 推荐使用第一种方式
     */
    public void getTime() {
        long time1 = SystemClock.elapsedRealtime();
        long time2 = SystemClock.currentThreadTimeMillis();
//        long time2 = System.currentTimeMillis();

//        1970-01-11 08-22-17
//        1970-01-01 08-00-00
//        1970-01-09 18-42-08
        LogUtil.e("时间", DateUtils.getStarandDate(time1));
        LogUtil.e("时间", DateUtils.getStarandDate(time2));
        LogUtil.e("时间", DateUtils.getStarandDate(SystemClock.uptimeMillis()));

    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.DISABLE_KEYGUARD,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            GPSUtils.getInstance(UserActivity.this).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                                @Override
                                public void onLocationResult(Location location) {
                                    LogUtil.e("位置", location.getLatitude() + "--" + location.getLongitude() + "---" + location.getAltitude());
                                    LogUtil.e("位置", location.getExtras() + "--" + location.getLongitude());

                                }

                                @Override
                                public void OnLocationChange(Location location) {
                                    LogUtil.e("位置", location.getLatitude() + "--" + location.getLongitude() + "---" + location.getAltitude());
                                    LogUtil.e("位置", location.getExtras() + "--" + location.getLongitude());
                                }
                            });

                            uuid = AndroidUtil.getUUID();
                            version.setText("版本：" + AndroidUtil.getLocalVersionName() + "\n" + AppLike.APP_CHANNEL + "\n" + uuid);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mUserFragment != null) {
            mUserFragment.onActivityResult(requestCode, resultCode, data);
        }

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
        dispose();
        LogUtils.i(TAG, "------------------onDestroy()方法--------------");
        super.onDestroy();
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.user_fragment, targetFragment)
                    .commit();
        } else {
            transaction.hide(currentFragment)
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
                    mXToolbar.setTitle(UIUtils.getResources().getString(R.string.app_name));
                    switchFragment(new UserFragment());
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void openRightDrawer() {

        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void openLeftDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
