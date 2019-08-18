package com.android.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.library.R;


/**
 * Created by caoyu on 2017/12/2/002.
 */

public class IOSDialogUtils {


    public static IOSDialogUtils getInstance() {
        return new IOSDialogUtils();
    }

    public void clickDialogIos(Context context, String msg, String ok, final OnButtonClickListener onClickListener) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setText(ok);
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

    public void clickDialogIos(Context context, String msg, String ok, boolean cancle) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setText(ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onPositiveButtonClick(dialog);
            }
        });
        if (cancle) {
            tvCancel.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onCancelButtonClick(dialog);
            }
        });
    }

    public void clickDialogIos(Context context, String msg, String cancel,String ok , final OnButtonClickListener onClickListener) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setText(ok);
        tvCancel.setText(cancel);
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


    private static OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onClickListener) {
        onButtonClickListener = onClickListener;
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
        void onPositiveButtonClick(IOSDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(IOSDialog dialog);
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnUploadDjiClickListener {
        void onPositiveButtonClick(IOSDialog dialog, String nikename);

    }

    /**
     * 按钮点击回调接口
     */
    public interface OnExitClickListener {
        /**
         * 确定按钮点击回调方法
         */
        void onPositiveButtonClick();

    }

    public void showDialogIos(Context context, String msg) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setVisibility(View.GONE);
        tvCancel.setText("确定");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onCancelButtonClick(dialog);
            }
        });
    }

    public void showEditDialogIos(Context context, final OnUploadDjiClickListener onUploadDjiClickListener) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.edit_iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        final EditText editText = (EditText) dialog.findViewById(R.id.et_nikename);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadDjiClickListener.onPositiveButtonClick(dialog, editText.getText().toString());
            }
        });
    }



    public void exitDialogIos(Context context, String msg, String btn, final OnButtonClickListener onClickListener) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keylistener);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvCancel.setText("退出");
        tvOk.setText(btn);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onCancelButtonClick(dialog);
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onPositiveButtonClick(dialog);
            }
        });
    }


    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };


}
