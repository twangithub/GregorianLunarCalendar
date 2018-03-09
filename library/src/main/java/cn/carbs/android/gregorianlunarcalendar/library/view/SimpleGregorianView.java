package cn.carbs.android.gregorianlunarcalendar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;

import cn.carbs.android.gregorianlunarcalendar.library.R;
import cn.carbs.android.gregorianlunarcalendar.library.data.ChineseCalendar;
import cn.carbs.android.gregorianlunarcalendar.library.util.Util;

@SuppressLint("WrongConstant")
public class SimpleGregorianView extends LinearLayout implements NumberPickerView.OnValueChangeListener{

    private static final int DEFAULT_GREGORIAN_COLOR = 0xff3388ff;
    private static final int DEFAULT_NORMAL_TEXT_COLOR = 0xFF555555;

    private static final int YEAR_START = 1901;
    private static final int YEAR_STOP = 2100;
    private static final int YEAR_SPAN = YEAR_STOP - YEAR_START + 1;

    private static final int MONTH_START = 1;
    private static final int MONTH_START_GREGORIAN = 1;
    private static final int MONTH_STOP_GREGORIAN = 12;
    private static final int MONTH_SPAN_GREGORIAN = MONTH_STOP_GREGORIAN - MONTH_START_GREGORIAN + 1;


    private static final int DAY_START = 1;
    private static final int DAY_STOP = 30;

    private static final int DAY_START_GREGORIAN = 1;
    private static final int DAY_STOP_GREGORIAN = 31;
    private static final int DAY_SPAN_GREGORIAN = DAY_STOP_GREGORIAN - DAY_START_GREGORIAN + 1;

    private NumberPickerView mYearPickerView;
    private NumberPickerView mMonthPickerView;
    private NumberPickerView mDayPickerView;

    private int mThemeColorG = DEFAULT_GREGORIAN_COLOR;
    private int mNormalTextColor = DEFAULT_NORMAL_TEXT_COLOR;

    /**
     * display values
     */
    private String[] mDisplayYearsGregorian;
    private String[] mDisplayMonthsGregorian;
    private String[] mDisplayDaysGregorian;


    /**
     * true to use scroll anim when switch picker passively
     */
    private boolean mScrollAnim = true;

    private OnDateChangedListener mOnDateChangedListener;

    public SimpleGregorianView(Context context) {
        super(context);
        initInternal(context);
    }

