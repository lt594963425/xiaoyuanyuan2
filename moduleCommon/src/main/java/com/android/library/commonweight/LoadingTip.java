package com.android.library.commonweight;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.R;
import com.android.library.utils.LogUtils;


/**
 * des:加载页面内嵌提示
 * 加载状态 四种状态
 */
public class LoadingTip extends LinearLayout {

    private ImageView img_tip_logo;
    private ImageView progress;
    private TextView tv_tips;
    private TextView bt_operate;
    private String errorMsg;
    private onReloadListener onReloadListener;
    private AnimationDrawable anim;

    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态
    public static enum LoadStatus {
        sereverError, error, empty, loading, finish,
        attention_empty, collection_empty, browseHistory_empty, myadvert_empty
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.dialog_loading_tip, this);
        img_tip_logo = (ImageView) findViewById(R.id.img_tip_logo);
        progress = (ImageView) findViewById(R.id.progress);
        progress.setImageResource(R.drawable.loading_frame_animation);
        anim = (AnimationDrawable) progress.getDrawable();
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        bt_operate = (TextView) findViewById(R.id.bt_operate);
        //重新尝试
        bt_operate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReloadListener != null) {
                    onReloadListener.reload();
                }
            }
        });
        setVisibility(View.GONE);
    }

    public void setTips(String tips) {
        if (tv_tips != null) {
            tv_tips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     *
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus) {
        switch (loadStatus) {
            case empty:
                bt_operate.setVisibility(INVISIBLE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                tv_tips.setText("");
                img_tip_logo.setImageResource(R.drawable.no_data);
                break;
            case attention_empty:
                bt_operate.setVisibility(INVISIBLE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                tv_tips.setText("");
                img_tip_logo.setImageResource(R.drawable.gauznhu);
                break;
            case collection_empty:
                bt_operate.setVisibility(INVISIBLE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                tv_tips.setText("");
                img_tip_logo.setImageResource(R.drawable.no_shoucang);
                break;
            case browseHistory_empty:
                bt_operate.setVisibility(INVISIBLE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                tv_tips.setText("");
                img_tip_logo.setImageResource(R.drawable.liulan);
                break;
            case myadvert_empty:
                bt_operate.setVisibility(INVISIBLE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                tv_tips.setText("");
                img_tip_logo.setImageResource(R.drawable.ganggao);
                break;
            case sereverError:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                bt_operate.setVisibility(VISIBLE);
                bt_operate.setText("重新尝试");
                img_tip_logo.setImageResource(R.drawable.ic_wrong);
                break;
            case error:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                stop();
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText("");
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.no_wifi);
                bt_operate.setText("重新尝试");
                bt_operate.setVisibility(VISIBLE);
                break;
            case loading:
                bt_operate.setVisibility(GONE);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                start();
                tv_tips.setText(getContext().getText(R.string.loading).toString());
                LogUtils.d("loading", "------loading--------");
                break;
            case finish:
                bt_operate.setVisibility(GONE);
                setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                stop();
                LogUtils.d("loading", "------finish--------");
                break;
        }
    }

    public void start() {
        // 开始播放
        anim.start();
    }

    public void stop() {
        //停止播放
        anim.stop();
    }

    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    /**
     * 重新尝试接口
     */
    public interface onReloadListener {
        void reload();
    }


}

