package com.android.utils;

import android.content.Context;


import com.android.bean.AddressBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AddressDataHelper {
    private static List<AddressBean> beans;

    public static List<AddressBean> getAddress(Context context) {
        if (beans == null) {
            InputStreamReader inputStreamReader;
            try {
                inputStreamReader = new InputStreamReader(context.getAssets().open(
                        "address.json"), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStreamReader.close();
                bufferedReader.close();
                beans = FastJsonUtils.toList(stringBuilder.toString(), AddressBean.class);
                return beans;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        } else {
            return beans;
        }
    }
}
