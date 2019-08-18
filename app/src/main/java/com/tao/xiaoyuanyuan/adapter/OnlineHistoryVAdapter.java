package com.tao.xiaoyuanyuan.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean;
import com.tao.xiaoyuanyuan.utils.DateUitl;

import java.util.List;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/16 0016
 * Time: 上午 11:00
 */
public class OnlineHistoryVAdapter extends BaseQuickAdapter<OnLineTimeBean, BaseViewHolder> {

    public OnlineHistoryVAdapter(List<OnLineTimeBean> list) {
        super(R.layout.adapter_item_online_view, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OnLineTimeBean item) {
        ((TextView) helper.getView(R.id.title_time_tv)).setText(item.getId());
        ((TextView) helper.getView(R.id.time)).setText(DateUitl.formatTime(item.getOnLinetime())[0]);
    }
}
