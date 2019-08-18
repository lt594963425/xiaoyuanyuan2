package com.tao.xiaoyuanyuan.db;


import com.android.utils.LogUtil;
import com.tao.xiaoyuanyuan.db.entity.GoldManagerBean;
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean;
import com.tao.xiaoyuanyuan.db.entity.ReadStateBean;
import com.tao.xiaoyuanyuan.db.entity.RealmLikeBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by codeest on 16/8/16.
 */

public class RealmHelper implements DBHelper {

    public static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;


    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * 增加 阅读记录
     *
     * @param id 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    @Override
    public void insertNewsId(int id) {
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }


    /**
     * 查询 阅读记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean queryNewsId(int id) {
        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean item : results) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 增加 在线记录
     *
     * @param bean
     */
    @Override
    public void insertOnlineTimeBean(OnLineTimeBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 查询 在线记录
     */

    public List<OnLineTimeBean> queryOnlineTimeBean() {
        mRealm.beginTransaction();
        RealmResults<OnLineTimeBean> realmLikeBeans = mRealm.where(OnLineTimeBean.class).findAll();
        mRealm.commitTransaction();
        return mRealm.copyFromRealm(realmLikeBeans);
    }

    /**
     * 查询 在线记录
     */

    public OnLineTimeBean queryOnlineTimeBeanByDate(String id) {
        LogUtil.e("查询", id);
        OnLineTimeBean data = mRealm.where(OnLineTimeBean.class).equalTo("id", id).findFirst();
        if (data != null) {
            LogUtil.e("查询", data.getOnLinetime() + "");
            return data;
        } else {
            LogUtil.e("查询", "没有查到---------------");
            return null;
        }

    }

    /**
     * 增加 收藏记录
     *
     * @param bean
     */
    @Override
    public void insertLikeBean(RealmLikeBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 删除 收藏记录
     *
     * @param id
     */
    @Override
    public void deleteLikeBean(String id) {
        RealmLikeBean data = mRealm.where(RealmLikeBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    /**
     * 查询 收藏记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean queryLikeId(String id) {
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
        for (RealmLikeBean item : results) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<RealmLikeBean> getLikeList() {
        //使用findAllSort ,先findAll再result.sort无效
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll().sort("time");

        return mRealm.copyFromRealm(results);
    }

    /**
     * 修改 收藏记录 时间戳以重新排序
     *
     * @param id
     * @param time
     * @param isPlus
     */
    @Override
    public void changeLikeTime(String id, long time, boolean isPlus) {
        RealmLikeBean bean = mRealm.where(RealmLikeBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (isPlus) {
            bean.setTime(++time);
        } else {
            bean.setTime(--time);
        }
        mRealm.commitTransaction();
    }

    /**
     * 更新 掘金首页管理列表
     *
     * @param bean
     */
    @Override
    public void updateGoldManagerList(GoldManagerBean bean) {
        GoldManagerBean data = mRealm.where(GoldManagerBean.class).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }

    /**
     * 获取 掘金首页管理列表
     *
     * @return
     */
    @Override
    public GoldManagerBean getGoldManagerList() {
        GoldManagerBean bean = mRealm.where(GoldManagerBean.class).findFirst();
        if (bean == null)
            return null;
        return mRealm.copyFromRealm(bean);
    }
}
