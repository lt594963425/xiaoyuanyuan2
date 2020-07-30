package com.tao.xiaoyuanyuan.recoreddhistory.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.BaseActivity;
import com.tao.xiaoyuanyuan.utils.LiveDataBus;

/**
 * Created by GraffitiViewActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023be
 * Time: 上午 9:50
 */
public class HistoryRcordActivity extends BaseActivity {

    public Fragment currentFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        setStatusBarShowHeight(Color.rgb(50, 52, 87));
        currentFragment = RcordHistoryFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, currentFragment)
                    .commitNow();
        }


        LiveDataBus.get().with("key1").observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                changeFragment1();
            }
        });

        LiveDataBus.get().with("key2").observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

                changeFragment2();
            }
        });
    }


    public void changeFragment1() {
        setStatusBarShowHeight(Color.rgb(50, 52, 87));
        switchFragment(RcordHistoryFragment.newInstance());
    }

    public void changeFragment2() {
        setStatusBarShowHeight(Color.rgb(0, 0, 0));
        switchFragment(RcordHistoryFragment2.newInstance());
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.container, targetFragment)
                    .commit();
        } else {
            transaction.hide(currentFragment)
                    .show(targetFragment)
                    .commit();
            System.out.println("添加了( ⊙o⊙ )哇");
        }
        currentFragment = targetFragment;
    }

}



