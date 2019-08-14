package com.tao.xiaoyuanyuan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tao.xiaoyuanyuan.event.BackGroundServiceEvent;
import com.tao.xiaoyuanyuan.event.SuoEvent;
import com.tao.xiaoyuanyuan.event.SuoServerEvent;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.rxbus2.Subscribe;
import com.tao.xiaoyuanyuan.rxbus2.ThreadMode;
import com.tao.xiaoyuanyuan.server.BackGroundService;
import com.tao.xiaoyuanyuan.utils.AnimalUtil;
import com.tao.xiaoyuanyuan.utils.SPManager;
import com.tao.xiaoyuanyuan.utils.ToastUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;
import com.tao.xiaoyuanyuan.view.colorpicker.ColorPickerView;
import com.tao.xiaoyuanyuan.view.colorpicker.OnColorChangedListener;
import com.tao.xiaoyuanyuan.view.colorpicker.OnColorSelectedListener;
import com.tao.xiaoyuanyuan.view.colorpicker.slider.AlphaSlider;
import com.tao.xiaoyuanyuan.view.colorpicker.slider.LightnessSlider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.tao.xiaoyuanyuan.utils.UIUtils.getPackageName;


public class UserFragment extends Fragment {
    EditText suspensionTextEt;
    TextView suspensionTextSize;
    SeekBar seekbarLevel;
    TextView suspensionTextColor;
    TextView suspensionColorTv;
    LinearLayout parentContaintLly;
    ColorPickerView colorPickerView;
    LightnessSlider vLightnessSlider;
    AlphaSlider vAlphaSlider;
    Button openSuspension;

