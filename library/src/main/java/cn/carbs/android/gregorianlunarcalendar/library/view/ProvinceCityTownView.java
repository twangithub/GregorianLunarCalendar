package cn.carbs.android.gregorianlunarcalendar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import cn.carbs.android.gregorianlunarcalendar.library.R;

@SuppressLint("WrongConstant")
public class ProvinceCityTownView extends LinearLayout implements NumberPickerView.OnValueChangeListener{

    private static final int DEFAULT_GREGORIAN_COLOR = 0xff3388ff;
    private static final int DEFAULT_NORMAL_TEXT_COLOR = 0xFF555555;

    private NumberPickerView mProvincePickerView;
    private NumberPickerView mCityPickerView;
    private NumberPickerView mTownPickerView;

    private int mThemeColorG = DEFAULT_GREGORIAN_COLOR;
    private int mNormalTextColor = DEFAULT_NORMAL_TEXT_COLOR;

    /**
     * display values
     */
    private ArrayList<String> mProviceData;
    private ArrayList<ArrayList<String>> mCityData;
    private ArrayList<ArrayList<ArrayList<String>>> mTownData;


    /**
     * true to use scroll anim when switch picker passively
     */
    private boolean mScrollAnim = true;

    private OnDateChangedListener mOnDateChangedListener;

    public ProvinceCityTownView(Context context) {
        super(context);
        initInternal(context);
    }

    public ProvinceCityTownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initInternal(context);
    }

    public ProvinceCityTownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(context, attrs);
        initInternal(context);
    }

	private void initInternal(Context context){
        View contentView = inflate(context, R.layout.view_gregorian_lunar_calendar, this);

        mProvincePickerView = (NumberPickerView) contentView.findViewById(R.id.picker_year);
        mCityPickerView = (NumberPickerView) contentView.findViewById(R.id.picker_month);
        mTownPickerView = (NumberPickerView) contentView.findViewById(R.id.picker_day);

        mProvincePickerView.setOnValueChangedListener(this);
        mCityPickerView.setOnValueChangedListener(this);
        mTownPickerView.setOnValueChangedListener(this);
	}

    private void initAttr(Context context, AttributeSet attrs){
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GregorianLunarCalendarView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if(attr == R.styleable.GregorianLunarCalendarView_glcv_ScrollAnimation){
                mScrollAnim = a.getBoolean(attr, true);
            }else if(attr == R.styleable.GregorianLunarCalendarView_glcv_GregorianThemeColor){
                mThemeColorG = a.getColor(attr, DEFAULT_GREGORIAN_COLOR);
            }if(attr == R.styleable.GregorianLunarCalendarView_glcv_NormalTextColor){
                mNormalTextColor = a.getColor(attr, DEFAULT_NORMAL_TEXT_COLOR);
            }
        }
        a.recycle();
    }

    public void init(ArrayList<String> proData, ArrayList<ArrayList<String>> cityData , ArrayList<ArrayList<ArrayList<String>>> townData){
        setColor(mThemeColorG, mNormalTextColor);
        mProviceData = proData;
        mCityData = cityData;
        mTownData = townData;
        setDisplayValuesForAll();
    }

    private void setDisplayValuesForAll(){
        mProvincePickerView.refreshByNewDisplayedValues(mProviceData.toArray(new String[]{}));
        mCityPickerView.refreshByNewDisplayedValues(mCityData.get(0).toArray(new String[]{}));
        mTownPickerView.refreshByNewDisplayedValues(mTownData.get(0).get(0).toArray(new String[]{}));
    }


    public void setColor(int themeColor, int normalColor){
        setThemeColor(themeColor);
        setNormalColor(normalColor);
    }

    public void setThemeColor(int themeColor){
        mProvincePickerView.setSelectedTextColor(themeColor);
        mProvincePickerView.setHintTextColor(themeColor);
        mProvincePickerView.setDividerColor(themeColor);
        mProvincePickerView.setHintText("");
        mCityPickerView.setSelectedTextColor(themeColor);
        mCityPickerView.setHintTextColor(themeColor);
        mCityPickerView.setDividerColor(themeColor);
        mCityPickerView.setHintText("");
        mTownPickerView.setSelectedTextColor(themeColor);
        mTownPickerView.setHintTextColor(themeColor);
        mTownPickerView.setDividerColor(themeColor);
        mTownPickerView.setHintText("");
    }

    public void setNormalColor(int normalColor){
        mProvincePickerView.setNormalTextColor(normalColor);
        mCityPickerView.setNormalTextColor(normalColor);
        mTownPickerView.setNormalTextColor(normalColor);
    }


    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if(picker == null) return;
        if(picker == mProvincePickerView){
            int proIndex = mProvincePickerView.getValue();
            updateCityAndTown(proIndex);
        }else if(picker == mCityPickerView){
            int cityIndex = mCityPickerView.getValue();
            updateTown(cityIndex);
        }else if(picker == mTownPickerView){

        }

        callback(mProvincePickerView.getContentByCurrValue(),mCityPickerView.getContentByCurrValue(),
                mTownPickerView.getContentByCurrValue());
    }

    private void updateCityAndTown(int proIndex) {
        mCityPickerView.refreshByNewDisplayedValues(mCityData.get(proIndex).toArray(new String[]{}));
        mTownPickerView.refreshByNewDisplayedValues(mTownData.get(proIndex).get(0).toArray(new String[]{}));
    }

    private void updateTown(int proIndex) {
        mTownPickerView.refreshByNewDisplayedValues(mTownData.get(proIndex).get(0).toArray(new String[]{}));
    }

    private void callback(String pro,String city,String town){
        if(mOnDateChangedListener != null){
            mOnDateChangedListener.onDateChanged(pro,city,town);
        }
    }


    public interface OnDateChangedListener{
        void onDateChanged(String pro,String city,String town);
    }

    public void setOnDateChangedListener(OnDateChangedListener listener){
        mOnDateChangedListener = listener;
    }

}