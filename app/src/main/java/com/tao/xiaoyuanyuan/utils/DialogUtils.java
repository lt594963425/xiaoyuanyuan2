package com.tao.xiaoyuanyuan.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.utils.TextUtil;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.view.BaseDialog;

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
}
