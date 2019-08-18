package com.android.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/3/19/019
 */


public class PhoneUtils {

    /**
     * 判断手机号码是否正确
     */
    public static boolean isMobileNO(String phone) {
        String regex = "^((13[0-9])|(14[1])|(14[4-9])|(15[0-3])|(15[5-9])|(16[2])|(16[5-7])|(17[0-3])|(17[5-8])|(18[0-9])|(19[1|8|9]))\\d{8}$";
        if (phone.length() != 11) {
            LogUtil.d("phone","手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                LogUtil.d("phone","请填入正确的手机号");
            }
            return isMatch;
        }
    }

    public static boolean isEmail(String strEmail) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }


}
