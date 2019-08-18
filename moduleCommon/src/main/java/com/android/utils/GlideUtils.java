package com.android.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/3/21/021
 */


public class GlideUtils {
    public static void intoImage(String path, ImageView imageView,int placeholder,int errorimg) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(errorimg);
        Glide.with(UIUtils.getContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void intoBusinessHeadImage(String path, ImageView imageView,int placeholder,int errorimg) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(errorimg);
        Glide.with(UIUtils.getContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void intoAdImage(String path, ImageView imageView,int placeholder,int errorimg) {
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeholder)
                    .error(errorimg);
            Glide.with(UIUtils.getContext())
                    .load(path)
                    .apply(requestOptions)
                    .into(imageView);
        } catch (Exception e) {
            LogUtil.e("加载图片", e.toString());
            e.printStackTrace();
        }
    }
}
