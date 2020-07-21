package com.tao.xiaoyuanyuan.recoreddhistory.adapter;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.UIUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.recoreddhistory.bean.DateRecodBean;

import java.util.List;

/**
 * @author MiTa
 * @date 2017/11/20.
 */
public class RecordHistoryAdapter extends BaseItemDraggableAdapter<DateRecodBean, BaseViewHolder> {

    public RecordHistoryAdapter(List<DateRecodBean> list) {
        super(R.layout.item_history_record, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateRecodBean item) {
        LinearLayout layout = helper.getView(R.id.layout);
        TextView title = helper.getView(R.id.title);
        TextView time = helper.getView(R.id.time);
        TextView count_tv = helper.getView(R.id.count_tv);
        TextView time_long = helper.getView(R.id.time_long);

        ImageView image1 = helper.getView(R.id.image1);
        ImageView image2 = helper.getView(R.id.image2);
        ImageView image3 = helper.getView(R.id.image3);

        title.setText(item.getTitle());
        title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        String startTime = item.getStart_time().split("年")[1];
        String endTIme = item.getEnd_time().split("年")[1];
        time.setText(startTime + " - " + endTIme);
        count_tv.setText(item.getCount()+"w");
        time_long.setText(item.getTimeLong());
        if (helper.getAdapterPosition() % 2 == 0) {
            image1.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.person_g));
            image2.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.bean_w));
            image3.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.clock_w));
            layout.setBackgroundColor(UIUtils.getColor(R.color.white));
        } else {
            image1.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.person_w));
            image2.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.bean_g));
            image3.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.clock_g));
            layout.setBackgroundColor(UIUtils.getColor(R.color.color_f3f3f3));

        }
    }

}
