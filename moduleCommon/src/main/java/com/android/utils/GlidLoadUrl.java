package com.android.utils;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * @ClassName: GlidLoadUrl
 * @Description: java类作用描述
 * @Author: 刘涛
 * @Date: 2019/4/18 0018 下午 13:51
 */

public class GlidLoadUrl extends GlideUrl {
    private String mUrl;

    public GlidLoadUrl(String url) {
        super(url);
        this.mUrl = url;
    }

    @Override
    public String getCacheKey() {
        return mUrl.replace(findTokenParam(), "");
    }

    private String findTokenParam() {
        String tokenParam = "";
        int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
        if (tokenKeyIndex != -1) {
            int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
            if (nextAndIndex != -1) {
                tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
            } else {
                tokenParam = mUrl.substring(tokenKeyIndex);
            }
        }
        return tokenParam;
    }

}