    private boolean expandView = false;
    public LinearLayout mColorPickViewLly;
    public SwitchCompat mSwitchCompat;
    //文字内容
    public String mTextString = "";
    //字体类型
    public int mTextType = Typeface.NORMAL;
    //字体颜色
    public int mTextColor = Color.BLACK;
    //字体大小
    private int mTextSize = 15;
    public RadioButton mFontType1;
    public RadioButton mFontType2;
    public RadioButton mFontType3;
    public RadioButton mFontType4;
    public ImageView mClearText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }

        mColorPickViewLly = view.findViewById(R.id.color_pick_view_lly);
        suspensionTextEt = view.findViewById(R.id.suspension_text_et);
        suspensionTextSize = view.findViewById(R.id.suspension_text_size);
        seekbarLevel = view.findViewById(R.id.seekbar_level);
        suspensionTextColor = view.findViewById(R.id.suspension_text_color);
        suspensionColorTv = view.findViewById(R.id.suspension_color_tv);
        parentContaintLly = view.findViewById(R.id.parent_containt_lly);
        colorPickerView = view.findViewById(R.id.color_picker_view);
        vLightnessSlider = view.findViewById(R.id.v_lightness_slider);
        vAlphaSlider = view.findViewById(R.id.v_alpha_slider);
        openSuspension = view.findViewById(R.id.open_suspension);
        mSwitchCompat = view.findViewById(R.id.switch_compat);
        mClearText = view.findViewById(R.id.clear_text);
        //字体类型
        mFontType1 = view.findViewById(R.id.text_type_1);
        mFontType2 = view.findViewById(R.id.text_type_2);
        mFontType3 = view.findViewById(R.id.text_type_3);
        mFontType4 = view.findViewById(R.id.text_type_4);

        mTextString = SPManager.getString(SPManager.SP_MAIN_FLAG, "mTextString", mTextString);
        mTextType = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
        mTextColor = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextColor", mTextColor);
        mTextSize = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextSize", mTextSize);
        suspensionTextEt.setText(mTextString);
        suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
        suspensionTextEt.setTextColor(mTextColor);
        suspensionTextEt.setTextSize(mTextSize);
        seekbarLevel.setProgress(mTextSize - 1);

        initView();

        return view;
    }

    private void initView() {
        openSuspension.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startFloatingButtonService();
            }
        });
        addDisposable(RxTextView.textChanges(suspensionTextEt)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if (TextUtils.isEmpty(charSequence)) {
                            mClearText.setVisibility(View.GONE);
                            return;
                        }
                        if (TextUtils.isEmpty(charSequence.toString())) {
                            mClearText.setVisibility(View.GONE);
                            return;
                        }
                        if (TextUtils.isEmpty(charSequence.toString().trim())) {
                            mClearText.setVisibility(View.GONE);
                            return;
                        }
                        mTextString = charSequence.toString();
                        SPManager.saveString(SPManager.SP_MAIN_FLAG, "mTextString", mTextString);
                        mClearText.setVisibility(View.VISIBLE);
                        postChangeText();
                    }
                }));

        mClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextString = "";
                suspensionTextEt.setText(mTextString);
                SPManager.saveString(SPManager.SP_MAIN_FLAG, "mTextString", mTextString);
                postChangeText();
            }
        });
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RxBus.getDefault().post(new SuoServerEvent(isChecked));
            }
        });
        colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int selectedColor) {
                mTextColor = selectedColor;
                suspensionTextEt.setTextColor(selectedColor);
                suspensionColorTv.setBackgroundColor(selectedColor);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextColor", mTextColor);
                postChangeText();
            }
        });
        colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int selectedColor) {
                mTextColor = selectedColor;
                suspensionTextEt.setTextColor(selectedColor);
                suspensionColorTv.setBackgroundColor(selectedColor);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextColor", mTextColor);
                postChangeText();
            }
        });

        parentContaintLly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandView) {
                    expandView = false;
                    AnimalUtil.startAnimal(mColorPickViewLly, UIUtils.dip2Px(400), 0);
                    AnimalUtil.startRotation(parentContaintLly, 0, 90);
                } else {
                    expandView = true;
                    mColorPickViewLly.setVisibility(View.VISIBLE);
                    AnimalUtil.startAnimal(mColorPickViewLly, 0, UIUtils.dip2Px(400));
                    AnimalUtil.startRotation(parentContaintLly, 0, 90);
                }

            }
        });
        seekbarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextSize = progress + 1;
                if (fromUser) {
                    suspensionTextEt.setTextSize(mTextSize);
                    SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextSize", mTextSize);

                }
                postChangeText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mFontType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextType = Typeface.NORMAL;
                suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
                postChangeText();
            }
        });
        mFontType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextType = Typeface.ITALIC;
                suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
                postChangeText();
            }
        });
        mFontType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextType = Typeface.BOLD;
                suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
                postChangeText();

            }
        });
        mFontType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextType = Typeface.BOLD_ITALIC;
                suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
                SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
                postChangeText();
            }
        });
    }

    public void postChangeText() {
        BackGroundServiceEvent backGroundService = new BackGroundServiceEvent();
        backGroundService.setString(mTextString);
        backGroundService.setTextSize(mTextSize);
        backGroundService.setTextType(mTextType);
        backGroundService.setTextColor(mTextColor);
        RxBus.getDefault().post(backGroundService);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
        if (RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private int x = 221;
    private int y = 1857;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startFloatingButtonService() {
        if (BackGroundService.isStarted) {
            return;
        }
        if (!Settings.canDrawOverlays(getActivity())) {
            ToastUtils.showToast("当前无权限，请授权");
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            Intent startIntent = new Intent(getActivity(), BackGroundService.class);
            startIntent.putExtra("mTextString", mTextString);
            startIntent.putExtra("mTextType", mTextType);
            startIntent.putExtra("mTextColor", mTextColor);
            startIntent.putExtra("mTextSize", mTextSize);
            if (getActivity() != null) {
                getActivity().startService(startIntent);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SuoEvent suoEvent) {
        if (mSwitchCompat!=null){
            mSwitchCompat.setChecked(suoEvent.isIssuo());
        }

    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }
}
