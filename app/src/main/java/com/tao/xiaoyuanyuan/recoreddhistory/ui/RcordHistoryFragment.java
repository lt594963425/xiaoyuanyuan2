package com.tao.xiaoyuanyuan.recoreddhistory.ui;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.recoreddhistory.adapter.RecordHistoryAdapter;
import com.tao.xiaoyuanyuan.recoreddhistory.bean.DateRecodBean;
import com.tao.xiaoyuanyuan.utils.DateUtils;
import com.tao.xiaoyuanyuan.utils.DialogUtils;
import com.tao.xiaoyuanyuan.utils.LiveDataBus;
import com.tao.xiaoyuanyuan.view.BaseDialog;

import java.util.Random;


public class RcordHistoryFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private TextView addDataTv;
    private DateRecodBean dateRecodBean;
    private RecordHistoryAdapter recordHistoryAdapter;
    private View mNotDataView;
    private TextView changeTv;

    public static RcordHistoryFragment newInstance() {
        return new RcordHistoryFragment();
    }

    public void initBaseView() {
        mNotDataView = getLayoutInflater().inflate(R.layout.base_empty_view, null, false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hitory, null);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        addDataTv = view.findViewById(R.id.add_data_tv);
        changeTv = view.findViewById(R.id.change_tv);
        initBaseView();
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().finish();
            }
        });
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().finish();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recordHistoryAdapter = new RecordHistoryAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recordHistoryAdapter.disableDragItem();
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(recordHistoryAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        recyclerView.setAdapter(recordHistoryAdapter);
        recordHistoryAdapter.setEmptyView(mNotDataView);
        recordHistoryAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                if (recordHistoryAdapter != null) {
                    dateRecodBean = recordHistoryAdapter.getData().get(pos);
                }
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });

        recyclerView.setAdapter(recordHistoryAdapter);

        addDataTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAutoData();
                //添加一一条数据
//                DateRecodBean dateRecodBean = new DateRecodBean();
//                dateRecodBean.setTitle("欢迎来到我的直播间");
//                DialogUtils.getInstance().showUpdateItemDialog(getActivity(), dateRecodBean, new DialogUtils.OnDataClickListener() {
//                    @Override
//                    public void onPositiveButtonClick(DateRecodBean dateRecodBean, BaseDialog dialog) {
//                        recordHistoryAdapter.addData(dateRecodBean);
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onCancelButtonClick(BaseDialog dialog) {
//                        dialog.dismiss();
//
//                    }
//
//
//                });
            }
        });
        recordHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DateRecodBean dateRecodBean = (DateRecodBean) adapter.getData().get(position);
                //修改
                DialogUtils.getInstance().showUpdateItemDialog(getActivity(), dateRecodBean, new DialogUtils.OnDataClickListener() {

                    @Override
                    public void onPositiveButtonClick(DateRecodBean dateRecodBean, BaseDialog dialog) {
                        recordHistoryAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelButtonClick(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                });

            }
        });
        getAutoData();

        changeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.get().with("key2").postValue("");
            }
        });
        return view;
    }

    private void initData() {
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年12月01日 17:02", "2020年12月01日 20:11", "4.5", "3小时9分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月30日 22:32", "2020年12月01日 00:53", "3.5", "2小时21分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月29日 09:58", "2020年11月29日 12:09", "4.3", "2小时10分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月28日 20:00", "2020年11月28日 22:40", "3.3", "2小时39分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月27日 14:11", "2020年11月27日 17:23", "4.2", "3小时11分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月26日 17:16", "2020年11月26日 19:35", "4.3", "2小时19分钟"));
        recordHistoryAdapter.addData(new DateRecodBean("啊哈", "2020年11月25日 13:18", "2020年11月25日 16:42", "4.3", "3小时24分钟"));
    }

    String[] number = new String[]{"3.5", "3.6", "3.7", "3.8", "3.9", "4.0", "4.1", "4.2", "4.3", "4.4", "4.5"};

    /**
     * 自动获取数据
     */
    public void getAutoData() {
        Random rand = new Random();
        recordHistoryAdapter.getData().clear();
        for (int i = 1; i < 8; i++) {
            DateRecodBean dateRecodBean = new DateRecodBean();
            String[] date = DateUtils.getB1Time(i);
            dateRecodBean.setStart_time(date[0]);
            dateRecodBean.setEnd_time(date[1]);
            String diffTime = DateUtils.getTimeStringDifference(DateUtils.getDate(date[0]), DateUtils.getDate(date[1]));
            dateRecodBean.setTimeLong(diffTime);
            int randHNum = rand.nextInt(number.length);
            dateRecodBean.setCount(number[randHNum]);
            dateRecodBean.setTitle("欢迎来到我的直播间");
            recordHistoryAdapter.addData(dateRecodBean);
        }
    }

}
