package com.tao.xiaoyuanyuan.ui;

import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.utils.GlideUtils;
import com.android.utils.PictureSelectUtils;
import com.android.utils.listener.ActivityListener;
import com.android.view.CircleImageView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tao.xiaoyuanyuan.R;
import com.tao.xiaoyuanyuan.base.App;
import com.tao.xiaoyuanyuan.db.entity.NormalTextBean;
import com.tao.xiaoyuanyuan.db.entity.OnLineTimeBean;
import com.tao.xiaoyuanyuan.event.BackGroundServiceEvent;
import com.tao.xiaoyuanyuan.event.ImageServiceEvent;
import com.tao.xiaoyuanyuan.event.ImageShowEvent;
import com.tao.xiaoyuanyuan.event.ImageShowWHEvent;
import com.tao.xiaoyuanyuan.event.ImageTypeEvent;
import com.tao.xiaoyuanyuan.event.SelectUpdateTextEvent;
import com.tao.xiaoyuanyuan.event.ShowEvent;
import com.tao.xiaoyuanyuan.event.ShowWidthEvent;
import com.tao.xiaoyuanyuan.event.SuoEvent;
import com.tao.xiaoyuanyuan.event.SuoServerEvent;
import com.tao.xiaoyuanyuan.rxbus2.RxBus;
import com.tao.xiaoyuanyuan.rxbus2.Subscribe;
import com.tao.xiaoyuanyuan.rxbus2.ThreadMode;
import com.tao.xiaoyuanyuan.server.BackGroundService;
import com.tao.xiaoyuanyuan.utils.AnimalUtil;
import com.tao.xiaoyuanyuan.utils.DateUitl;
import com.tao.xiaoyuanyuan.utils.LogUtils;
import com.tao.xiaoyuanyuan.utils.SPManager;
import com.tao.xiaoyuanyuan.utils.ToastUtils;
import com.tao.xiaoyuanyuan.utils.UIUtils;
import com.tao.xiaoyuanyuan.view.colorpicker.ColorPickerView;
import com.tao.xiaoyuanyuan.view.colorpicker.OnColorChangedListener;
import com.tao.xiaoyuanyuan.view.colorpicker.OnColorSelectedListener;
import com.tao.xiaoyuanyuan.view.colorpicker.slider.AlphaSlider;
import com.tao.xiaoyuanyuan.view.colorpicker.slider.LightnessSlider;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
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
    Button closeSuspension;

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
    private int mWidth = 360;
    public RadioButton mFontType1;
    public RadioButton mFontType2;
    public RadioButton mFontType3;
    public RadioButton mFontType4;
    public ImageView mClearText;
    public RadioGroup mRgOrientation;
    public SeekBar mSeekbarWidth;
    public BackGroundServiceEvent mBackGroundService;
    public ImageView mAddInputText;
    public ActivityListener mActivityListener;
    public SwitchCompat mSwitchShowImage;
    public RadioButton mImageType1;
    public RadioButton mImageType2;
    public SeekBar mSeekbarImageWidth;
    public CircleImageView mImagePathIv;
    private List<LocalMedia> selectList = new ArrayList<>();
    public String mImagePaht;
    public boolean isShowPicture;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityListener = (ActivityListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }

        mAddInputText = view.findViewById(R.id.add_input_text);
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
        closeSuspension = view.findViewById(R.id.close_suspension);
        mSwitchCompat = view.findViewById(R.id.switch_compat);
        mClearText = view.findViewById(R.id.clear_text);
        mRgOrientation = view.findViewById(R.id.rg_Orientation);
        //宽度
        mSeekbarWidth = view.findViewById(R.id.seekbar_width);

        //字体类型
        mFontType1 = view.findViewById(R.id.text_type_1);
        mFontType2 = view.findViewById(R.id.text_type_2);
        mFontType3 = view.findViewById(R.id.text_type_3);
        mFontType4 = view.findViewById(R.id.text_type_4);


        //贴图
        mImagePathIv = view.findViewById(R.id.image_path_iv);
        mSwitchShowImage = view.findViewById(R.id.switch_show_image);
        mImageType1 = view.findViewById(R.id.image_type_1);
        mImageType2 = view.findViewById(R.id.image_type_2);
        mSeekbarImageWidth = view.findViewById(R.id.seekbar_image_width);

        mWidth = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mWidth", mWidth);
        mTextString = SPManager.getString(SPManager.SP_MAIN_FLAG, "mTextString", mTextString);
        mTextType = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextType", mTextType);
        mTextColor = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextColor", mTextColor);
        mTextSize = SPManager.getInt(SPManager.SP_MAIN_FLAG, "mTextSize", mTextSize);

        mImagePaht = SPManager.getString(SPManager.SP_MAIN_FLAG, "imagePath", null);

        isShowPicture = SPManager.getBoolean(SPManager.SP_MAIN_FLAG, "isShowPicture", false);
        GlideUtils.intoImage(mImagePaht, mImagePathIv, R.mipmap.logo, R.mipmap.logo);

        mSwitchShowImage.setChecked(isShowPicture);
        suspensionTextEt.setText(mTextString);
        suspensionTextEt.setTypeface(Typeface.DEFAULT, mTextType);
        suspensionTextEt.setTextColor(mTextColor);
        suspensionTextEt.setTextSize(mTextSize);
        suspensionColorTv.setBackgroundColor(mTextColor);
        seekbarLevel.setProgress(mTextSize - 1);
        mSeekbarWidth.setProgress(mWidth - 50);
        if (Typeface.NORMAL == mTextType) {
            mRgOrientation.check(R.id.text_type_1);
        } else if (mTextType == Typeface.ITALIC) {
            mRgOrientation.check(R.id.text_type_2);
        } else if (mTextType == Typeface.BOLD) {
            mRgOrientation.check(R.id.text_type_3);
        } else if (mTextType == Typeface.BOLD_ITALIC) {
            mRgOrientation.check(R.id.text_type_4);
        }

        OnLineTimeBean onLineTimeBean = App.getRealmHelper()
                .queryOnlineTimeBeanByDate(DateUitl.getformatCurrentTime(System.currentTimeMillis()));
        if (onLineTimeBean != null) {
            App.getInstance().setOnlineTime(onLineTimeBean.getOnLinetime());
            LogUtils.e("时间  初始化", onLineTimeBean.getOnLinetime() + "");
        } else {
            App.getInstance().setOnlineTime(0);
        }
        initView();
       
        return view;
    }

    private void initView() {
        mAddInputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivityListener != null) {
                    mActivityListener.openRightDrawer();
                }
                AnimalUtil.startRotation(mAddInputText, 0, 90);
            }
        });
        openSuspension.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startFloatingButtonService();
            }
        });
        closeSuspension.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                RxBus.getDefault().post(new ShowEvent(false));
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
        mSwitchShowImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RxBus.getDefault().post(new ImageShowEvent(isChecked));
                SPManager.saveBoolean(SPManager.SP_MAIN_FLAG, "isShowPicture", isChecked);
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
                    LogUtils.e("文字大小", "----" + progress);
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
        mSeekbarImageWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RxBus.getDefault().post(new ImageShowWHEvent(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekbarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mWidth = progress + 50;
                    LogUtils.e("progress", "progress:" + progress);
                    SPManager.saveInt(SPManager.SP_MAIN_FLAG, "mWidth", mWidth);
                    postChangeTextWith(new ShowWidthEvent(mWidth));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mImagePathIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectUtils.pictureSelectorFormPhoto(mActivityListener.getActivity(),
                        PictureMimeType.ofImage(),
                        1, true, PictureConfig.MULTIPLE,
                        selectList, PictureConfig.CHOOSE_REQUEST);
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

        mImageType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePathIv.setDisableCircularTransformation(true);
                RxBus.getDefault().post(new ImageTypeEvent(true));
            }
        });
        mImageType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePathIv.setDisableCircularTransformation(false);
                RxBus.getDefault().post(new ImageTypeEvent(false));
            }
        });
    }

    /**
     * 调节文字内容
     */
    public void postChangeText() {
        mBackGroundService = new BackGroundServiceEvent();
        mBackGroundService.setString(mTextString);
        mBackGroundService.setTextSize(mTextSize);
        mBackGroundService.setTextType(mTextType);
        mBackGroundService.setTextColor(mTextColor);
        RxBus.getDefault().post(mBackGroundService);
    }

    /**
     * 调节悬浮宽度
     */
    public void postChangeTextWith(ShowWidthEvent showWidthEvent) {
        RxBus.getDefault().post(showWidthEvent);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startFloatingButtonService() {
        if (BackGroundService.isStarted) {
            RxBus.getDefault().post(new ShowEvent(true));
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
            startIntent.putExtra("mWidth", mWidth);
            startIntent.putExtra("imagePath", mImagePaht);
            startIntent.putExtra("isShowPicture", isShowPicture);
            NormalTextBean normalTextBean = new NormalTextBean();
            normalTextBean.setText(mTextString);
            App.getRealmHelper().insertNormalTextBean(normalTextBean);

            if (getActivity() != null) {
                getActivity().startService(startIntent);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SuoEvent suoEvent) {
        if (mSwitchCompat != null) {
            mSwitchCompat.setChecked(suoEvent.isIssuo());
        }
    }

    /**
     * 修改头像
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getCompressPath());
                    }
                    mImagePaht = selectList.get(0).getPath();
                    GlideUtils.intoImage(selectList.get(0).getPath(), mImagePathIv, R.mipmap.logo, R.mipmap.logo);
                    RxBus.getDefault().post(new ImageServiceEvent(selectList.get(0).getPath()));
                    SPManager.saveString(SPManager.SP_MAIN_FLAG, "imagePath", selectList.get(0).getPath());
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SelectUpdateTextEvent selectUpdateTextEvent) {
        mTextString = selectUpdateTextEvent.getSeledtedText();
        SPManager.saveString(SPManager.SP_MAIN_FLAG, "mTextString", mTextString);
        mClearText.setVisibility(View.VISIBLE);
        suspensionTextEt.setText(mTextString);
        postChangeText();
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
