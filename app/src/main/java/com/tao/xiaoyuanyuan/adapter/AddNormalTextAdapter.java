package com.tao.xiaoyuanyuan.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.App;
import com.tao.xiaoyuanyuan.db.entity.NormalTextBean;
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
public class AddNormalTextAdapter extends BaseItemDraggableAdapter<NormalTextBean, BaseViewHolder> {
    private int selectedId = -1;

    public AddNormalTextAdapter(List<NormalTextBean> list) {
        super(R.layout.adapter_item_mormal_text_view, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, NormalTextBean item) {
        TextView textView = (TextView) helper.getView(R.id.title_time_tv);
        LinearLayout normalTextLly = (LinearLayout) helper.getView(R.id.normal_text_lly);
        textView.setText(item.getText());
        if (selectedId != -1) {
            if (selectedId == helper.getLayoutPosition()) {
                normalTextLly.setBackground(App.getContext().getResources().getDrawable(R.drawable.bg_ad_tab_selected));
                textView.setTextColor(Color.WHITE);
            } else {
                normalTextLly.setBackground(App.getContext().getResources().getDrawable(R.drawable.bg_ad_tab_unselected));
                textView.setTextColor(Color.BLACK);
            }
        } else {
            normalTextLly.setBackground(App.getContext().getResources().getDrawable(R.drawable.bg_ad_tab_unselected));
            textView.setTextColor(Color.BLACK);
        }
    }

    public void setSelectedId(int position) {
        if (selectedId != -1) {
            if (selectedId == position) {
                selectedId = -1;
            } else {
                selectedId = position;
            }
        } else if (position == -1) {
            selectedId = position;
        } else {
            selectedId = position;
        }
        notifyDataSetChanged();
    }

    public int getSelectedId() {
        return selectedId;
    }


}
