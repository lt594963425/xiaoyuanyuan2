package com.tao.xiaoyuanyuan.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期工具
 * Created by caoyu on 2018/4/23/023.
 */

public class DateUtils {
    private final String TAG = "DateUtils";
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private static ArrayList<String> pmtypelist = new ArrayList<>();
    private static String province;
    private static String city;


    public static void showDatePicker(Context context, final TextView textView, EditText editText) {
        hindInput(context, editText);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(DateUtils.getTime(date));
            }
        })//默认全部显示
                .setContentTextSize(16)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public static void datePicker(Context context, final TextView textView, EditText editText) {
        hindInput(context, editText);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (comparaData(date)) {
                    ToastUtils.showToast("截止时间必须大于今天！");
                } else {
                    textView.setText(DateUtils.getTime(date));
                }
            }
        })//默认全部显示
                .setContentTextSize(16)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字

//                .setRangDate(Calendar.getInstance(),null)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public static void showDateTrack(Context context, final TextView textView, EditText editText) {
        hindInput(context, editText);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(DateUtils.getTime(date));
            }
        })//默认全部显示
                .setContentTextSize(16)//滚轮文字大小
                .isDialog(true)
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setRangDate(Calendar.getInstance(),null)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    public static void showDayDatePicker(Context context, final TextView textView) {
//        hindInput(context);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(DateUtils.getAllTime(date));
            }
        })//默认全部显示
                .setType(new boolean[]{true, true, true, true, true, true})
                .setContentTextSize(16)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public static String getTime(Date date, boolean[] booleans) {
        if (booleans[1]) {
            //可根据需要自行截取数据显示
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        } else {
            //可根据需要自行截取数据显示
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            return format.format(date);
        }
    }

    public static String getTime(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static String getSTime(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }

    public static Calendar getCalendar(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //可根据需要自行截取数据显示
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getData(String dateString) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getAllTime(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getTime(Date date, String sdf) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(sdf);
        return format.format(date);
    }

    public static Date strToDate(String szBeginTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(szBeginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判斷是否時過期時間
     *
     * @param targetDate
     * @return true 过期 ， false 没有过期
     */
    public static boolean comparaData(String targetDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String currentTime = df.format(d);
        try {
            Date targetTime = df.parse(targetDate);
            Date curentTime = df.parse(currentTime);
            if (targetTime.getTime() <= curentTime.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判斷是否時過期時間
     *
     * @param targetDate
     * @return true 过期 ， false 没有过期
     */
    public static boolean comparaData(Date targetDate) {
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        if (targetDate.getTime() <= d.getTime()) {
            return true;
        }
        return false;
    }


    private static void initPMTypeData() {
        pmtypelist = new ArrayList<>();
        pmtypelist.add("文明施工");
        pmtypelist.add("工程实体");
        pmtypelist.add("安全生产");
        pmtypelist.add("其他");
    }

    /**
     * 获取时间 yyyy-MM-dd_HH-mm-ss
     */

    public static String getDate() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//如果hh为小写 那么就搜12小时制 如果为大写 那么就是24小时制
            Date day = new Date();
            return df.format(day);
        } catch (Exception e) {
            return "0000-00-00 00:00";
        }

    }

    /**
     * 15点上下随机波动
     *
     * @param day
     * @return 起始和结束时间 相差2~3小时
     */
    public static String[] getB1Time(int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String[] times = new String[2];
        String startTime;
        String endTime;
        Calendar calendar = Calendar.getInstance();
        Random rand = new Random();
        //小时
        int shour = 0;
        int defHuor = 15;
        int randHNum = rand.nextInt(5);//[0，4]
        int fuhao = 1 + rand.nextInt(2);//[0，1]--->[1,2]
        if (fuhao == 1) {
            shour = defHuor - randHNum;
        } else {
            shour = defHuor + randHNum;
        }
        //分钟
        int sminute = 1 + rand.nextInt(59);
        //秒
        int ssecond = 1 + rand.nextInt(59);
        calendar.add(Calendar.DATE, -day); //向前走一天
        calendar.set(Calendar.HOUR_OF_DAY, shour); //向前走一个
        calendar.add(Calendar.MINUTE, sminute); //向前走一天
        calendar.add(Calendar.SECOND, ssecond); //向前走一天
        Date date = calendar.getTime();
        startTime = df.format(date);
        shour = calendar.get(Calendar.HOUR_OF_DAY) + 2;
        calendar.set(Calendar.HOUR_OF_DAY, shour);
        sminute = 1 + rand.nextInt(59);
        calendar.add(Calendar.MINUTE, sminute);
        ssecond = 1 + rand.nextInt(59);
        calendar.add(Calendar.SECOND, ssecond);
        Date date2 = calendar.getTime();
        endTime = df.format(date2);
        times[0] = startTime;
        times[1] = endTime;
        return times;
    }

    /**
     * 获取时间戳
     */

    public static long getDate(String strTime) {
        try {

            long returnMillis = 0;
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//如果hh为小写 那么就搜12小时制 如果为大写 那么就是24小时制
            Date d = null;
            d = df.parse(strTime);
            returnMillis = d.getTime();
            return returnMillis;
        } catch (Exception e) {
            return 0;
        }

    }

    public static void hindInput(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd HH-mm-ss
     * 第二个大
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /**
     * 获取时间 yyyy-MM-dd_HH-mm-ss
     */

    public static String getStarandDate(long time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//如果hh为小写 那么就搜12小时制 如果为大写 那么就是24小时制
            return df.format(new Date(time));
        } catch (Exception e) {
            return "0000-00-00 00:00";
        }

    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd HH-mm-ss
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger2(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /**
     * 通过日期判断是周几
     *
     * @throws ParseException
     */
    public static String DateToDay(String daydate) {
        String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(daydate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(String date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        return date.equals(nowDay);

    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }


    /**
     * 获取前一天的时间 yyyy-MM-dd
     * return:String
     */
    public static String getBeforeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取当天的年月日 yyyy-MM-dd
     * return:String
     */
    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 判断addtime是否在七天之内
     *
     * @param addtime
     * @return
     */
    public static boolean isLatestWeek(String addtime) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date now1;
        Date now2;
        try {
            now1 = format1.parse(getBeforeNow());
            now2 = format1.parse(addtime);
            Calendar calendar = Calendar.getInstance();  //得到日历
            calendar.setTime(now1);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -7);  //设置为7天前
            Date before7days = calendar.getTime();   //得到7天前的时间
            if (before7days.getTime() <= now2.getTime()) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     *                 返回类型 ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    public static long getTimeDifference(long starTime, long endTime) {
        long s = 0;
        long diff = endTime - starTime;

        long day = diff / (24 * 60 * 60 * 1000);
        long hour = (diff / (60 * 60 * 1000) - day * 24);
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        long hour1 = diff / (60 * 60 * 1000);
        String hourString = hour1 + "";
        long min1 = ((diff / (60 * 1000)) - hour1 * 60);
        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        return s;
    }

    public static String getTimeStringDifference(long starTime, long endTime) {
        String s = "";
        long diff = endTime - starTime;
        long day = diff / (24 * 60 * 60 * 1000);
        long hour = (diff / (60 * 60 * 1000) - day * 24);
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long ss = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
//        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        long hour1 = diff / (60 * 60 * 1000);
        String hourString = hour1 + "";
        long min1 = ((diff / (60 * 1000)) - hour1 * 60);
//        System.out.println(day + "天" + hour + "小时" + min + "分" + ss + "秒");
        if (day > 0) {
            s = day + "天" + hour + "小时" + min + "分钟";
        } else if (hour > 0) {
            s = hour + "小时" + min + "分钟";
        } else if (min > 0) {
            s = min + "分钟";
        } else {
            s = ss + "秒";
        }
        return s;
    }

    /**
     * 根据毫秒返回时分秒
     *
     * @param time
     * @return
     */

    public static String getFormatHMS(long time) {
        time = time / 1000;//总秒数
        int h = (int) (time / 3600);
        int m = (int) (time % 3600 / 60);
        int s = (int) (time % 60);
        if (h > 0) {
            return String.format("%02d:%02d:%02d", h, m, s);
        } else {
            return String.format("%02d:%02d", m, s);
        }

    }

    /**
     * 根据毫秒返回时分秒
     *
     * @param time
     * @return 时间 是否大于30分钟
     */

    public static String[] getCHFormatHMS(long time) {
        String[] strings = new String[2];
        time = time / 1000;//总秒数

        int h = (int) (time / 3600);
        int m = (int) (time % 3600 / 60);
        int s = (int) (time % 60); //不足60的就是秒，够60就是分
        if (h > 0) {
            strings[0] = String.format("%2d时%2d分%2d秒", h, m, s);
            strings[1] = "1";
            return strings;
        } else if (m > 0) {
            strings[0] = String.format("%2d分%2d秒", m, s);
            if (m > 30) {
                strings[1] = "1";
            } else {
                strings[1] = "0";
            }
            return strings;
        } else if (s > 0) {
            strings[0] = String.format("%2d秒", s);
            strings[1] = "0";
            return strings;
        } else {
            strings[0] = "0";
            strings[1] = "0";
            return strings;
        }
    }

    /**
     * 根据秒返回时分秒
     *
     * @param time
     * @return 时间 是否大于30分钟
     */

    public static String[] getSecondToFormat(long time) {
        String[] strings = new String[2];
        int h = (int) (time / 3600);
        int m = (int) (time % 3600 / 60);
        int s = (int) (time % 60); //不足60的就是秒，够60就是分
        if (h > 0) {
            strings[0] = String.format("%02d时%02d分%02d秒", h, m, s);
            strings[1] = "1";
            return strings;
        } else if (m > 0) {
            strings[0] = String.format("%02d分%02d秒", m, s);
            if (m > 30) {
                strings[1] = "1";
            } else {
                strings[1] = "0";
            }
            return strings;
        } else if (s > 0) {
            strings[0] = String.format("%02d分%02d秒", m, s);
            strings[1] = "0";
            return strings;
        } else {
            strings[0] = String.format("%02d分%02d秒", m, s);
            strings[1] = "0";
            return strings;
        }
    }

    /**
     * 根据秒返回时分秒
     *
     * @param time
     * @return 时间 是否大于30分钟
     */

    public static String[] getSecondToFormatHMS(long time) {
        String[] strings = new String[2];
        int h = (int) (time / 3600);
        int m = (int) (time % 3600 / 60);
        int s = (int) (time % 60); //不足60的就是秒，够60就是分
        if (h > 0) {
            strings[0] = String.format("%02d:%02d:%02d", h, m, s);
            strings[1] = "1";
            return strings;
        } else if (m > 0) {
            strings[0] = String.format("%02d:%02d", m, s);
            if (m > 30) {
                strings[1] = "1";
            } else {
                strings[1] = "0";
            }
            return strings;
        } else if (s > 0) {
            strings[0] = String.format("%02d:%02d", m, s);
            strings[1] = "0";
            return strings;
        } else {
            strings[0] = String.format("%02d:%02d", m, s);
            strings[1] = "0";
            return strings;
        }
    }

    public static String getNumberFromString(String s) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(s);
        return matcher.replaceAll("").trim();
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timeSSdate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * HH:mm:ss
     *
     * @param timetemp
     * @return
     */
    public static String getHMS(long timetemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timetemp);
        SimpleDateFormat fmat = new SimpleDateFormat("HH:mm:ss");
        String time = fmat.format(calendar.getTime());
        return time;
    }

    //十位时间戳字符串转小时分钟秒
    public static String Hourmin(long time) {
        long longMinutes = time / (60);   //根据时间差来计算分钟数
        long longS = time - longMinutes * 60;   //根据时间差来计算秒数
        return longMinutes + ":" + String.format("%02d", longS) + "m(分钟)";
    }


}
