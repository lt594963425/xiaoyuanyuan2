package com.tao.xiaoyuanyuan.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.utils.TextUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.recoreddhistory.bean.DateRecodBean;
import com.tao.xiaoyuanyuan.view.BaseDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: xiaoyuanyuan
 * functiona:
 * Date: 2019/8/16 0016
 * Time: 上午 9:55
 */
public class DialogUtils {

    public static DialogUtils getInstance() {
        return new DialogUtils();
    }

    public void showDialog(Context context, String msg, OnButtonClickListener onClickListener) {
        BaseDialog dialog = new BaseDialog(context, R.layout.base_dialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onPositiveButtonClick(dialog);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onCancelButtonClick(dialog);
            }
        });
    }

    public void showInputDialog(Context context, OnButtonInputeClickListener onClickListener) {
        BaseDialog dialog = new BaseDialog(context, R.layout.base_inpute_dialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        EditText inputTvMsg = (EditText) dialog.findViewById(R.id.input_tv_msg);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputTvMsg.getText())) {
                    ToastUtils.showToast("请输入常用语");
                    return;
                }
                if (TextUtils.isEmpty(inputTvMsg.getText().toString().trim())) {
                    ToastUtils.showToast("请输入常用语");
                    return;
                }
                onClickListener.onPositiveButtonClick(dialog, inputTvMsg.getText().toString());
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onCancelButtonClick(dialog);
            }
        });
    }


    public void showUpdateItemDialog(Context context, DateRecodBean dateRecodBean, OnDataClickListener onClickListener) {
        BaseDialog dialog = new BaseDialog(context, R.layout.base_update_item_dialog);
        showDialog(dialog);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        EditText input_title_msg = (EditText) dialog.findViewById(R.id.input_title_msg);
        TextView start_time = (TextView) dialog.findViewById(R.id.start_time);
        TextView end_time = (TextView) dialog.findViewById(R.id.end_time);
        TextView see_person_number = (TextView) dialog.findViewById(R.id.see_person_number);
        if (dateRecodBean != null) {
            input_title_msg.setText(dateRecodBean.getTitle() + "");
            start_time.setText(dateRecodBean.getStart_time() + "");
            end_time.setText(dateRecodBean.getEnd_time() + "");
            end_time.setText(dateRecodBean.getEnd_time() + "");
            see_person_number.setText(dateRecodBean.getCount() + "");
        }
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        showDialog(dialog);
                        String startTime = DateUtils.getSTime(date);
                        start_time.setText(startTime);
                        dateRecodBean.setStart_time(startTime);
                    }
                })//默认全部显示
                        .setTitleText("开始时间")
                        .setDate(DateUtils.getCalendar(dateRecodBean.getStart_time()))
                        .setContentTextSize(16)//滚轮文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setType(new boolean[]{true, true, true, true, true, false})
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                pvTime.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        showDialog(dialog);

                    }
                });

            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        showDialog(dialog);
                        String startTime = DateUtils.getSTime(date);
                        end_time.setText(startTime);
                        dateRecodBean.setEnd_time(startTime);
                    }
                })//默认全部显示
                        .setTitleText("结束时间")
                        .setDate(DateUtils.getCalendar(dateRecodBean.getEnd_time()))
                        .setContentTextSize(16)//滚轮文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setType(new boolean[]{true, true, true, true, true, false})
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                pvTime.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        showDialog(dialog);

                    }
                });
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(input_title_msg.getText()) || TextUtils.isEmpty(input_title_msg.getText().toString().trim())) {
                    ToastUtils.showToast("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(see_person_number.getText()) || TextUtils.isEmpty(see_person_number.getText().toString().trim())) {
                    ToastUtils.showToast("请输入人数");
                    return;
                }
                String diffTime = DateUtils.getTimeStringDifference(DateUtils.getDate(dateRecodBean.getStart_time()), DateUtils.getDate(dateRecodBean.getEnd_time()));
                dateRecodBean.setTitle(input_title_msg.getText().toString() + "");
                dateRecodBean.setTimeLong(diffTime + "");
                dateRecodBean.setCount(see_person_number.getText().toString() + "");
                onClickListener.onPositiveButtonClick(dateRecodBean, dialog);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onCancelButtonClick(dialog);
            }
        });
    }

    private void showDialog(BaseDialog dialog) {
        if (dialog != null) {
            dialog.show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

    }

    /**
     * 按钮点击回调接口
     */
    public interface OnButtonClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(BaseDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(BaseDialog dialog);
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnButtonInputeClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(BaseDialog dialog, String msg);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(BaseDialog dialog);
    }

    public interface OnDataClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(DateRecodBean dateRecodBean, BaseDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(BaseDialog dialog);
    }

}
