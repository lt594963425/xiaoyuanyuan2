package com.tao.xiaoyuanyuan.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * $activityName
 * 倒计时
 *
 * @author LiuTao
 * @date 2018/11/14/014
 */


public class RxTimerUtil {
    private Disposable countdownTimerDisposable;
    private Disposable timerDisposable;

    public interface OnRxTimerListener {
        void onSubscribe(Disposable d);

        void onNext(Integer integer);

        void onStart();

        void onError(Throwable throwable);

        void onComplete();
    }

    public interface RxTimerNextListener {
        void onTimerNext(long number);

    }

    public RxTimerUtil() {
    }

    /**
     * 倒计时
     *
     * @param time 秒
     */
    public void startCountDownTime(int time, final OnRxTimerListener timerListener) {
        Observable observable = countdown(time)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        timerListener.onStart();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                countdownTimerDisposable = d;
                timerListener.onSubscribe(d);
            }

            @Override
            public void onNext(Integer integer) {
                timerListener.onNext(integer);
            }

            @Override
            public void onError(Throwable e) {
                timerListener.onError(e);
                clearCountDownTimer();
            }

            @Override
            public void onComplete() {
                timerListener.onComplete();
                clearCountDownTimer();

            }
        };
        observable.subscribe(observer);

    }

    /**
     * 结束调用
     */
    public void clearCountDownTimer() {
        if (countdownTimerDisposable != null && !countdownTimerDisposable.isDisposed()) {
            countdownTimerDisposable.dispose();
        }
    }

    private Observable<Integer> countdown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);

    }

    /**
     * 开始计时器
     *
     * @param rxTimerNextListener
     */
    public void startTimer(Long time, RxTimerNextListener rxTimerNextListener) {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        timerDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        rxTimerNextListener.onTimerNext(time + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        clearTimer();
                    }

                    @Override
                    public void onComplete() {
                        clearTimer();
                    }
                });
    }


    /**
     * 结束计时器的调用
     */
    public void clearTimer() {
        if (timerDisposable != null && !timerDisposable.isDisposed()) {
            timerDisposable.dispose();
        }

    }
}
