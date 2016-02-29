package cn.carbs.android.gregorianlunarcalendar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import cn.carbs.android.gregorianlunarcalendar.R;
import cn.carbs.android.gregorianlunarcalendar.data.ChineseCalendar;
import cn.carbs.android.gregorianlunarcalendar.view.GregorianLunarCalendarView;
import cn.carbs.android.gregorianlunarcalendar.view.GregorianLunarCalendarView.CalendarData;

public class MainActivity extends Activity implements View.OnClickListener{

	private static final String TAG = "tag";
	private GregorianLunarCalendarView view;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (GregorianLunarCalendarView)this.findViewById(R.id.my_view);
        
        button1 = (Button)this.findViewById(R.id.button1);
        button2 = (Button)this.findViewById(R.id.button2);
        button3 = (Button)this.findViewById(R.id.button3);
        button4 = (Button)this.findViewById(R.id.button4);
        
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			CalendarData calendarData = view.getCalendarData();
			Log.d(TAG, "calendarData G --> y:" + calendarData.getCalendar().get(Calendar.YEAR));
			Log.d(TAG, "calendarData G --> m:" + (calendarData.getCalendar().get(Calendar.MONTH) + 1));
			Log.d(TAG, "calendarData G --> d:" + calendarData.getCalendar().get(Calendar.DAY_OF_MONTH));
			
			Log.d(TAG, "calendarData L --> y:" + calendarData.getCalendar().get(ChineseCalendar.CHINESE_YEAR));
			Log.d(TAG, "calendarData L --> m:" + (calendarData.getCalendar().get(ChineseCalendar.CHINESE_MONTH)));
			Log.d(TAG, "calendarData L --> d:" + calendarData.getCalendar().get(ChineseCalendar.CHINESE_DATE));
			break;
		case R.id.button2:
			view.setGregorian(true);
			break;
		case R.id.button3:
			view.setGregorian(false);
			break;
		case R.id.button4:
			view.initConfigs(Calendar.getInstance(), true);
			break;	
			
		}
	}
	
}
