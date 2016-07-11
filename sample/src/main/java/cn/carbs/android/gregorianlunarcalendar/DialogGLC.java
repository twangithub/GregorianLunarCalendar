package cn.carbs.android.gregorianlunarcalendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import cn.carbs.android.gregorianlunarcalendar.library.data.ChineseCalendar;
import cn.carbs.android.gregorianlunarcalendar.library.view.GregorianLunarCalendarView;
import cn.carbs.android.indicatorview.library.IndicatorView;

/**
 * Created by carbs on 2016/7/12.
 */

public class DialogGLC extends Dialog implements View.OnClickListener, IndicatorView.OnIndicatorChangedListener {

    private Context mContext;
    private IndicatorView mIndicatorView;
    private GregorianLunarCalendarView mGLCView;
    private Button mButtonGetData;

    public DialogGLC(Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_glc);

        mGLCView = (GregorianLunarCalendarView) this.findViewById(R.id.calendar_view);
        mGLCView.init();//init has no scroll effection, to today
        mIndicatorView = (IndicatorView) this.findViewById(R.id.indicator_view);
        mIndicatorView.setOnIndicatorChangedListener(this);

        mButtonGetData = (Button) this.findViewById(R.id.button_get_data);
        mButtonGetData.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        mGLCView.init();//if you want to reset data everytime showing this dialog, open this line
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_data:
                GregorianLunarCalendarView.CalendarData calendarData = mGLCView.getCalendarData();
                Calendar calendar = calendarData.getCalendar();
                String showToast = "Gregorian : " + calendar.get(Calendar.YEAR) + "-"
                        + (calendar.get(Calendar.MONTH) + 1) + "-"
                        + calendar.get(Calendar.DAY_OF_MONTH) + "\n"
                        + "Lunar     : " + calendar.get(ChineseCalendar.CHINESE_YEAR) + "-"
                        + (calendar.get(ChineseCalendar.CHINESE_MONTH)) + "-"
                        + calendar.get(ChineseCalendar.CHINESE_DATE);
                Toast.makeText(mContext.getApplicationContext(), showToast, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onIndicatorChanged(int oldSelectedIndex, int newSelectedIndex) {
        if (newSelectedIndex == 0) {
            toGregorianMode();
        } else if (newSelectedIndex == 1) {
            toLunarMode();
        }
    }

    private void toGregorianMode() {
        mGLCView.toGregorianMode();
    }

    private void toLunarMode() {
        mGLCView.toLunarMode();
    }
}