    public SimpleGregorianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initInternal(context);
    }

    public SimpleGregorianView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(context, attrs);
        initInternal(context);
    }

	private void initInternal(Context context){
        View contentView = inflate(context, R.layout.view_gregorian_lunar_calendar, this);

        mYearPickerView = (NumberPickerView) contentView.findViewById(R.id.picker_year);
        mMonthPickerView = (NumberPickerView) contentView.findViewById(R.id.picker_month);
        mDayPickerView = (NumberPickerView) contentView.findViewById(R.id.picker_day);

        mYearPickerView.setOnValueChangedListener(this);
        mMonthPickerView.setOnValueChangedListener(this);
        mDayPickerView.setOnValueChangedListener(this);
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

    public void init(){
        setColor(mThemeColorG, mNormalTextColor);
        setConfigs(Calendar.getInstance(), true);
    }

    public void init(Calendar calendar){
        setColor(mThemeColorG, mNormalTextColor);
        setConfigs(calendar, false);
    }

    private void setConfigs(Calendar c, boolean anim){
        if(c == null){
            c = Calendar.getInstance();
        }
        if(!checkCalendarAvailable(c, YEAR_START, YEAR_STOP)){
            c = adjustCalendarByLimit(c, YEAR_START, YEAR_STOP);
        }
        ChineseCalendar cc;
        if(c instanceof ChineseCalendar){
            cc = (ChineseCalendar)c;
        }else{
            cc = new ChineseCalendar(c);
        }
        setDisplayValuesForAll(cc, anim);
    }

    private Calendar adjustCalendarByLimit(Calendar c, int yearStart, int yearStop){
        int yearGrego = c.get(Calendar.YEAR);
        if(yearGrego < yearStart){
            c.set(Calendar.YEAR, yearStart);
            c.set(Calendar.MONTH, MONTH_START_GREGORIAN);
            c.set(Calendar.DAY_OF_MONTH, DAY_START_GREGORIAN);
        }
        if(yearGrego > yearStop){
            c.set(Calendar.YEAR, yearStop);
            c.set(Calendar.MONTH, MONTH_STOP_GREGORIAN - 1);
            int daySway = Util.getSumOfDayInMonthForGregorianByMonth(yearStop, MONTH_STOP_GREGORIAN);
            c.set(Calendar.DAY_OF_MONTH, daySway);
        }
        return c;
    }
	
    public void toGregorianMode(){
        setThemeColor(mThemeColorG);
        setGregorian( true);
    }


    public void setColor(int themeColor, int normalColor){
        setThemeColor(themeColor);
        setNormalColor(normalColor);
    }

    public void setThemeColor(int themeColor){
        mYearPickerView.setSelectedTextColor(themeColor);
        mYearPickerView.setHintTextColor(themeColor);
        mYearPickerView.setDividerColor(themeColor);
        mMonthPickerView.setSelectedTextColor(themeColor);
        mMonthPickerView.setHintTextColor(themeColor);
        mMonthPickerView.setDividerColor(themeColor);
        mDayPickerView.setSelectedTextColor(themeColor);
        mDayPickerView.setHintTextColor(themeColor);
        mDayPickerView.setDividerColor(themeColor);
    }

    public void setNormalColor(int normalColor){
        mYearPickerView.setNormalTextColor(normalColor);
        mMonthPickerView.setNormalTextColor(normalColor);
        mDayPickerView.setNormalTextColor(normalColor);
    }

    private void setDisplayValuesForAll(ChineseCalendar cc, boolean anim){
        setDisplayData();
        initValuesForY(cc, anim);
        initValuesForM(cc, anim);
        initValuesForD(cc, anim);
    }
	
    /**
     *
     */
    private void setDisplayData(){

        if(mDisplayYearsGregorian == null){
            mDisplayYearsGregorian = new String[YEAR_SPAN];
            for(int i = 0; i < YEAR_SPAN; i++){
                mDisplayYearsGregorian[i] = String.valueOf(YEAR_START + i);
            }
        }
        if(mDisplayMonthsGregorian == null){
            mDisplayMonthsGregorian = new String[MONTH_SPAN_GREGORIAN];
            for(int i = 0; i < MONTH_SPAN_GREGORIAN; i++){
                mDisplayMonthsGregorian[i] = String.valueOf(MONTH_START_GREGORIAN + i);
            }
        }
        if(mDisplayDaysGregorian == null){
            mDisplayDaysGregorian = new String[DAY_SPAN_GREGORIAN];
            for(int i = 0; i < DAY_SPAN_GREGORIAN; i++){
                mDisplayDaysGregorian[i] = String.valueOf(DAY_START_GREGORIAN + i);
            }
        }

    }

    //without scroll animation when init
    private void initValuesForY(ChineseCalendar cc, boolean anim){

        int yearSway = cc.get(Calendar.YEAR);
        setValuesForPickerView(mYearPickerView, yearSway, YEAR_START, YEAR_STOP, mDisplayYearsGregorian, false, anim);

    }
    
    private void initValuesForM(ChineseCalendar cc,boolean anim){
        int monthStart;
        int monthStop;
        int monthSway;
        String[] newDisplayedVales = null;

        monthStart = MONTH_START_GREGORIAN;
        monthStop = MONTH_STOP_GREGORIAN;
        monthSway = cc.get(Calendar.MONTH) + 1;
        newDisplayedVales = mDisplayMonthsGregorian;

        setValuesForPickerView(mMonthPickerView, monthSway, monthStart, monthStop, newDisplayedVales, false, anim);
    }

    private void initValuesForD(ChineseCalendar cc, boolean anim){
            int dayStart = DAY_START_GREGORIAN;
            int dayStop = Util.getSumOfDayInMonthForGregorianByMonth(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH) + 1);
            int daySway = cc.get(Calendar.DAY_OF_MONTH);
            mDayPickerView.setHintText(getContext().getResources().getString(R.string.day));
            setValuesForPickerView(mDayPickerView, daySway, dayStart, dayStop, mDisplayDaysGregorian, false, anim);
    }
    
    private void setValuesForPickerView(NumberPickerView pickerView, int newSway, int newStart, int newStop,
                                        String[] newDisplayedVales, boolean needRespond, boolean anim){

        if(newDisplayedVales == null){
            throw new IllegalArgumentException("newDisplayedVales should not be null.");
        }else if(newDisplayedVales.length == 0){
            throw new IllegalArgumentException("newDisplayedVales's length should not be 0.");
        }
        int newSpan = newStop - newStart + 1;
        if(newDisplayedVales.length < newSpan){
            throw new IllegalArgumentException("newDisplayedVales's length should not be less than newSpan.");
        }

        int oldStart = pickerView.getMinValue();
        int oldStop = pickerView.getMaxValue();
        int oldSpan = oldStop - oldStart + 1;
        int fromValue = pickerView.getValue();
        pickerView.setMinValue(newStart);
        if(newSpan > oldSpan){
            pickerView.setDisplayedValues(newDisplayedVales);
            pickerView.setMaxValue(newStop);
        }else{
            pickerView.setMaxValue(newStop);
            pickerView.setDisplayedValues(newDisplayedVales);
        }
        if(mScrollAnim && anim){
            int toValue = newSway;
            if(fromValue < newStart){
                fromValue = newStart;
            }
            pickerView.smoothScrollToValue(fromValue, toValue, needRespond);
        }else{
            pickerView.setValue(newSway);
        }
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if(picker == null) return;
        if(picker == mYearPickerView){
            passiveUpdateMonthAndDay(oldVal, newVal);
        }else if(picker == mMonthPickerView){
            int fixYear = mYearPickerView.getValue();
            passiveUpdateDay(fixYear, fixYear, oldVal, newVal);
        }else if(picker == mDayPickerView){
            if(mOnDateChangedListener != null){
                mOnDateChangedListener.onDateChanged(getCalendarData());
            }
        }
    }

    private void passiveUpdateMonthAndDay(int oldYearFix, int newYearFix){
        int oldMonthSway = mMonthPickerView.getValue();
        int oldDaySway = mDayPickerView.getValue();

        int newMonthSway = oldMonthSway;
        int oldDayStop = Util.getSumOfDayInMonth(oldYearFix, oldMonthSway, true);
        int newDayStop = Util.getSumOfDayInMonth(newYearFix, newMonthSway, true);

        if(oldDayStop == newDayStop){
            if(mOnDateChangedListener != null){
                mOnDateChangedListener.onDateChanged(getCalendarData(newYearFix, newMonthSway, oldDaySway));
            }
            return;
        }
        int newDaySway = (oldDaySway <= newDayStop) ? oldDaySway : newDayStop;
        setValuesForPickerView(mDayPickerView, newDaySway, DAY_START, newDayStop, mDisplayDaysGregorian, true, true);
        if(mOnDateChangedListener != null){
            mOnDateChangedListener.onDateChanged(getCalendarData(newYearFix, newMonthSway, newDaySway));
        }
        return;
    }
	
    private void passiveUpdateDay(int oldYear, int newYear, int oldMonth, int newMonth){
        int oldDaySway = mDayPickerView.getValue();

        int oldDayStop = Util.getSumOfDayInMonth(oldYear, oldMonth,true);
        int newDayStop = Util.getSumOfDayInMonth(newYear, newMonth,true);

        if(oldDayStop == newDayStop){
            if(mOnDateChangedListener != null){
                mOnDateChangedListener.onDateChanged(getCalendarData(newYear, newMonth, oldDaySway));
            }
            return;//不需要更新
        }else{
            int newDaySway = oldDaySway <= newDayStop ? oldDaySway : newDayStop;
            setValuesForPickerView(mDayPickerView, newDaySway, DAY_START, newDayStop, mDisplayDaysGregorian, true, true);
            if(mOnDateChangedListener != null){
                mOnDateChangedListener.onDateChanged(getCalendarData(newYear, newMonth, newDaySway));
            }
            return;
        }
    }
	
    public void setGregorian(boolean anim){

        ChineseCalendar cc = (ChineseCalendar)getCalendarData().getCalendar();//根据mIsGregorian收集数据
        if(!checkCalendarAvailable(cc, YEAR_START, YEAR_STOP)){
            cc = (ChineseCalendar)adjustCalendarByLimit(cc, YEAR_START, YEAR_STOP);//调整改变后的界面的显示
        }
        setConfigs(cc, anim);//重新更新界面数据
    }

    private boolean checkCalendarAvailable(Calendar cc, int yearStart, int yearStop){
        int year = cc.get(Calendar.YEAR);
        return (yearStart <= year) && (year <= yearStop);
    }

    public View getNumberPickerYear(){
        return mYearPickerView;
    }

    public View getNumberPickerMonth(){
        return mMonthPickerView;
    }

    public View getNumberPickerDay(){
        return mDayPickerView;
    }

    public void setNumberPickerYearVisibility(int visibility){
        setNumberPickerVisibility(mYearPickerView, visibility);
    }

    public void setNumberPickerMonthVisibility(int visibility){
        setNumberPickerVisibility(mMonthPickerView, visibility);
    }

    public void setNumberPickerDayVisibility(int visibility){
        setNumberPickerVisibility(mDayPickerView, visibility);
    }

    public void setNumberPickerVisibility(NumberPickerView view, int visibility){
        if(view.getVisibility() == visibility){
            return;
        }else if(visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE){
            view.setVisibility(visibility);
        }
    }

    private CalendarData getCalendarData(int pickedYear, int pickedMonthSway, int pickedDay){
        return new CalendarData(pickedYear, pickedMonthSway, pickedDay);
    }

    public CalendarData getCalendarData(){
        int pickedYear = mYearPickerView.getValue();
        int pickedMonthSway = mMonthPickerView.getValue();
        int pickedDay = mDayPickerView.getValue();
        return new CalendarData(pickedYear, pickedMonthSway, pickedDay);
    }



    public static class CalendarData{
        public int pickedYear;
        public int pickedMonthSway;
        public int pickedDay;

        /**
         * 获取数据示例与说明：
         * Gregorian : //公历
         *      chineseCalendar.get(Calendar.YEAR)              //获取公历年份，范围[1900 ~ 2100]
         *      chineseCalendar.get(Calendar.MONTH) + 1         //获取公历月份，范围[1 ~ 12]
         *      chineseCalendar.get(Calendar.DAY_OF_MONTH)      //返回公历日，范围[1 ~ 30]
         *
         * Lunar
         *      chineseCalendar.get(ChineseCalendar.CHINESE_YEAR)   //返回农历年份，范围[1900 ~ 2100]
         *      chineseCalendar.get(ChineseCalendar.CHINESE_MONTH)) //返回农历月份，范围[(-12) ~ (-1)] || [1 ~ 12]
         *                                                          //当有月份为闰月时，返回对应负值
         *                                                          //当月份非闰月时，返回对应的月份值
         *      calendar.get(ChineseCalendar.CHINESE_DATE)         //返回农历日，范围[1 ~ 30]
         */
        public ChineseCalendar chineseCalendar;

        /**
         * model类的构造方法
         * @param pickedYear
         * 			年
         * @param pickedMonthSway
         * 			月，公历农历均从1开始。农历如果有闰年，按照实际的顺序添加
         * @param pickedDay
         * 			日，从1开始，日期在月份中的显示数值
         */
        public CalendarData(int pickedYear, int pickedMonthSway, int pickedDay) {
            this.pickedYear = pickedYear;
            this.pickedMonthSway = pickedMonthSway;
            this.pickedDay = pickedDay;
            initChineseCalendar();
        }

        /**
         * 初始化成员变量chineseCalendar，用来记录当前选中的时间。此成员变量同时存储了农历和公历的信息
         */
        private void initChineseCalendar(){
            chineseCalendar = new ChineseCalendar(pickedYear, pickedMonthSway - 1, pickedDay);//公历日期构造方法
        }

        public Calendar getCalendar(){
            return chineseCalendar;
        }
    }

    public interface OnDateChangedListener{
        void onDateChanged(CalendarData calendarData);
    }

    public void setOnDateChangedListener(OnDateChangedListener listener){
        mOnDateChangedListener = listener;
    }

}