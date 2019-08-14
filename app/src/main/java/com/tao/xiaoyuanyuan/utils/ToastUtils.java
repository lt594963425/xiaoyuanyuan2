package com.tao.xiaoyuanyuan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tao.xiaoyuanyuan.R;


/**
 * * @author ${LiuTao}
 *
 * @date 2018/3/17/017
 */

public class ToastUtils {
    private static Toast toast;
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    public static void showToast(final String msg) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                toast = Toast.makeText(UIUtils.getContext(), msg + "", Toast.LENGTH_SHORT);
                toast.setText(msg + "");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void showToast(final int resId) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                toast = Toast.makeText(UIUtils.getContext(), resId + "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setText(resId + "");
                toast.show();
            }
        });
    }

    public static void showToast(final int resId, boolean UIUtilsend) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                toast = Toast.makeText(UIUtils.getContext(), resId + "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setText(resId);
                toast.show();
            }
        });
    }


    public static void setResultToToast(final String string) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UIUtils.getContext(), string + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setResultToText(final TextView tv, final String s) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (tv == null) {
                    // Toast.makeText(UIUtils.getContext(), "tv is null", Toast.LENGTH_LONG).show();
                } else {
                    tv.setText(s);
                }
            }
        });
    }

    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");

    @ColorInt
    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    @ColorInt
    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    @ColorInt
    private static final int WARNING_COLOR = Color.parseColor("#FFA900");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    private static Toast currentToast;
    private static Toast topViewRound;

    //*******************************************普通 使用UIUtilslicationContext 方法*********************
    /**
     * Toast 替代方法 ：立即显示无需等待
     */
    private static Toast mToast;
    private static long mExitTime;

    public static void normal(@NonNull final String message) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                normal(UIUtils.getContext(), message, Toast.LENGTH_SHORT, null, false).show();
            }
        });

    }

    public static void normal(@NonNull final String message, final Drawable icon) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                normal(UIUtils.getContext(), message, Toast.LENGTH_SHORT, icon, true).show();
            }
        });

    }

    public static void normal(@NonNull final String message, final int duration) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                normal(UIUtils.getContext(), message, duration, null, false).show();
            }
        });

    }

    public static void normal(@NonNull final String message, final int duration, final Drawable icon) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                normal(UIUtils.getContext(), message, duration, icon, true).show();
            }
        });

    }

    public static Toast normal(@NonNull String message, int duration, Drawable icon, boolean withIcon) {
        return custom(UIUtils.getContext(), message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public static void warning(@NonNull final String message) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                warning(UIUtils.getContext(), message, Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public static void warning(@NonNull final String message, final int duration) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                warning(UIUtils.getContext(), message, duration, true).show();
            }
        });

    }

    public static Toast warning(@NonNull String message, int duration, boolean withIcon) {
        return custom(UIUtils.getContext(), message, getDrawable(UIUtils.getContext(), R.drawable.ic_error_outline_white_48dp), DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static void info(@NonNull final String message) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                info(UIUtils.getContext(), message, Toast.LENGTH_SHORT, true).show();
            }
        });

    }


    public static void info(@NonNull final String message, final int duration) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                info(UIUtils.getContext(), message, duration, true).show();
            }
        });

    }

    public static Toast info(@NonNull String message, int duration, boolean withIcon) {

        return custom(UIUtils.getContext(), message, getDrawable(UIUtils.getContext(), R.drawable.ic_info_outline_white_48dp), DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static void success(@NonNull final String message) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                success(UIUtils.getContext(), message, Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public static void success(@NonNull final String message, final int duration) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                success(UIUtils.getContext(), message, duration, true).show();
            }
        });

    }

    public static Toast success(@NonNull String message, int duration, boolean withIcon) {
        return custom(UIUtils.getContext(), message, getDrawable(UIUtils.getContext(), R.drawable.ic_check_white_48dp), DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static void error(@NonNull final String message) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                error(UIUtils.getContext(), message, Toast.LENGTH_SHORT, true).show();
            }
        });

    }
    //===========================================使用UIUtilslicationContext 方法=========================

    //*******************************************常规方法********************************************

    public static void error(@NonNull final String message, final int duration) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                error(UIUtils.getContext(), message, duration, true).show();
            }
        });

    }

    public static Toast error(@NonNull String message, int duration, boolean withIcon) {
        return custom(UIUtils.getContext(), message, getDrawable(UIUtils.getContext(), R.drawable.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration) {
        return normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon, boolean withIcon) {
        return custom(context, message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_error_outline_white_48dp), DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_info_outline_white_48dp), DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }


    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_check_white_48dp), DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    //===========================================常规方法============================================

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }

    //*******************************************内需方法********************************************

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, getDrawable(context, iconRes), textColor, tintColor, duration, withIcon, shouldTint);
    }


    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = getDrawable(context, R.drawable.toast_frame);
        }
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));
        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }

    public static final Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }

    //===========================================内需方法============================================


    //******************************************系统 Toast 替代方法***************************************

    public static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static final Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * 封装了Toast的方法 :需要等待
     *
     * @param context Context
     * @param str     要显示的字符串
     * @param isLong  Toast.LENGTH_LONG / Toast.LENGTH_SHORT
     */
    public static void showToast(Context context, String str, boolean isLong) {
        if (isLong) {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 封装了Toast的方法 :需要等待
     */
    public static void showToastShort(String str) {
        Toast.makeText(UIUtils.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 封装了Toast的方法 :需要等待
     */
    public static void showToastShort(int resId) {
        Toast.makeText(UIUtils.getContext(), UIUtils.getContext().getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 封装了Toast的方法 :需要等待
     */
    public static void showToastLong(String str) {
        Toast.makeText(UIUtils.getContext(), str, Toast.LENGTH_LONG).show();
    }

    /**
     * 封装了Toast的方法 :需要等待
     */
    public static void showToastLong(int resId) {
        Toast.makeText(UIUtils.getContext(), UIUtils.getContext().getString(resId), Toast.LENGTH_LONG).show();
    }


    /**
     * Toast 替代方法 ：立即显示无需等待
     *
     * @param context  实体
     * @param resId    String资源ID
     * @param duration 显示时长
     */
    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }
    //===========================================Toast 替代方法======================================

    /**
     * Toast 替代方法 ：立即显示无需等待
     *
     * @param context  实体
     * @param msg      要显示的字符串
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static boolean doubleClickExit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.normal("再按一次退出");
            mExitTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }
}
