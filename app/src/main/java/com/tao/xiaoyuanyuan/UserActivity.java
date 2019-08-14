package com.tao.xiaoyuanyuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.xiaoyuanyuan.view.XToolbar;


/**
 * 独编译运行时调用
 */
public class UserActivity extends AppCompatActivity {
    private XToolbar mXToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mXToolbar = findViewById(R.id.toolbar);
        mXToolbar.setTitle("小圆圆专用");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
