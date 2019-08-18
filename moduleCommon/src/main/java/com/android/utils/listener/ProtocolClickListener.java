package com.android.utils.listener;

import android.app.Activity;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.android.library.R;
import com.android.library.utils.PhoneUtils;
import com.android.library.utils.SpanUtils;


/**
 * $activityName
 * 我已经阅读并同意：派无界隐私协议及用户协议
 *
 * @author LiuTao
 * @date 2019/3/14/014
 */


public class ProtocolClickListener {

    public static void setProtocolTextViewClick(final Activity activity, TextView view, final Class toActivity) {
        ClickableSpan clickPrivatePolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity,toActivity));
            }
        };
        ClickableSpan clickUserPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, toActivity));
            }
        };
        //我已经阅读并同意：派无界隐私协议及用户协议
        SpanUtils.with(view).append("我已经阅读并同意：派无界")
                .append("隐私协议").setUnderline()
                .setForegroundColor(activity.getResources().getColor(R.color.color_orange))
                .setClickSpan(clickPrivatePolicySpan)
                .append("及")
                .append("用户协议").setUnderline()
                .setForegroundColor(activity.getResources().getColor(R.color.color_orange))
                .setClickSpan(clickUserPolicySpan)
                .create();


    }

    public static void setAboutUsClick(Activity activity, TextView view) {
        ClickableSpan clickPrivatePolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                PhoneUtils.dial("0734-5223695");

            }
        };
        //我已经阅读并同意：派无界隐私协议及用户协议
        SpanUtils.with(view).append("欢迎致电:")
                .append("0734-5223695").setUnderline()
                .setForegroundColor(activity.getResources().getColor(R.color.color_orange))
                .setClickSpan(clickPrivatePolicySpan)
                .setForegroundColor(activity.getResources().getColor(R.color.color_orange))
                .create();
    }
}
