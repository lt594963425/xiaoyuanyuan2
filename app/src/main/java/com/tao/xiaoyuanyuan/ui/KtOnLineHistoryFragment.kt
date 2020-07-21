package com.tao.xiaoyuanyuan.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tao.xiaoyuanyuan.R
import com.tao.xiaoyuanyuan.adapter.OnlineHistoryVAdapter
import com.tao.xiaoyuanyuan.base.App
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/19 0019
 * Time: 上午 11:19
 */

class KtOnLineHistoryFragment : androidx.fragment.app.Fragment() {
    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var progressStatusLlt: LinearLayout? = null
    private var statusTv: TextView? = null
    private var mOnlineHistoryVAdapter: OnlineHistoryVAdapter? = null

    private var mLineTimeBeans: List<OnLineTimeBean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_online_time, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView
        progressStatusLlt = view.findViewById(R.id.progress_status_llt) as LinearLayout
        statusTv = view.findViewById(R.id.status_tv) as TextView
        mOnlineHistoryVAdapter = OnlineHistoryVAdapter(null)
        recyclerView!!.setLayoutManager(androidx.recyclerview.widget.LinearLayoutManager(context))
        recyclerView!!.setAdapter(mOnlineHistoryVAdapter)
        statusTv!!.setOnClickListener {
        }
        initData()
    }

    private fun initData() {
        mLineTimeBeans = App.getRealmHelper().queryOnlineTimeBean()
        mOnlineHistoryVAdapter?.setNewData(mLineTimeBeans)
        progressStatusLlt?.visibility = View.GONE
        if (mLineTimeBeans!!.size > 0) {
            statusTv?.visibility = View.GONE
        } else {
            statusTv?.visibility = View.VISIBLE
        }
    }

}