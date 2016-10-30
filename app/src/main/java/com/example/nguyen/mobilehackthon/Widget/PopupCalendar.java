package com.example.nguyen.mobilehackthon.Widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.nguyen.mobilehackthon.Activity.AddEventActivity;
import com.example.nguyen.mobilehackthon.R;


public class PopupCalendar extends Activity {

    CalendarView calendar;

    int yy,mm,dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_pick_date);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        calendar= (CalendarView) findViewById(R.id.calen);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView View,int year,int month,int dayOfMonth)
            {
                Button bt= (Button) findViewById(R.id.btn_day_ok);
                yy=year;
                mm=month;
                dd=dayOfMonth;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(PopupCalendar.this, AddEventActivity.class);
                        intent.putExtra("year",Integer.toString(yy));
                        intent.putExtra("month",Integer.toString(mm+1));
                        intent.putExtra("day",Integer.toString(dd));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        }
        );

    }
}
