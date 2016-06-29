package cn.carbs.android.gregorianlunarcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import cn.carbs.android.gregorianlunarcalendar.library.data.ChineseCalendar;
import cn.carbs.android.gregorianlunarcalendar.library.view.GregorianLunarCalendarView;
import cn.carbs.android.indicatorview.library.IndicatorView;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, IndicatorView.OnIndicatorChangedListener {

    //indicator view used to indicate and switch gregorien/lunar mode
    private IndicatorView mIndicatorView;

    private GregorianLunarCalendarView mGLCView;

    private Button mDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLCView = (GregorianLunarCalendarView) this.findViewById(R.id.calendar_view);
        mGLCView.init();//init has no scroll effection, to today

        /*Calendar customizedCalendar = Calendar.getInstance();
        customizedCalendar.set((2012), 11, 12);//2012-12-12
        mGLCView.init(customizedCalendar);//to 2012-12-12*/

        mIndicatorView = (IndicatorView) this.findViewById(R.id.indicator_view);
        mIndicatorView.setOnIndicatorChangedListener(this);

        mDataButton = (Button) this.findViewById(R.id.button_get_data);
        mDataButton.setOnClickListener(this);
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
                Toast.makeText(getApplicationContext(), showToast, Toast.LENGTH_LONG).show();
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

