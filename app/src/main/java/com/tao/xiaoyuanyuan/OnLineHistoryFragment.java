package com.tao.xiaoyuanyuan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tao.xiaoyuanyuan.adapter.OnlineHistoryVAdapter;
import com.tao.xiaoyuanyuan.base.App;
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.view.BaseLoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class OnLineHistoryFragment extends Fragment {

    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    public OnlineHistoryVAdapter mOnlineHistoryVAdapter;
    public List<OnLineTimeBean> mLineTimeBeans = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_time, null);
        unbinder = ButterKnife.bind(this, view);
        mOnlineHistoryVAdapter = new OnlineHistoryVAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mOnlineHistoryVAdapter);
        initData();
        return view;
    }

    private void initData() {
        mLineTimeBeans = App.getRealmHelper().queryOnlineTimeBean();
        mOnlineHistoryVAdapter.setNewData(mLineTimeBeans);
        if (mLineTimeBeans.size() > 0) {
            statusTv.setVisibility(View.GONE);
        } else {
            statusTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    private BaseLoadingDialog dialog;

    public void showPDLoading(String s) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new BaseLoadingDialog(getContext());
        dialog.show();
    }

    public void dismissLoading() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

}
