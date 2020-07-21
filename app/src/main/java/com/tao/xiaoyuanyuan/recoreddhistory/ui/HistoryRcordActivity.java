package com.tao.xiaoyuanyuan.recoreddhistory.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.BaseActivity;
import com.tao.xiaoyuanyuan.utils.LiveDataBus;
import com.tao.xiaoyuanyuan.utils.StatusBarUtils;

/**
 * Created by GraffitiViewActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023be
 * Time: 上午 9:50
 */
public class HistoryRcordActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        setStatusBarShowHeight(Color.rgb(50, 52, 87));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RcordHistoryFragment.newInstance())
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RcordHistoryFragment.newInstance())
                .commitNow();
    }

    public void changeFragment2() {
        setStatusBarShowHeight(Color.rgb(0, 0, 0));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RcordHistoryFragment2.newInstance())
                .commitNow();
    }



}



