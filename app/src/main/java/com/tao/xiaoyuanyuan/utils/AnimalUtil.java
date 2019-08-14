package com.tao.xiaoyuanyuan.utils;

import android.animation.ValueAnimator;
import android.view.View;



/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/13 0013
 * Time: 下午 15:39
 */
public class AnimalUtil {
    /**
     * 伸缩
     * @param view
     * @param start
     * @param end
     */
    public static void startAnimal(final View view, int start, int end) {
        //会调用自定义路径属性动画evaluate方法
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer integer = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = integer;
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    public static void startRotation(final View view, int start, int end) {
        //会调用自定义路径属性动画evaluate方法
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float stepValue = (Float) animation.getAnimatedValue();
                LogUtils.e("动画2  角度", stepValue + "");
                view.setRotation(stepValue);
            }
        });
        valueAnimator.start();
    }
}
