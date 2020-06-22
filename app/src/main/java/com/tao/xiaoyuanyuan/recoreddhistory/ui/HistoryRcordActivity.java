package com.tao.xiaoyuanyuan.recoreddhistory.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.library.utils.StatusBarUtils;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.BaseActivity;
import com.tao.xiaoyuanyuan.recoreddhistory.adapter.RecordHistoryAdapter;
import com.tao.xiaoyuanyuan.recoreddhistory.bean.DateRecodBean;

import java.util.ArrayList;
import java.util.List;

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

        setStatusBarShowHeight(Color.rgb(50,52,87));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RcordHistoryFragment.newInstance())
                    .commitNow();
        }

    }
}



